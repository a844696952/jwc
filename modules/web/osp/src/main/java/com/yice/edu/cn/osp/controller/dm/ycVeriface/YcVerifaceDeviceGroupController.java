package com.yice.edu.cn.osp.controller.dm.ycVeriface;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.dm.kqDevice.KqDeviceGroup;
import com.yice.edu.cn.common.pojo.dm.kqDevice.KqDeviceGroupPerson;
import com.yice.edu.cn.common.pojo.dm.kqDevice.KqDevicePerson;
import com.yice.edu.cn.common.pojo.dm.ycVeriface.device.YcVerifaceDevice;
import com.yice.edu.cn.common.pojo.xw.kq.KqSchoolInit;
import com.yice.edu.cn.common.pojo.xw.kq.ZyDeviceBean;
import com.yice.edu.cn.osp.service.dm.ycVeriface.YcVerifaceDeviceService;
import com.yice.edu.cn.osp.service.dm.zyDevice.KqDeviceGroupPersonService;
import com.yice.edu.cn.osp.service.dm.zyDevice.KqDeviceGroupService;
import com.yice.edu.cn.osp.service.dm.zyDevice.KqDevicePersonService;
import com.yice.edu.cn.osp.service.xw.kq.KqSchoolInitService;
import com.yice.edu.cn.osp.service.xw.kq.KqSchoolZyDevicesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/YcVerifaceDeviceGroup")
@Api(value = "/YcVerifaceDeviceGroup",description = "亿策人脸识别设备分组模块")
public class YcVerifaceDeviceGroupController {
    @Autowired
    private KqDeviceGroupService kqDeviceGroupService;
    @Autowired
    private KqSchoolZyDevicesService kqSchoolZyDevicesService;
    @Autowired
    private KqSchoolInitService kqSchoolInitService;
    @Autowired
    private KqDeviceGroupPersonService kqDeviceGroupPersonService;
    @Autowired
    private KqDevicePersonService kqDevicePersonService;
    @Autowired
    private YcVerifaceDeviceService ycVerifaceDeviceService;

    @Value("#{'${spring.profiles.active}'=='prod'}")
    private Boolean isProd;

    @PostMapping("/saveKqDeviceGroup")
    @ApiOperation(value = "保存考勤设备分组类型对象", notes = "返回保存好的考勤设备分组类型对象", response=KqDeviceGroup.class)
    public ResponseJson saveKqDeviceGroup(
            @ApiParam(value = "考勤设备分组类型对象", required = true)
            @RequestBody KqDeviceGroup kqDeviceGroup){
        kqDeviceGroup.setSchoolId(mySchoolId());
        KqDeviceGroup k = new KqDeviceGroup();
        k.setGroupName(kqDeviceGroup.getGroupName());
        k.setSchoolId(mySchoolId());
        List<KqDeviceGroup> checkExist = kqDeviceGroupService.findKqDeviceGroupListByCondition(k);
        if (checkExist!=null&&checkExist.size()>0){
            return new ResponseJson(false,"请勿添加同名分组！");
        }
        KqDeviceGroup s=kqDeviceGroupService.saveKqDeviceGroup(kqDeviceGroup);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findKqDeviceGroupById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找考勤设备分组类型", notes = "返回响应对象", response=KqDeviceGroup.class)
    public ResponseJson findKqDeviceGroupById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        KqDeviceGroup kqDeviceGroup=kqDeviceGroupService.findKqDeviceGroupById(id);
        return new ResponseJson(kqDeviceGroup);
    }

    @PostMapping("/update/updateKqDeviceGroup")
    @ApiOperation(value = "修改考勤设备分组类型对象", notes = "返回响应对象")
    public ResponseJson updateKqDeviceGroup(
            @ApiParam(value = "被修改的考勤设备分组类型对象,对象属性不为空则修改", required = true)
            @RequestBody KqDeviceGroup kqDeviceGroup){
        KqDeviceGroup k = new KqDeviceGroup();
        k.setGroupName(kqDeviceGroup.getGroupName());
        k.setSchoolId(mySchoolId());
        List<KqDeviceGroup> checkExist = kqDeviceGroupService.findKqDeviceGroupListByCondition(k);
        checkExist= checkExist.stream().filter(c->!c.getId().equals(kqDeviceGroup.getId())).collect(Collectors.toList());
        if (checkExist!=null&&checkExist.size()>0){
            return new ResponseJson(false,"已存在同名分组！");
        }
        kqDeviceGroup.setSchoolId(mySchoolId());
        kqDeviceGroupService.updateKqDeviceGroupAndDeviceType(kqDeviceGroup);
        return new ResponseJson();
    }

