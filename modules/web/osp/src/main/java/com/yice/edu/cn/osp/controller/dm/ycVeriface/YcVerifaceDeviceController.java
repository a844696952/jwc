package com.yice.edu.cn.osp.controller.dm.ycVeriface;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.dm.ycVeriface.account.YcVerifaceAccount;
import com.yice.edu.cn.common.pojo.dm.ycVeriface.device.YcVerifaceDevice;
import com.yice.edu.cn.osp.service.dm.ycVeriface.YcVerifaceAccountService;
import com.yice.edu.cn.osp.service.dm.ycVeriface.YcVerifaceDeviceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import java.util.List;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.myId;
import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/ycVerifaceDevice")
@Api(value = "/ycVerifaceDevice",description = "设备模块")
public class YcVerifaceDeviceController {
    @Autowired
    private YcVerifaceDeviceService ycVerifaceDeviceService;
    @Autowired
    private YcVerifaceAccountService ycVerifaceAccountService;
    @PostMapping("/saveYcVerifaceDevice")
    @ApiOperation(value = "保存对象", notes = "返回保存好的对象", response= YcVerifaceDevice.class)
    public ResponseJson saveYcVerifaceDevice(
            @ApiParam(value = "对象", required = true)
            @RequestBody YcVerifaceDevice ycVerifaceDevice){
       ycVerifaceDevice.setSchoolId(mySchoolId());
        YcVerifaceDevice s=ycVerifaceDeviceService.saveYcVerifaceDevice(ycVerifaceDevice);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findYcVerifaceDeviceById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找", notes = "返回响应对象", response=YcVerifaceDevice.class)
    public ResponseJson findYcVerifaceDeviceById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        YcVerifaceDevice ycVerifaceDevice=ycVerifaceDeviceService.findYcVerifaceDeviceById(id);
        return new ResponseJson(ycVerifaceDevice);
    }

    @PostMapping("/update/updateYcVerifaceDevice")
    @ApiOperation(value = "修改对象", notes = "返回响应对象")
    public ResponseJson updateYcVerifaceDevice(
            @ApiParam(value = "被修改的对象,对象属性不为空则修改", required = true)
            @RequestBody YcVerifaceDevice ycVerifaceDevice){
        YcVerifaceDevice y = new YcVerifaceDevice();
        y.setType(ycVerifaceDevice.getType());
        y.setSchoolId(ycVerifaceDevice.getSchoolId());
        y.setDeviceSign(ycVerifaceDevice.getDeviceSign());
        List<YcVerifaceDevice> ys = ycVerifaceDeviceService.findYcVerifaceDeviceListByCondition(y);
        if (CollectionUtil.isNotEmpty(ys)&&(ys.size()>1||!ys.get(0).getId().equals(ycVerifaceDevice.getId()))){
            return  new ResponseJson(false,"设备ID重复");
        }
        YcVerifaceDevice y2 = new YcVerifaceDevice();
        y2.setType(ycVerifaceDevice.getType());
        y2.setSchoolId(ycVerifaceDevice.getSchoolId());
        y2.setDeviceName(ycVerifaceDevice.getDeviceName());
        List<YcVerifaceDevice> ys2 = ycVerifaceDeviceService.findYcVerifaceDeviceListByCondition(y2);
        if (CollectionUtil.isNotEmpty(ys2)&&(ys2.size()>1||!ys2.get(0).getId().equals(ycVerifaceDevice.getId()))){
            return  new ResponseJson(false,"设备名称重复");
        }
        ycVerifaceDeviceService.updateYcVerifaceDevice(ycVerifaceDevice);
        return new ResponseJson();
    }

