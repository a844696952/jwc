package com.yice.edu.cn.yed.controller.system.appPerm;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.yedAdmin.AppSchoolPerm;
import com.yice.edu.cn.yed.service.system.appPerm.AppSchoolPermService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@RestController
@RequestMapping("/appSchoolPerm")
@Api(value = "/appSchoolPerm",description = "模块")
public class AppSchoolPermController {
    @Autowired
    private AppSchoolPermService appSchoolPermService;

    @PostMapping("/saveAppSchoolPerm")
    @ApiOperation(value = "保存对象", notes = "返回保存好的对象", response=AppSchoolPerm.class)
    public ResponseJson saveAppSchoolPerm(
            @ApiParam(value = "对象", required = true)
            @RequestBody AppSchoolPerm appSchoolPerm){
       appSchoolPerm.setSchoolId(appSchoolPerm.getSchoolId());
        AppSchoolPerm s=appSchoolPermService.saveAppSchoolPerm(appSchoolPerm);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findAppSchoolPermById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找", notes = "返回响应对象", response=AppSchoolPerm.class)
    public ResponseJson findAppSchoolPermById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        AppSchoolPerm appSchoolPerm=appSchoolPermService.findAppSchoolPermById(id);
        return new ResponseJson(appSchoolPerm);
    }

    @PostMapping("/update/updateAppSchoolPerm")
    @ApiOperation(value = "修改对象", notes = "返回响应对象")
    public ResponseJson updateAppSchoolPerm(
            @ApiParam(value = "被修改的对象,对象属性不为空则修改", required = true)
            @RequestBody AppSchoolPerm appSchoolPerm){
        appSchoolPermService.updateAppSchoolPerm(appSchoolPerm);
        return new ResponseJson();
    }

    @GetMapping("/look/lookAppSchoolPermById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找", notes = "返回响应对象", response=AppSchoolPerm.class)
    public ResponseJson lookAppSchoolPermById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        AppSchoolPerm appSchoolPerm=appSchoolPermService.findAppSchoolPermById(id);
        return new ResponseJson(appSchoolPerm);
    }

    @PostMapping("/findAppSchoolPermsByCondition")
    @ApiOperation(value = "根据条件查找", notes = "返回响应对象", response=AppSchoolPerm.class)
    public ResponseJson findAppSchoolPermsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody AppSchoolPerm appSchoolPerm){
       appSchoolPerm.setSchoolId(appSchoolPerm.getSchoolId());
        List<AppSchoolPerm> data=appSchoolPermService.findAppSchoolPermListByCondition(appSchoolPerm);
        long count=appSchoolPermService.findAppSchoolPermCountByCondition(appSchoolPerm);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneAppSchoolPermByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "没有时返回空", response=AppSchoolPerm.class)
    public ResponseJson findOneAppSchoolPermByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody AppSchoolPerm appSchoolPerm){
        AppSchoolPerm one=appSchoolPermService.findOneAppSchoolPermByCondition(appSchoolPerm);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteAppSchoolPerm/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteAppSchoolPerm(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        appSchoolPermService.deleteAppSchoolPerm(id);
        return new ResponseJson();
    }


    @PostMapping("/findAppSchoolPermListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回响应对象,不包含总条数", response=AppSchoolPerm.class)
    public ResponseJson findAppSchoolPermListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody AppSchoolPerm appSchoolPerm){
       appSchoolPerm.setSchoolId(appSchoolPerm.getSchoolId());
        List<AppSchoolPerm> data=appSchoolPermService.findAppSchoolPermListByCondition(appSchoolPerm);
        return new ResponseJson(data);
    }



}
