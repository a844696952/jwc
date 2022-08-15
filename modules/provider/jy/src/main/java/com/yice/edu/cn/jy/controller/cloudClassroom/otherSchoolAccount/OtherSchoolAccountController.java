package com.yice.edu.cn.jy.controller.cloudClassroom.otherSchoolAccount;

import com.yice.edu.cn.common.pojo.cc.otherSchoolAccount.OtherSchoolAccount;
import com.yice.edu.cn.jy.service.cloudClassroom.otherSchoolAccount.OtherSchoolAccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/otherSchoolAccount")
@Api(value = "/otherSchoolAccount",description = "外校账号模块")
public class OtherSchoolAccountController {
    @Autowired
    private OtherSchoolAccountService otherSchoolAccountService;

    @GetMapping("/findOtherSchoolAccountById/{id}")
    @ApiOperation(value = "通过id查找外校账号", notes = "返回外校账号对象")
    public OtherSchoolAccount findOtherSchoolAccountById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return otherSchoolAccountService.findOtherSchoolAccountById(id);
    }

    @PostMapping("/saveOtherSchoolAccount")
    @ApiOperation(value = "保存外校账号", notes = "返回外校账号对象")
    public OtherSchoolAccount saveOtherSchoolAccount(
            @ApiParam(value = "外校账号对象", required = true)
            @RequestBody OtherSchoolAccount otherSchoolAccount){
        otherSchoolAccountService.saveOtherSchoolAccount(otherSchoolAccount);
        return otherSchoolAccount;
    }

    @PostMapping("/findOtherSchoolAccountListByCondition")
    @ApiOperation(value = "根据条件查找外校账号列表", notes = "返回外校账号列表")
    public List<OtherSchoolAccount> findOtherSchoolAccountListByCondition(
            @ApiParam(value = "外校账号对象")
            @RequestBody OtherSchoolAccount otherSchoolAccount){
        return otherSchoolAccountService.findOtherSchoolAccountListByCondition(otherSchoolAccount);
    }
    @PostMapping("/findOtherSchoolAccountCountByCondition")
    @ApiOperation(value = "根据条件查找外校账号列表个数", notes = "返回外校账号总个数")
    public long findOtherSchoolAccountCountByCondition(
            @ApiParam(value = "外校账号对象")
            @RequestBody OtherSchoolAccount otherSchoolAccount){
        return otherSchoolAccountService.findOtherSchoolAccountCountByCondition(otherSchoolAccount);
    }

    @PostMapping("/updateOtherSchoolAccount")
    @ApiOperation(value = "修改外校账号", notes = "外校账号对象必传")
    public void updateOtherSchoolAccount(
            @ApiParam(value = "外校账号对象,对象属性不为空则修改", required = true)
            @RequestBody OtherSchoolAccount otherSchoolAccount){
        otherSchoolAccountService.updateOtherSchoolAccount(otherSchoolAccount);
    }

    @GetMapping("/deleteOtherSchoolAccount/{id}")
    @ApiOperation(value = "通过id删除外校账号")
    public void deleteOtherSchoolAccount(
            @ApiParam(value = "外校账号对象", required = true)
            @PathVariable String id){
        otherSchoolAccountService.deleteOtherSchoolAccount(id);
    }
    @PostMapping("/deleteOtherSchoolAccountByCondition")
    @ApiOperation(value = "根据条件删除外校账号")
    public void deleteOtherSchoolAccountByCondition(
            @ApiParam(value = "外校账号对象")
            @RequestBody OtherSchoolAccount otherSchoolAccount){
        otherSchoolAccountService.deleteOtherSchoolAccountByCondition(otherSchoolAccount);
    }
    @PostMapping("/findOneOtherSchoolAccountByCondition")
    @ApiOperation(value = "根据条件查找单个外校账号,结果必须为单条数据", notes = "返回单个外校账号,没有时为空")
    public OtherSchoolAccount findOneOtherSchoolAccountByCondition(
            @ApiParam(value = "外校账号对象")
            @RequestBody OtherSchoolAccount otherSchoolAccount){
        return otherSchoolAccountService.findOneOtherSchoolAccountByCondition(otherSchoolAccount);
    }
}
