package com.yice.edu.cn.osp.controller.jy.cloudClassroom.otherSchoolAccount;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.cc.otherSchoolAccount.OtherSchoolAccount;
import com.yice.edu.cn.osp.service.jy.cloudClassroom.otherSchoolAccount.OtherSchoolAccountService;

import cn.hutool.core.date.DateUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import java.util.List;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/otherSchoolAccount")
@Api(value = "/otherSchoolAccount",description = "外校账号模块")
public class OtherSchoolAccountController {
    @Autowired
    private OtherSchoolAccountService otherSchoolAccountService;

    @PostMapping("/saveOtherSchoolAccount")
    @ApiOperation(value = "保存外校账号对象", notes = "返回保存好的外校账号对象", response= OtherSchoolAccount.class)
    public ResponseJson saveOtherSchoolAccount(
            @ApiParam(value = "外校账号对象", required = true)
            @RequestBody OtherSchoolAccount otherSchoolAccount){
       otherSchoolAccount.setSchoolId(mySchoolId());
       //需要判断账号是否重复了
        OtherSchoolAccount cond = new OtherSchoolAccount();
        cond.setName(otherSchoolAccount.getName());
        OtherSchoolAccount exist = otherSchoolAccountService.findOneOtherSchoolAccountByCondition(cond);
        if(exist!=null){
            return new ResponseJson(false,"系统已存在同名外校,请重新输入");
        }
        OtherSchoolAccount s=otherSchoolAccountService.saveOtherSchoolAccount(otherSchoolAccount);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findOtherSchoolAccountById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找外校账号", notes = "返回响应对象", response=OtherSchoolAccount.class)
    public ResponseJson findOtherSchoolAccountById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        OtherSchoolAccount otherSchoolAccount=otherSchoolAccountService.findOtherSchoolAccountById(id);
        return new ResponseJson(otherSchoolAccount);
    }

    @PostMapping("/update/updateOtherSchoolAccount")
    @ApiOperation(value = "修改外校账号对象", notes = "返回响应对象")
    public ResponseJson updateOtherSchoolAccount(
            @ApiParam(value = "被修改的外校账号对象,对象属性不为空则修改", required = true)
            @RequestBody OtherSchoolAccount otherSchoolAccount){
        //需要判断账号是否重复了
        OtherSchoolAccount cond = new OtherSchoolAccount();
        cond.setName(otherSchoolAccount.getName());
        OtherSchoolAccount exist = otherSchoolAccountService.findOneOtherSchoolAccountByCondition(cond);
        if(!exist.getId().equals(otherSchoolAccount.getId())){
            return new ResponseJson(false,"系统已存在同名外校,请重新输入");
        }
        otherSchoolAccountService.updateOtherSchoolAccount(otherSchoolAccount);
        return new ResponseJson();
    }

    @GetMapping("/look/lookOtherSchoolAccountById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找外校账号", notes = "返回响应对象", response=OtherSchoolAccount.class)
    public ResponseJson lookOtherSchoolAccountById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        OtherSchoolAccount otherSchoolAccount=otherSchoolAccountService.findOtherSchoolAccountById(id);
        return new ResponseJson(otherSchoolAccount);
    }

    @PostMapping("/findOtherSchoolAccountsByCondition")
    @ApiOperation(value = "根据条件查找外校账号", notes = "返回响应对象", response=OtherSchoolAccount.class)
    public ResponseJson findOtherSchoolAccountsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody OtherSchoolAccount otherSchoolAccount){
       otherSchoolAccount.setSchoolId(mySchoolId());
       if("1".equals(otherSchoolAccount.getStatus())) {
           otherSchoolAccount.getPager().setRangeField("expireDate").setRangeArray(new String[]{DateUtil.today(),null});
       }
       if("2".equals(otherSchoolAccount.getStatus())) {
           otherSchoolAccount.getPager().setRangeField("expireDate").setRangeArray(new String[]{null,DateUtil.yesterday().toDateStr()});
       }
        List<OtherSchoolAccount> data=otherSchoolAccountService.findOtherSchoolAccountListByCondition(otherSchoolAccount);
        long count=otherSchoolAccountService.findOtherSchoolAccountCountByCondition(otherSchoolAccount);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneOtherSchoolAccountByCondition")
    @ApiOperation(value = "根据条件查找单个外校账号,结果必须为单条数据", notes = "没有时返回空", response=OtherSchoolAccount.class)
    public ResponseJson findOneOtherSchoolAccountByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody OtherSchoolAccount otherSchoolAccount){
        OtherSchoolAccount one=otherSchoolAccountService.findOneOtherSchoolAccountByCondition(otherSchoolAccount);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteOtherSchoolAccount/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteOtherSchoolAccount(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        otherSchoolAccountService.deleteOtherSchoolAccount(id);
        return new ResponseJson();
    }


    @PostMapping("/findOtherSchoolAccountListByCondition")
    @ApiOperation(value = "根据条件查找外校账号列表", notes = "返回响应对象,不包含总条数", response=OtherSchoolAccount.class)
    public ResponseJson findOtherSchoolAccountListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody OtherSchoolAccount otherSchoolAccount){
       otherSchoolAccount.setSchoolId(mySchoolId());
        List<OtherSchoolAccount> data=otherSchoolAccountService.findOtherSchoolAccountListByCondition(otherSchoolAccount);
        return new ResponseJson(data);
    }

}
