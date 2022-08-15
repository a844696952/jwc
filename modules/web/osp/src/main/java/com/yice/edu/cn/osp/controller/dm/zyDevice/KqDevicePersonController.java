package com.yice.edu.cn.osp.controller.dm.zyDevice;

import cn.hutool.core.util.StrUtil;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.dm.kqDevice.KqDeviceGroup;
import com.yice.edu.cn.common.pojo.dm.kqDevice.KqDevicePerson;
import com.yice.edu.cn.common.pojo.xw.kq.KqSchoolInit;
import com.yice.edu.cn.common.pojo.xw.kq.ZyDeviceBean;
import com.yice.edu.cn.osp.service.dm.zyDevice.KqDeviceGroupService;
import com.yice.edu.cn.osp.service.dm.zyDevice.KqDevicePersonService;
import com.yice.edu.cn.osp.service.xw.kq.KqSchoolInitService;
import com.yice.edu.cn.osp.service.xw.kq.KqSchoolZyDevicesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.stream.Collectors;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/kqDevicePerson")
@Api(value = "/kqDevicePerson",description = "考勤设备和分组关联表模块")
public class KqDevicePersonController {
    @Autowired
    private KqDevicePersonService kqDevicePersonService;
    @Autowired
    private KqSchoolZyDevicesService kqSchoolZyDevicesService;
    @Autowired
    private KqSchoolInitService kqSchoolInitService;
    @Autowired
    private KqDeviceGroupService kqDeviceGroupService;
    @Value("#{'${spring.profiles.active}'=='prod'}")
    private Boolean isProd;

    @PostMapping("/saveKqDevicePerson")
    @ApiOperation(value = "保存考勤设备人员关联表对象", notes = "返回保存好的考勤设备人员关联表对象", response=KqDevicePerson.class)
    public ResponseJson saveKqDevicePerson(
            @ApiParam(value = "考勤设备人员关联表对象", required = true)
            @RequestBody KqDevicePerson kqDevicePerson){
       kqDevicePerson.setSchoolId(mySchoolId());
        KqDevicePerson s=kqDevicePersonService.saveKqDevicePerson(kqDevicePerson);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findKqDevicePersonById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找考勤设备人员关联表", notes = "返回响应对象", response=KqDevicePerson.class)
    public ResponseJson findKqDevicePersonById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        KqDevicePerson kqDevicePerson=kqDevicePersonService.findKqDevicePersonById(id);
        return new ResponseJson(kqDevicePerson);
    }

    @PostMapping("/update/updateKqDevicePerson")
    @ApiOperation(value = "修改考勤设备人员关联表对象", notes = "返回响应对象")
    public ResponseJson updateKqDevicePerson(
            @ApiParam(value = "被修改的考勤设备人员关联表对象,对象属性不为空则修改", required = true)
            @RequestBody KqDevicePerson kqDevicePerson){
        kqDevicePersonService.updateKqDevicePerson(kqDevicePerson);
        return new ResponseJson();
    }

    @GetMapping("/look/lookKqDevicePersonById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找考勤设备人员关联表", notes = "返回响应对象", response=KqDevicePerson.class)
    public ResponseJson lookKqDevicePersonById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        KqDevicePerson kqDevicePerson=kqDevicePersonService.findKqDevicePersonById(id);
        return new ResponseJson(kqDevicePerson);
    }

    @PostMapping("/findKqDevicePersonsByCondition")
    @ApiOperation(value = "根据条件查找考勤设备人员关联表", notes = "返回响应对象", response=KqDevicePerson.class)
    public ResponseJson findKqDevicePersonsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody KqDevicePerson kqDevicePerson){
       kqDevicePerson.setSchoolId(mySchoolId());
        List<KqDevicePerson> data=kqDevicePersonService.findKqDevicePersonListByCondition(kqDevicePerson);
        long count=kqDevicePersonService.findKqDevicePersonCountByCondition(kqDevicePerson);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneKqDevicePersonByCondition")
    @ApiOperation(value = "根据条件查找单个考勤设备人员关联表,结果必须为单条数据", notes = "没有时返回空", response=KqDevicePerson.class)
    public ResponseJson findOneKqDevicePersonByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody KqDevicePerson kqDevicePerson){
        KqDevicePerson one=kqDevicePersonService.findOneKqDevicePersonByCondition(kqDevicePerson);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteKqDevicePerson/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteKqDevicePerson(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        kqDevicePersonService.deleteKqDevicePerson(id);
        return new ResponseJson();
    }


    @PostMapping("/find/findKqDevicePersonListByCondition")
    @ApiOperation(value = "根据条件查找考勤设备人员关联表列表", notes = "返回响应对象,不包含总条数", response=KqDevicePerson.class)
    public ResponseJson findKqDevicePersonListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody KqDevicePerson kqDevicePerson){
       kqDevicePerson.setSchoolId(mySchoolId());
        List<KqDevicePerson> data=kqDevicePersonService.findKqDevicePersonListByCondition(kqDevicePerson);
        return new ResponseJson(data);
    }

    @PostMapping("/find/fingZyDevice")
    @ApiOperation(value = "根据条件查找考勤设备分组类型列表", notes = "返回响应对象,不包含总条数", response= KqDeviceGroup.class)
    public ResponseJson fingZyDevice(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody KqDevicePerson zyDevice){
        //获得本校密钥
        KqSchoolInit kqSchoolInit = new KqSchoolInit();
        kqSchoolInit.setCustId(mySchoolId());
        KqSchoolInit kqSchool =  kqSchoolInitService.findOneKqSchoolInitByCondition(kqSchoolInit);
        if (kqSchool==null){
            return new ResponseJson(false, "本校未开通此服务，请联系管理员！");
        }
        List<ZyDeviceBean> zyDeviceBeans = kqSchoolZyDevicesService.findSchoolDevices(isProd,kqSchool, zyDevice.getDeviceType());
        KqDevicePerson kqDevicePerson =new KqDevicePerson();
        kqDevicePerson.setDeviceType(zyDevice.getDeviceType());
        kqDevicePerson.setSchoolId(mySchoolId());
        Pager pager = new Pager();
        pager.setPaging(false);
        pager.setIncludes("id","groupName","groupId","deviceNo","deviceName","schoolId","deviceType");
        kqDevicePerson.setPager(pager);
        List<KqDevicePerson> kqDevicePersons=kqDevicePersonService.findKqDevicePersonListByCondition(kqDevicePerson);
        List<ZyDeviceBean> data = kqDevicePersonService.fuseZyDeviceAndLocal(zyDeviceBeans,kqDevicePersons);
        data=  kqDevicePersonService.deviceFilter(data,zyDevice);
        long count = data.size();
        data=  data.stream().skip(zyDevice.getPager().getBeginRow()).limit(zyDevice.getPager().getPageSize()).collect(Collectors.toList());
        return new ResponseJson(data,count);
    }

    @PostMapping("/find/findKqDeviceGroupsByCondition")
    @ApiOperation(value = "根据条件查找考勤设备分组类型", notes = "返回响应对象", response=KqDeviceGroup.class)
    public ResponseJson findKqDeviceGroupsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody KqDeviceGroup kqDeviceGroup){
        kqDeviceGroup.getPager().setPaging(false);
        kqDeviceGroup.setSchoolId(mySchoolId());
        List<KqDeviceGroup> data=kqDeviceGroupService.findKqDeviceGroupListByCondition(kqDeviceGroup);
        long count=kqDeviceGroupService.findKqDeviceGroupCountByCondition(kqDeviceGroup);
        return new ResponseJson(data,count);
    }
}
