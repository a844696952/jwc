package com.yice.edu.cn.bmp.controller.appGuidance;

import com.yice.edu.cn.bmp.service.appGuidance.AppGuidanceService;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.appGuidance.AppGuidance;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appGuidance")
@Api(value = "/appGuidance",description = "模块")
public class AppGuidanceController {
    @Autowired
    private AppGuidanceService appGuidanceService;

    @PostMapping("/saveAppGuidance")
    @ApiOperation(value = "保存对象", notes = "返回响应对象")
    public ResponseJson saveAppGuidance(
            @ApiParam(value = "对象", required = true)
            @RequestBody AppGuidance appGuidance){
        AppGuidance s=appGuidanceService.saveAppGuidance(appGuidance);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findAppGuidanceById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找", notes = "返回响应对象")
    public ResponseJson findAppGuidanceById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        AppGuidance appGuidance=appGuidanceService.findAppGuidanceById(id);
        return new ResponseJson(appGuidance);
    }

    @PostMapping("/update/updateAppGuidance")
    @ApiOperation(value = "修改对象", notes = "返回响应对象")
    public ResponseJson updateAppGuidance(
            @ApiParam(value = "被修改的对象,对象属性不为空则修改", required = true)
            @RequestBody AppGuidance appGuidance){
        appGuidanceService.updateAppGuidance(appGuidance);
        return new ResponseJson();
    }

    @GetMapping("/look/lookAppGuidanceById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找", notes = "返回响应对象")
    public ResponseJson lookAppGuidanceById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        AppGuidance appGuidance=appGuidanceService.findAppGuidanceById(id);
        return new ResponseJson(appGuidance);
    }

    @PostMapping("/findAppGuidancesByCondition")
    @ApiOperation(value = "根据条件查找", notes = "返回响应对象")
    public ResponseJson findAppGuidancesByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody AppGuidance appGuidance){
        List<AppGuidance> data=appGuidanceService.findAppGuidanceListByCondition(appGuidance);
        long count=appGuidanceService.findAppGuidanceCountByCondition(appGuidance);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneAppGuidanceByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "没有时返回空")
    public ResponseJson findOneAppGuidanceByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody AppGuidance appGuidance){
        AppGuidance one=appGuidanceService.findOneAppGuidanceByCondition(appGuidance);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteAppGuidance/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteAppGuidance(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        appGuidanceService.deleteAppGuidance(id);
        return new ResponseJson();
    }


    @PostMapping("/findAppGuidanceListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回响应对象,不包含总条数")
    public ResponseJson findAppGuidanceListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody AppGuidance appGuidance){
        List<AppGuidance> data=appGuidanceService.findAppGuidanceListByCondition(appGuidance);
        return new ResponseJson(data);
    }



}
