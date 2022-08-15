package com.yice.edu.cn.yed.controller.system.appPerm;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.yedAdmin.AppPerm;
import com.yice.edu.cn.yed.service.system.appPerm.AppPermService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@RestController
@RequestMapping("/appPerm")
@Api(value = "/appPerm",description = "app端菜单权限模块")
public class AppPermController {
    @Autowired
    private AppPermService appPermService;

    @PostMapping("/saveAppPerm")
    @ApiOperation(value = "保存app端菜单权限对象", notes = "返回保存好的app端菜单权限对象", response=AppPerm.class)
    public ResponseJson saveAppPerm(
            @ApiParam(value = "app端菜单权限对象", required = true)
            @RequestBody AppPerm appPerm){
        AppPerm s=appPermService.saveAppPerm(appPerm);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findAppPermById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找app端菜单权限", notes = "返回响应对象", response=AppPerm.class)
    public ResponseJson findAppPermById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        AppPerm appPerm=appPermService.findAppPermById(id);
        return new ResponseJson(appPerm);
    }

    @PostMapping("/update/updateAppPerm")
    @ApiOperation(value = "修改app端菜单权限对象", notes = "返回响应对象")
    public ResponseJson updateAppPerm(
            @ApiParam(value = "被修改的app端菜单权限对象,对象属性不为空则修改", required = true)
            @RequestBody AppPerm appPerm){
        appPermService.updateAppPerm(appPerm);
        return new ResponseJson();
    }

    @GetMapping("/look/lookAppPermById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找app端菜单权限", notes = "返回响应对象", response=AppPerm.class)
    public ResponseJson lookAppPermById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        AppPerm appPerm=appPermService.findAppPermById(id);
        return new ResponseJson(appPerm);
    }

    @PostMapping("/findAppPermsByCondition")
    @ApiOperation(value = "根据条件查找app端菜单权限", notes = "返回响应对象", response=AppPerm.class)
    public ResponseJson findAppPermsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody AppPerm appPerm){
        List<AppPerm> data=appPermService.findAppPermListByCondition(appPerm);
        long count=appPermService.findAppPermCountByCondition(appPerm);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneAppPermByCondition")
    @ApiOperation(value = "根据条件查找单个app端菜单权限,结果必须为单条数据", notes = "没有时返回空", response=AppPerm.class)
    public ResponseJson findOneAppPermByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody AppPerm appPerm){
        AppPerm one=appPermService.findOneAppPermByCondition(appPerm);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteAppPerm/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteAppPerm(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        appPermService.deleteAppPerm(id);
        return new ResponseJson();
    }


    @PostMapping("/findAppPermListByCondition")
    @ApiOperation(value = "根据条件查找app端菜单权限列表", notes = "返回响应对象,不包含总条数", response=AppPerm.class)
    public ResponseJson findAppPermListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody AppPerm appPerm){
        List<AppPerm> data=appPermService.findAppPermListByCondition(appPerm);
        return new ResponseJson(data);
    }

    @GetMapping("/findAppPermAndSchoolPermKong/{whatApp}/{schoolId}")
    @ApiOperation(value = "根据条件查询某学校App端或小程序权限列表",notes = "返回权限树")
    public ResponseJson findAppPermAndSchoolPermKong(
            @ApiParam(value = "学校Id和客户端类型",required = true)
            @PathVariable("whatApp") Integer whatApp,
            @PathVariable("schoolId") String schoolId
    ){
        List<AppPerm> appPerms = appPermService.findAppPermAndSchoolPermKong(whatApp,schoolId);
        return new ResponseJson(appPerms);
    }

    @PostMapping("/updatesAppSchoolPerm/{whatApp}/{schoolId}")
    @ApiOperation(value = "根据条件修改学校小程序或App端功能展示",notes = "无返回")
    public ResponseJson updatesAppSchoolPerm(
            @PathVariable("whatApp")Integer whatApp,
            @PathVariable("schoolId")String schoolId,
            @RequestBody List<AppPerm> appPerms
    ){
        appPermService.updatesAppSchoolPerm(whatApp,schoolId,appPerms);
        return new ResponseJson();
    }


    @PostMapping("/update/updateAppPermModel/{whatApp}")
    @ApiOperation(value = "根据条件修改学校小程序或者App端默认模板",notes = "无返回")
    public ResponseJson updateAppPermModel(
            @PathVariable("whatApp") String whatApp,
            @RequestBody List<AppPerm> appPermList
    ){
        appPermService.updateAppPermModel(Integer.parseInt(whatApp),appPermList);
        return new ResponseJson();
    }
}
