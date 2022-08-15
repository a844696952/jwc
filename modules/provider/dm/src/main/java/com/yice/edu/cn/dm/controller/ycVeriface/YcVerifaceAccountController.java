package com.yice.edu.cn.dm.controller.ycVeriface;

import com.yice.edu.cn.common.pojo.dm.ycVeriface.account.YcVerifaceAccount;
import com.yice.edu.cn.dm.service.ycVeriface.YcVerifaceAccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ycVerifaceAccount")
@Api(value = "/ycVerifaceAccount",description = "人脸识别账号")
public class YcVerifaceAccountController {
    @Autowired
    private YcVerifaceAccountService ycVerifaceAccountService;

    @GetMapping("/findYcVerifaceAccountById/{id}")
    @ApiOperation(value = "通过id查找", notes = "返回对象")
    public YcVerifaceAccount findYcVerifaceAccountById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return ycVerifaceAccountService.findYcVerifaceAccountById(id);
    }

    @PostMapping("/saveYcVerifaceAccount")
    @ApiOperation(value = "保存", notes = "返回对象")
    public YcVerifaceAccount saveYcVerifaceAccount(
            @ApiParam(value = "对象", required = true)
            @RequestBody YcVerifaceAccount ycVerifaceAccount){
        ycVerifaceAccountService.saveYcVerifaceAccount(ycVerifaceAccount);
        return ycVerifaceAccount;
    }

    @PostMapping("/findYcVerifaceAccountListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
    public List<YcVerifaceAccount> findYcVerifaceAccountListByCondition(
            @ApiParam(value = "对象")
            @RequestBody YcVerifaceAccount ycVerifaceAccount){
        return ycVerifaceAccountService.findYcVerifaceAccountListByCondition(ycVerifaceAccount);
    }
    @PostMapping("/findYcVerifaceAccountCountByCondition")
    @ApiOperation(value = "根据条件查找列表个数", notes = "返回总个数")
    public long findYcVerifaceAccountCountByCondition(
            @ApiParam(value = "对象")
            @RequestBody YcVerifaceAccount ycVerifaceAccount){
        return ycVerifaceAccountService.findYcVerifaceAccountCountByCondition(ycVerifaceAccount);
    }

    @PostMapping("/updateYcVerifaceAccount")
    @ApiOperation(value = "修改", notes = "对象必传")
    public void updateYcVerifaceAccount(
            @ApiParam(value = "对象,对象属性不为空则修改", required = true)
            @RequestBody YcVerifaceAccount ycVerifaceAccount){
        ycVerifaceAccountService.updateYcVerifaceAccount(ycVerifaceAccount);
    }

    @GetMapping("/deleteYcVerifaceAccount/{id}")
    @ApiOperation(value = "通过id删除")
    public void deleteYcVerifaceAccount(
            @ApiParam(value = "对象", required = true)
            @PathVariable String id){
        ycVerifaceAccountService.deleteYcVerifaceAccount(id);
    }
    @PostMapping("/deleteYcVerifaceAccountByCondition")
    @ApiOperation(value = "根据条件删除")
    public void deleteYcVerifaceAccountByCondition(
            @ApiParam(value = "对象")
            @RequestBody YcVerifaceAccount ycVerifaceAccount){
        ycVerifaceAccountService.deleteYcVerifaceAccountByCondition(ycVerifaceAccount);
    }
    @PostMapping("/findOneYcVerifaceAccountByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "返回单个,没有时为空")
    public YcVerifaceAccount findOneYcVerifaceAccountByCondition(
            @ApiParam(value = "对象")
            @RequestBody YcVerifaceAccount ycVerifaceAccount){
        return ycVerifaceAccountService.findOneYcVerifaceAccountByCondition(ycVerifaceAccount);
    }
}