    @GetMapping("/look/lookKqDeviceGroupById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找考勤设备分组类型", notes = "返回响应对象", response=KqDeviceGroup.class)
    public ResponseJson lookKqDeviceGroupById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        KqDeviceGroup kqDeviceGroup=kqDeviceGroupService.findKqDeviceGroupById(id);
        return new ResponseJson(kqDeviceGroup);
    }

    @PostMapping("/find/findKqDeviceGroupsByCondition")
    @ApiOperation(value = "根据条件查找考勤设备分组类型", notes = "返回响应对象", response=KqDeviceGroup.class)
    public ResponseJson findKqDeviceGroupsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody KqDeviceGroup kqDeviceGroup){
       kqDeviceGroup.setSchoolId(mySchoolId());
        List<KqDeviceGroup> data=kqDeviceGroupService.findKqDeviceGroupListByCondition(kqDeviceGroup);
        long count=kqDeviceGroupService.findKqDeviceGroupCountByCondition(kqDeviceGroup);
        return new ResponseJson(data,count);
    }
    @PostMapping("/find/findOneKqDeviceGroupByCondition")
    @ApiOperation(value = "根据条件查找单个考勤设备分组类型,结果必须为单条数据", notes = "没有时返回空", response=KqDeviceGroup.class)
    public ResponseJson findOneKqDeviceGroupByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody KqDeviceGroup kqDeviceGroup){
        KqDeviceGroup one=kqDeviceGroupService.findOneKqDeviceGroupByCondition(kqDeviceGroup);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteKqDeviceGroup/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteKqDeviceGroup(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        YcVerifaceDevice ycVerifaceDevice = new YcVerifaceDevice();
        ycVerifaceDevice.setDeviceGroupId(id);
        ycVerifaceDevice.setSchoolId(mySchoolId());
        long ycVerifaceDeviceCountByCondition = ycVerifaceDeviceService.findYcVerifaceDeviceCountByCondition(ycVerifaceDevice);
        if (ycVerifaceDeviceCountByCondition>0){
            return new ResponseJson(false,"该组别名下有设备存在 ，不允许删除！");
        }
        //先查找是否有人员绑定了该分组
        KqDeviceGroupPerson kqDeviceGroupPerson = new KqDeviceGroupPerson();
        kqDeviceGroupPerson.setDeviceGroupId(id);
        kqDeviceGroupPerson.setSchoolId(mySchoolId());
        List<KqDeviceGroupPerson> persons = kqDeviceGroupPersonService.findKqDeviceGroupPersonListByCondition(kqDeviceGroupPerson);
        //没有设备
        if (persons!=null&&persons.size()>0){
            kqDeviceGroupPersonService.deleteKqDeviceGroupPersonByCondition(kqDeviceGroupPerson);
        }
        kqDeviceGroupService.deleteKqDeviceGroup(id);
        return new ResponseJson();
    }


    @PostMapping("/find/findKqDeviceGroupListByCondition")
    @ApiOperation(value = "根据条件查找考勤设备分组类型列表", notes = "返回响应对象,不包含总条数", response=KqDeviceGroup.class)
    public ResponseJson findKqDeviceGroupListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody KqDeviceGroup kqDeviceGroup){
       kqDeviceGroup.setSchoolId(mySchoolId());
        List<KqDeviceGroup> data=kqDeviceGroupService.findKqDeviceGroupListByCondition(kqDeviceGroup);
        return new ResponseJson(data);
    }

    @PostMapping("/find/fingZyDevice")
    @ApiOperation(value = "根据条件查找考勤设备分组类型列表", notes = "返回响应对象,不包含总条数", response=KqDeviceGroup.class)
    public ResponseJson fingZyDevice(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody ZyDeviceBean zyDeviceBean){
        //获得本校密钥
        KqSchoolInit kqSchoolInit = new KqSchoolInit();
        kqSchoolInit.setCustId(mySchoolId());
        KqSchoolInit kqSchool =  kqSchoolInitService.findOneKqSchoolInitByCondition(kqSchoolInit);
        if (kqSchool==null){
            return new ResponseJson(false, "本校未开通此服务，请联系管理员！");
        }
        List<ZyDeviceBean> zyDeviceBeans = kqSchoolZyDevicesService.findSchoolDevices(isProd,kqSchool, zyDeviceBean.getDeviceType());

        return new ResponseJson(zyDeviceBeans);
    }

}
