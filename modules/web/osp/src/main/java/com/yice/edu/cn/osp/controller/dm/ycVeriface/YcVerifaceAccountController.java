package com.yice.edu.cn.osp.controller.dm.ycVeriface;

import cn.hutool.core.collection.CollectionUtil;
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

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/ycVerifaceAccount")
@Api(value = "/ycVerifaceAccount",description = "亿策人脸识别账号")
public class YcVerifaceAccountController {
    @Autowired
    private YcVerifaceAccountService ycVerifaceAccountService;
    @Autowired
    private YcVerifaceDeviceService ycVerifaceDeviceService;

    @PostMapping("/saveYcVerifaceAccount")
    @ApiOperation(value = "保存对象", notes = "返回保存好的对象", response= YcVerifaceAccount.class)
    public ResponseJson saveYcVerifaceAccount(
            @ApiParam(value = "对象", required = true)
            @RequestBody YcVerifaceAccount ycVerifaceAccount){
       ycVerifaceAccount.setSchoolId(mySchoolId());
        YcVerifaceAccount s=ycVerifaceAccountService.saveYcVerifaceAccount(ycVerifaceAccount);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findYcVerifaceAccountById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找", notes = "返回响应对象", response=YcVerifaceAccount.class)
    public ResponseJson findYcVerifaceAccountById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        YcVerifaceAccount ycVerifaceAccount=ycVerifaceAccountService.findYcVerifaceAccountById(id);
        return new ResponseJson(ycVerifaceAccount);
    }

    @PostMapping("/update/updateYcVerifaceAccount")
    @ApiOperation(value = "修改对象", notes = "返回响应对象")
    public ResponseJson updateYcVerifaceAccount(
            @ApiParam(value = "被修改的对象,对象属性不为空则修改", required = true)
            @RequestBody YcVerifaceAccount ycVerifaceAccount){
        ycVerifaceAccountService.updateYcVerifaceAccount(ycVerifaceAccount);
        return new ResponseJson();
    }

