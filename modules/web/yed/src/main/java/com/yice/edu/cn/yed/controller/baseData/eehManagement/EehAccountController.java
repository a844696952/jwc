package com.yice.edu.cn.yed.controller.baseData.eehManagement;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.eehManagement.EehAccount;
import com.yice.edu.cn.yed.service.baseData.eehManagement.EehAccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@RestController
@RequestMapping("/eehAccount")
@Api(value = "/eehAccount",description = "模块")
public class EehAccountController {
    @Autowired
    private EehAccountService eehAccountService;

    @PostMapping("/saveEehAccount")
    @ApiOperation(value = "保存对象", notes = "返回保存好的对象", response=EehAccount.class)
    public ResponseJson saveEehAccount(
            @ApiParam(value = "对象", required = true)
            @RequestBody EehAccount eehAccount){
        EehAccount s=eehAccountService.saveEehAccount(eehAccount);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findEehAccountById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找", notes = "返回响应对象", response=EehAccount.class)
    public ResponseJson findEehAccountById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        EehAccount eehAccount=eehAccountService.findEehAccountById(id);
        return new ResponseJson(eehAccount);
    }

    @PostMapping("/update/updateEehAccount")
    @ApiOperation(value = "修改对象", notes = "返回响应对象")
    public ResponseJson updateEehAccount(
            @ApiParam(value = "被修改的对象,对象属性不为空则修改", required = true)
            @RequestBody EehAccount eehAccount){
        eehAccountService.updateEehAccount(eehAccount);
        return new ResponseJson();
    }

    @GetMapping("/look/lookEehAccountById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找", notes = "返回响应对象", response=EehAccount.class)
    public ResponseJson lookEehAccountById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        EehAccount eehAccount=eehAccountService.findEehAccountById(id);
        return new ResponseJson(eehAccount);
    }

    @PostMapping("/findEehAccountsByCondition")
    @ApiOperation(value = "根据条件查找", notes = "返回响应对象", response=EehAccount.class)
    public ResponseJson findEehAccountsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody EehAccount eehAccount){
        List<EehAccount> data=eehAccountService.findEehAccountListByCondition(eehAccount);
        long count=eehAccountService.findEehAccountCountByCondition(eehAccount);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneEehAccountByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "没有时返回空", response=EehAccount.class)
    public ResponseJson findOneEehAccountByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody EehAccount eehAccount){
        EehAccount one=eehAccountService.findOneEehAccountByCondition(eehAccount);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteEehAccount/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteEehAccount(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        eehAccountService.deleteEehAccount(id);
        return new ResponseJson();
    }


    @PostMapping("/findEehAccountListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回响应对象,不包含总条数", response=EehAccount.class)
    public ResponseJson findEehAccountListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody EehAccount eehAccount){
        List<EehAccount> data=eehAccountService.findEehAccountListByCondition(eehAccount);
        return new ResponseJson(data);
    }

    @GetMapping("/updatePassword/{id}")
    public ResponseJson updatePassword(@PathVariable String id){
        eehAccountService.updatePassword(id);
        return new ResponseJson();
    }

    @PostMapping("/update/updateEehAccountStatus")
    @ApiOperation(value = "修改对象", notes = "返回响应对象")
    public ResponseJson updateEehAccountStatus(
            @ApiParam(value = "被修改的对象,对象属性不为空则修改", required = true)
            @RequestBody EehAccount eehAccount){
        eehAccountService.updateEehAccountStatus(eehAccount);
        return new ResponseJson();
    }

    @PostMapping("/findOneEehAccountByAccountType")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "没有时返回空", response=EehAccount.class)
    public ResponseJson findOneEehAccountByAccountType(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody EehAccount eehAccount){
        eehAccount.setAccountType("1");
        EehAccount one=eehAccountService.findOneEehAccountByAccountType(eehAccount);
        return new ResponseJson(one);
    }

}