    @GetMapping("/look/lookYcVerifaceDeviceById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找", notes = "返回响应对象", response=YcVerifaceDevice.class)
    public ResponseJson lookYcVerifaceDeviceById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        YcVerifaceDevice ycVerifaceDevice=ycVerifaceDeviceService.findYcVerifaceDeviceById(id);
        return new ResponseJson(ycVerifaceDevice);
    }

    @PostMapping("/findYcVerifaceDevicesByCondition")
    @ApiOperation(value = "根据条件查找", notes = "返回响应对象", response=YcVerifaceDevice.class)
    public ResponseJson findYcVerifaceDevicesByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody YcVerifaceDevice ycVerifaceDevice){
       ycVerifaceDevice.setSchoolId(mySchoolId());
        List<YcVerifaceDevice> data=ycVerifaceDeviceService.findYcVerifaceDeviceListByCondition(ycVerifaceDevice);
        long count=ycVerifaceDeviceService.findYcVerifaceDeviceCountByCondition(ycVerifaceDevice);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneYcVerifaceDeviceByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "没有时返回空", response=YcVerifaceDevice.class)
    public ResponseJson findOneYcVerifaceDeviceByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody YcVerifaceDevice ycVerifaceDevice){
        YcVerifaceDevice one=ycVerifaceDeviceService.findOneYcVerifaceDeviceByCondition(ycVerifaceDevice);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteYcVerifaceDevice/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteYcVerifaceDevice(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        ycVerifaceDeviceService.deleteYcVerifaceDevice(id);
        return new ResponseJson();
    }


    @PostMapping("/findYcVerifaceDeviceListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回响应对象,不包含总条数", response=YcVerifaceDevice.class)
    public ResponseJson findYcVerifaceDeviceListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody YcVerifaceDevice ycVerifaceDevice){
       ycVerifaceDevice.setSchoolId(mySchoolId());
        List<YcVerifaceDevice> data=ycVerifaceDeviceService.findYcVerifaceDeviceListByCondition(ycVerifaceDevice);
        return new ResponseJson(data);
    }