    @GetMapping("/look/lookYcVerifaceAccountById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找", notes = "返回响应对象", response=YcVerifaceAccount.class)
    public ResponseJson lookYcVerifaceAccountById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        YcVerifaceAccount ycVerifaceAccount=ycVerifaceAccountService.findYcVerifaceAccountById(id);
        return new ResponseJson(ycVerifaceAccount);
    }

    @PostMapping("/findYcVerifaceAccountsByCondition")
    @ApiOperation(value = "根据条件查找", notes = "返回响应对象", response=YcVerifaceAccount.class)
    public ResponseJson findYcVerifaceAccountsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody YcVerifaceAccount ycVerifaceAccount){
       ycVerifaceAccount.setSchoolId(mySchoolId());
        List<YcVerifaceAccount> data=ycVerifaceAccountService.findYcVerifaceAccountListByCondition(ycVerifaceAccount);
        long count=ycVerifaceAccountService.findYcVerifaceAccountCountByCondition(ycVerifaceAccount);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneYcVerifaceAccountByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "没有时返回空", response=YcVerifaceAccount.class)
    public ResponseJson findOneYcVerifaceAccountByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody YcVerifaceAccount ycVerifaceAccount){
        YcVerifaceAccount one=ycVerifaceAccountService.findOneYcVerifaceAccountByCondition(ycVerifaceAccount);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteYcVerifaceAccount/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteYcVerifaceAccount(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        //查询账号下是否有设备
        YcVerifaceDevice ycVerifaceDevice = new YcVerifaceDevice();
        ycVerifaceDevice.setAccountId(id);
        ycVerifaceDevice.setSchoolId(mySchoolId());
        List<YcVerifaceDevice> ycVerifaceDeviceListByCondition = ycVerifaceDeviceService.findYcVerifaceDeviceListByCondition(ycVerifaceDevice);
        if (CollectionUtil.isNotEmpty(ycVerifaceDeviceListByCondition)){
            return new ResponseJson(false,"请先删除账号下的设备");
        }
        ycVerifaceAccountService.deleteYcVerifaceAccount(id);
        return new ResponseJson();
    }
/*------------------------------------------------------------------------------------*/

    @PostMapping("/findYcVerifaceAccountListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回响应对象,不包含总条数", response=YcVerifaceAccount.class)
    public ResponseJson findYcVerifaceAccountListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody YcVerifaceAccount ycVerifaceAccount){
        ycVerifaceAccount.setSchoolId(mySchoolId());
        List<YcVerifaceAccount> data=ycVerifaceAccountService.findYcVerifaceAccountListByCondition(ycVerifaceAccount);
        return new ResponseJson(data);
    }
    @GetMapping("/findYcVerifaceDoorAccountListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回响应对象,不包含总条数", response=YcVerifaceAccount.class)
    public ResponseJson findYcVerifaceDoorAccountListByCondition(){
        YcVerifaceAccount ycVerifaceAccount = new YcVerifaceAccount();
        ycVerifaceAccount.setType("1");
        ycVerifaceAccount.setSchoolId(mySchoolId());
        YcVerifaceAccount data = ycVerifaceAccountService.findOneYcVerifaceAccountByCondition(ycVerifaceAccount);
        return new ResponseJson(data);
    }

    @GetMapping("/generateCameraAccount")
    @ApiOperation(value = "生成摄像头账号", notes = "返回响应对象,不包含总条数", response=YcVerifaceAccount.class)
    public ResponseJson generateCameraAccount(){
        YcVerifaceAccount ycVerifaceAccount = new YcVerifaceAccount();
        ycVerifaceAccount.setType("0");
        ycVerifaceAccount.setSchoolId(mySchoolId());
        YcVerifaceAccount data= ycVerifaceAccountService.generateAccount(ycVerifaceAccount);
        return new ResponseJson(data);
    }

    @GetMapping("/generateDoorAccount")
    @ApiOperation(value = "生成门禁账号", notes = "返回响应对象,不包含总条数", response=YcVerifaceAccount.class)
    public ResponseJson generateDoorAccount(){
        YcVerifaceAccount ycVerifaceAccount = new YcVerifaceAccount();
        ycVerifaceAccount.setType("1");
        ycVerifaceAccount.setSchoolId(mySchoolId());
        List<YcVerifaceAccount> ycVerifaceAccountListByCondition = ycVerifaceAccountService.findYcVerifaceAccountListByCondition(ycVerifaceAccount);
        if (CollectionUtil.isEmpty(ycVerifaceAccountListByCondition)){
            YcVerifaceAccount data= ycVerifaceAccountService.generateAccount(ycVerifaceAccount);
            return new ResponseJson(data);
        }else {
            return new ResponseJson(ycVerifaceAccountListByCondition.get(0));
        }
    }
    @PostMapping("/updateDoorAccount")
    @ApiOperation(value = "修改对象", notes = "返回响应对象")
    public ResponseJson updateDoorAccount(
            @ApiParam(value = "被修改的对象,对象属性不为空则修改", required = true)
            @RequestBody YcVerifaceAccount ycVerifaceAccount){
        ycVerifaceAccountService.updateYcVerifaceAccount(ycVerifaceAccount);
        return new ResponseJson();
    }
    @GetMapping("/findYcVerifaceCameraAccountsByCondition")
    @ApiOperation(value = "根据条件查找", notes = "返回响应对象", response=YcVerifaceAccount.class)
    public ResponseJson findYcVerifaceCameraAccountsByCondition(){
        YcVerifaceAccount ycVerifaceAccount =new YcVerifaceAccount();
        ycVerifaceAccount.setSchoolId(mySchoolId());
        ycVerifaceAccount.setType("0");
        List<YcVerifaceAccount> data=ycVerifaceAccountService.findYcVerifaceAccountListByCondition(ycVerifaceAccount);
        long count=ycVerifaceAccountService.findYcVerifaceAccountCountByCondition(ycVerifaceAccount);
        return new ResponseJson(data,count);
    }




}
