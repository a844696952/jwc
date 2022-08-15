package com.yice.edu.cn.yed.controller.jw.appIndex;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.appIndex.AppIndex;
import com.yice.edu.cn.yed.service.jw.appIndex.AppIndexService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@RestController
@RequestMapping("/appIndex")
@Api(value = "/appIndex",description = "移动端首页模块")
public class AppIndexController {
    @Autowired
    private AppIndexService appIndexService;

    @PostMapping("/saveAppIndex")
    @ApiOperation(value = "保存移动端首页对象", notes = "返回保存好的移动端首页对象", response= AppIndex.class)
    public ResponseJson saveAppIndex(
            @ApiParam(value = "移动端首页对象", required = true)
            @RequestBody AppIndex appIndex){
        AppIndex s=appIndexService.saveAppIndex(appIndex);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findAppIndexById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找移动端首页", notes = "返回响应对象", response=AppIndex.class)
    public ResponseJson findAppIndexById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        AppIndex appIndex=appIndexService.findAppIndexById(id);
        return new ResponseJson(appIndex);
    }

    @PostMapping("/update/updateAppIndex")
    @ApiOperation(value = "修改移动端首页对象", notes = "返回响应对象")
    public ResponseJson updateAppIndex(
            @ApiParam(value = "被修改的移动端首页对象,对象属性不为空则修改", required = true)
            @RequestBody AppIndex appIndex){
        appIndexService.updateAppIndex(appIndex);
        return new ResponseJson();
    }

    @GetMapping("/look/lookAppIndexById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找移动端首页", notes = "返回响应对象", response=AppIndex.class)
    public ResponseJson lookAppIndexById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        AppIndex appIndex=appIndexService.findAppIndexById(id);
        return new ResponseJson(appIndex);
    }

    @PostMapping("/findAppIndexsByCondition")
    @ApiOperation(value = "根据条件查找移动端首页", notes = "返回响应对象", response=AppIndex.class)
    public ResponseJson findAppIndexsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody AppIndex appIndex){
        List<AppIndex> data=appIndexService.findAppIndexListByCondition(appIndex);
        long count=appIndexService.findAppIndexCountByCondition(appIndex);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneAppIndexByCondition")
    @ApiOperation(value = "根据条件查找单个移动端首页,结果必须为单条数据", notes = "没有时返回空", response=AppIndex.class)
    public ResponseJson findOneAppIndexByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody AppIndex appIndex){
        AppIndex one=appIndexService.findOneAppIndexByCondition(appIndex);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteAppIndex/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteAppIndex(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        appIndexService.deleteAppIndex(id);
        return new ResponseJson();
    }


    @PostMapping("/findAppIndexListByCondition")
    @ApiOperation(value = "根据条件查找移动端首页列表", notes = "返回响应对象,不包含总条数", response=AppIndex.class)
    public ResponseJson findAppIndexListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody AppIndex appIndex){
        List<AppIndex> data=appIndexService.findAppIndexListByCondition(appIndex);
        return new ResponseJson(data);
    }

    @GetMapping("/ignore/moveAppIndexItem/{fromIndex}/{toIndex}")
    public ResponseJson moveAppIndexItem(@PathVariable int fromIndex,@PathVariable int toIndex){
        appIndexService.moveAppIndexItem(fromIndex,toIndex,0);
        return new ResponseJson();
    }


}