/*------------------------------------------------------------------------------------------------------------------------*/
    @PostMapping("/saveYcVerifaceDoorDevice")
    @ApiOperation(value = "保存门禁对象", notes = "返回保存好的对象", response= YcVerifaceDevice.class)
    public ResponseJson saveYcVerifaceDoorDevice(
            @ApiParam(value = "对象", required = true)
            @RequestBody YcVerifaceDevice ycVerifaceDevice){
        ycVerifaceDevice.setType("1");
        //查询是否重复设备标识
        ycVerifaceDevice.setSchoolId(mySchoolId());
        YcVerifaceAccount account = new YcVerifaceAccount();
        account.setSchoolId(mySchoolId());
        account.setType("1");
        YcVerifaceAccount oneYcVerifaceAccountByCondition = ycVerifaceAccountService.findOneYcVerifaceAccountByCondition(account);
        if (oneYcVerifaceAccountByCondition==null) {
            return new ResponseJson(false,"请先生成门禁账号");
        }
        YcVerifaceDevice y = new YcVerifaceDevice();
        y.setType("1");
        y.setSchoolId(ycVerifaceDevice.getSchoolId());
        y.setDeviceSign(ycVerifaceDevice.getDeviceSign());
        List<YcVerifaceDevice> ys = ycVerifaceDeviceService.findYcVerifaceDeviceListByCondition(y);
        if (CollectionUtil.isNotEmpty(ys)){
            return  new ResponseJson(false,"设备ID重复");
        }
        YcVerifaceDevice y2 = new YcVerifaceDevice();
        y2.setType("1");
        y2.setSchoolId(ycVerifaceDevice.getSchoolId());
        y2.setDeviceName(ycVerifaceDevice.getDeviceName());
        List<YcVerifaceDevice> ys2 = ycVerifaceDeviceService.findYcVerifaceDeviceListByCondition(y2);
        if (CollectionUtil.isNotEmpty(ys2)){
            return  new ResponseJson(false,"设备名称重复");
        }
        ycVerifaceDevice.setAccountId(oneYcVerifaceAccountByCondition.getId());
        YcVerifaceDevice s=ycVerifaceDeviceService.saveYcVerifaceDevice(ycVerifaceDevice);
        return new ResponseJson(s);
    }
    @PostMapping("/saveYcVerifaceCameraDevice")
    @ApiOperation(value = "保存摄像头对象", notes = "返回保存好的对象", response= YcVerifaceDevice.class)
    public ResponseJson saveYcVerifaceCameraDevice(
            @ApiParam(value = "对象", required = true)
            @RequestBody YcVerifaceDevice ycVerifaceDevice){

        ycVerifaceDevice.setType("0");
        //查询是否重复设备标识
        ycVerifaceDevice.setSchoolId(mySchoolId());
        YcVerifaceDevice y = new YcVerifaceDevice();
        y.setType("0");
        y.setSchoolId(ycVerifaceDevice.getSchoolId());
        y.setDeviceSign(ycVerifaceDevice.getDeviceSign());
        List<YcVerifaceDevice> ys = ycVerifaceDeviceService.findYcVerifaceDeviceListByCondition(y);
        if (CollectionUtil.isNotEmpty(ys)){
            return  new ResponseJson(false,"设备ID重复");
        }
        YcVerifaceDevice y2 = new YcVerifaceDevice();
        y2.setType("0");
        y2.setSchoolId(ycVerifaceDevice.getSchoolId());
        y2.setDeviceName(ycVerifaceDevice.getDeviceName());
        List<YcVerifaceDevice> ys2 = ycVerifaceDeviceService.findYcVerifaceDeviceListByCondition(y2);
        if (CollectionUtil.isNotEmpty(ys2)){
            return  new ResponseJson(false,"设备名称重复");
        }
        YcVerifaceDevice s=ycVerifaceDeviceService.saveYcVerifaceDevice(ycVerifaceDevice);
        return new ResponseJson(s);
    }
    @PostMapping("/findYcVerifaceDoorDevicesByCondition")
    @ApiOperation(value = "根据条件查找门禁设备", notes = "返回响应对象", response=YcVerifaceDevice.class)
    public ResponseJson findYcVerifaceDoorDevicesByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody YcVerifaceDevice ycVerifaceDevice){
        ycVerifaceDevice.setSchoolId(mySchoolId());
        ycVerifaceDevice.setType("1");
        List<YcVerifaceDevice> data=ycVerifaceDeviceService.findYcVerifaceDeviceListByCondition(ycVerifaceDevice);
        long count=ycVerifaceDeviceService.findYcVerifaceDeviceCountByCondition(ycVerifaceDevice);
        //合并状态
        data = ycVerifaceDeviceService.findAndChangeDoorStatus(data);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findYcVerifaceCameraDevicesByCondition")
    @ApiOperation(value = "根据条件查找摄像头设备", notes = "返回响应对象", response=YcVerifaceDevice.class)
    public ResponseJson findYcVerifaceCameraDevicesByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody YcVerifaceDevice ycVerifaceDevice){
        ycVerifaceDevice.setSchoolId(mySchoolId());
        ycVerifaceDevice.setType("0");
        List<YcVerifaceDevice> data=ycVerifaceDeviceService.findYcVerifaceDeviceListByCondition(ycVerifaceDevice);
        long count=ycVerifaceDeviceService.findYcVerifaceDeviceCountByCondition(ycVerifaceDevice);
        //合并状态
        return new ResponseJson(data,count);
    }

    @GetMapping("/findYcVerifaceCameraDevicesWithAccount")
    @ApiOperation(value = "根据条件查找每个摄像头账号下的摄像头设备", notes = "返回响应对象", response=YcVerifaceDevice.class)
    public ResponseJson findYcVerifaceCameraDevicesWithAccount(){
      List<YcVerifaceAccount> ycVerifaceAccountsWithDevice= ycVerifaceDeviceService.findYcVerifaceCameraDevicesWithAccount();
        //合并状态
        return new ResponseJson(ycVerifaceAccountsWithDevice);
    }

    @GetMapping("/resetDeviceAndBountPeople/{deviceId}")
    @ApiOperation(value = "通知设备重置设备人员列表", notes = "返回响应对象", response=YcVerifaceDevice.class)
    public ResponseJson getResetDeviceAndBountPeople(@ApiParam(value = "要初始化得设备deviceId", required = true)
                                                               @PathVariable String deviceId){
        YcVerifaceDevice ycVerifaceDeviceById = ycVerifaceDeviceService.findYcVerifaceDeviceById(deviceId);
        if (ycVerifaceDeviceById!=null&&StrUtil.isNotEmpty(ycVerifaceDeviceById.getDeviceSign())){
            //ycVerifaceDeviceService.toldDevicePeopleListHaveChange(ycVerifaceDeviceById.getDeviceSign());
            ycVerifaceDeviceById.setCacheKey("reset");
            ycVerifaceDeviceService.toldDevicePeopleListHaveChange(ycVerifaceDeviceById, Constant.YC_VERIFACE_TOLD_DEVICE_CHANGE_CODE.PERSON_LIST_RESET);

            return new ResponseJson(true,"正在重置");
        }
        //合并状态
        return new ResponseJson(false,"没有找到已绑定的设备");
    }
}
