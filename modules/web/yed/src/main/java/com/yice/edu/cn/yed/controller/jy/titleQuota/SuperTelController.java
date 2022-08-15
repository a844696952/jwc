package com.yice.edu.cn.yed.controller.jy.titleQuota;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jy.titleQuota.SuperTel;
import com.yice.edu.cn.yed.service.jy.titleQuota.SuperTelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/superTel")
@Api(value = "/superTel",description = "运营超级管理员手机号码维护模块")
public class SuperTelController {
    @Autowired
    private SuperTelService superTelService;

    @PostMapping("/saveSuperTel")
    @ApiOperation(value = "保存运营超级管理员手机号码维护对象", notes = "返回保存好的运营超级管理员手机号码维护对象", response=SuperTel.class)
    public ResponseJson saveSuperTel(
            @ApiParam(value = "运营超级管理员手机号码维护对象", required = true)
            @RequestBody SuperTel superTel){
        SuperTel s=superTelService.saveSuperTel(superTel);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findSuperTelById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找运营超级管理员手机号码维护", notes = "返回响应对象", response=SuperTel.class)
    public ResponseJson findSuperTelById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        SuperTel superTel=superTelService.findSuperTelById(id);
        return new ResponseJson(superTel);
    }

    @PostMapping("/update/updateSuperTel")
    @ApiOperation(value = "修改运营超级管理员手机号码维护对象非空字段", notes = "返回响应对象")
    public ResponseJson updateSuperTel(
            @ApiParam(value = "被修改的运营超级管理员手机号码维护对象,对象属性不为空则修改", required = true)
            @RequestBody SuperTel superTel){
        superTelService.updateSuperTel(superTel);
        return new ResponseJson();
    }

    @PostMapping("/update/updateSuperTelForAll")
    @ApiOperation(value = "修改运营超级管理员手机号码维护对象所有字段", notes = "返回响应对象")
    public ResponseJson updateSuperTelForAll(
            @ApiParam(value = "被修改的运营超级管理员手机号码维护对象", required = true)
            @RequestBody SuperTel superTel){
        superTelService.updateSuperTelForAll(superTel);
        return new ResponseJson();
    }

    @GetMapping("/look/lookSuperTelById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找运营超级管理员手机号码维护", notes = "返回响应对象", response=SuperTel.class)
    public ResponseJson lookSuperTelById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        SuperTel superTel=superTelService.findSuperTelById(id);
        return new ResponseJson(superTel);
    }

    @PostMapping("/findSuperTelsByCondition")
    @ApiOperation(value = "根据条件查找运营超级管理员手机号码维护", notes = "返回响应对象", response=SuperTel.class)
    public ResponseJson findSuperTelsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody SuperTel superTel){
        List<SuperTel> data=superTelService.findSuperTelListByCondition(superTel);
        long count=superTelService.findSuperTelCountByCondition(superTel);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneSuperTelByCondition")
    @ApiOperation(value = "根据条件查找单个运营超级管理员手机号码维护,结果必须为单条数据", notes = "没有时返回空", response=SuperTel.class)
    public ResponseJson findOneSuperTelByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody SuperTel superTel){
        SuperTel one=superTelService.findOneSuperTelByCondition(superTel);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteSuperTel/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteSuperTel(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        superTelService.deleteSuperTel(id);
        return new ResponseJson();
    }


    @PostMapping("/findSuperTelListByCondition")
    @ApiOperation(value = "根据条件查找运营超级管理员手机号码维护列表", notes = "返回响应对象,不包含总条数", response=SuperTel.class)
    public ResponseJson findSuperTelListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody SuperTel superTel){
        List<SuperTel> data=superTelService.findSuperTelListByCondition(superTel);
        return new ResponseJson(data);
    }



}
