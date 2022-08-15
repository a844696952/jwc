package com.yice.edu.cn.pcd.controller.pcdData;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.xw.pcdData.PcdStudentData;
import com.yice.edu.cn.pcd.interceptor.LoginInterceptor;
import com.yice.edu.cn.pcd.service.pcdData.PcdStudentDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@RestController
@RequestMapping("/pcdStudentData")
@Api(value = "/pcdStudentData",description = "模块")
public class PcdStudentDataController {
    @Autowired
    private PcdStudentDataService pcdStudentDataService;
    @PostMapping("/savePcdStudentData")
    @ApiOperation(value = "保存对象", notes = "返回保存好的对象", response=PcdStudentData.class)
    public ResponseJson savePcdStudentData(
            @ApiParam(value = "对象", required = true)
            @RequestBody PcdStudentData pcdStudentData){
        pcdStudentData.setId(LoginInterceptor.currentEehId());
        pcdStudentDataService.savePcdStudentDataKong(pcdStudentData);
        return new ResponseJson();
    }

    @GetMapping("/findPcdStudentDataById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找", notes = "返回响应对象", response=PcdStudentData.class)
    public ResponseJson findPcdStudentDataById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        PcdStudentData pcdStudentData=pcdStudentDataService.findPcdStudentDataById(id);
        return new ResponseJson(pcdStudentData);
    }

    @PostMapping("/updatePcdStudentData")
    @ApiOperation(value = "修改对象非空字段", notes = "返回响应对象")
    public ResponseJson updatePcdStudentData(
            @ApiParam(value = "被修改的对象,对象属性不为空则修改", required = true)
            @RequestBody PcdStudentData pcdStudentData){
        pcdStudentDataService.updatePcdStudentData(pcdStudentData);
        return new ResponseJson();
    }

    @PostMapping("/updatePcdStudentDataForAll")
    @ApiOperation(value = "修改对象所有字段", notes = "返回响应对象")
    public ResponseJson updatePcdStudentDataForAll(
            @ApiParam(value = "被修改的对象", required = true)
            @RequestBody PcdStudentData pcdStudentData){
        pcdStudentDataService.updatePcdStudentDataForAll(pcdStudentData);
        return new ResponseJson();
    }


    @PostMapping("/findPcdStudentDatasByCondition")
    @ApiOperation(value = "根据条件查找", notes = "返回响应对象", response=PcdStudentData.class)
    public ResponseJson findPcdStudentDatasByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody PcdStudentData pcdStudentData){
        List<PcdStudentData> data=pcdStudentDataService.findPcdStudentDataListByCondition(pcdStudentData);
        long count=pcdStudentDataService.findPcdStudentDataCountByCondition(pcdStudentData);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOnePcdStudentDataByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "没有时返回空", response=PcdStudentData.class)
    public ResponseJson findOnePcdStudentDataByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody PcdStudentData pcdStudentData){
        PcdStudentData one=pcdStudentDataService.findOnePcdStudentDataByCondition(pcdStudentData);
        return new ResponseJson(one);
    }
    @GetMapping("/deletePcdStudentData/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deletePcdStudentData(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        pcdStudentDataService.deletePcdStudentData(id);
        return new ResponseJson();
    }


    @PostMapping("/findPcdStudentDataListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回响应对象,不包含总条数", response=PcdStudentData.class)
    public ResponseJson findPcdStudentDataListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody PcdStudentData pcdStudentData){
        List<PcdStudentData> data=pcdStudentDataService.findPcdStudentDataListByCondition(pcdStudentData);
        return new ResponseJson(data);
    }

    @GetMapping("/findPcdStudentDataById")
    public ResponseJson findPcdStudentDataById(){
        PcdStudentData p = pcdStudentDataService.findPcdStudentDataById(LoginInterceptor.currentEehId());
        return new ResponseJson(p);
    }



}
