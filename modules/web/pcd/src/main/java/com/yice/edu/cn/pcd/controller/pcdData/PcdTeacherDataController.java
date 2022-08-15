package com.yice.edu.cn.pcd.controller.pcdData;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.xw.pcdData.PcdTeacherData;
import com.yice.edu.cn.pcd.interceptor.LoginInterceptor;
import com.yice.edu.cn.pcd.service.pcdData.PcdTeacherDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@RestController
@RequestMapping("/pcdTeacherData")
@Api(value = "/pcdTeacherData",description = "模块")
public class PcdTeacherDataController {
    @Autowired
    private PcdTeacherDataService pcdTeacherDataService;
    @PostMapping("/savePcdTeacherData")
    @ApiOperation(value = "保存对象", notes = "返回保存好的对象", response=PcdTeacherData.class)
    public ResponseJson savePcdTeacherData(
            @ApiParam(value = "对象", required = true)
            @RequestBody PcdTeacherData pcdTeacherData){
        pcdTeacherData.setId(LoginInterceptor.currentEehId());
        pcdTeacherDataService.savePcdTeacherDataKong(pcdTeacherData);
        return new ResponseJson();
    }

    @GetMapping("/findPcdTeacherDataById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找", notes = "返回响应对象", response=PcdTeacherData.class)
    public ResponseJson findPcdTeacherDataById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        PcdTeacherData pcdTeacherData=pcdTeacherDataService.findPcdTeacherDataById(id);
        return new ResponseJson(pcdTeacherData);
    }

    @PostMapping("/updatePcdTeacherData")
    @ApiOperation(value = "修改对象非空字段", notes = "返回响应对象")
    public ResponseJson updatePcdTeacherData(
            @ApiParam(value = "被修改的对象,对象属性不为空则修改", required = true)
            @RequestBody PcdTeacherData pcdTeacherData){
        pcdTeacherDataService.updatePcdTeacherData(pcdTeacherData);
        return new ResponseJson();
    }

    @PostMapping("/updatePcdTeacherDataForAll")
    @ApiOperation(value = "修改对象所有字段", notes = "返回响应对象")
    public ResponseJson updatePcdTeacherDataForAll(
            @ApiParam(value = "被修改的对象", required = true)
            @RequestBody PcdTeacherData pcdTeacherData){
        pcdTeacherDataService.updatePcdTeacherDataForAll(pcdTeacherData);
        return new ResponseJson();
    }


    @PostMapping("/findPcdTeacherDatasByCondition")
    @ApiOperation(value = "根据条件查找", notes = "返回响应对象", response=PcdTeacherData.class)
    public ResponseJson findPcdTeacherDatasByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody PcdTeacherData pcdTeacherData){
        List<PcdTeacherData> data=pcdTeacherDataService.findPcdTeacherDataListByCondition(pcdTeacherData);
        long count=pcdTeacherDataService.findPcdTeacherDataCountByCondition(pcdTeacherData);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOnePcdTeacherDataByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "没有时返回空", response=PcdTeacherData.class)
    public ResponseJson findOnePcdTeacherDataByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody PcdTeacherData pcdTeacherData){
        PcdTeacherData one=pcdTeacherDataService.findOnePcdTeacherDataByCondition(pcdTeacherData);
        return new ResponseJson(one);
    }
    @GetMapping("/deletePcdTeacherData/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deletePcdTeacherData(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        pcdTeacherDataService.deletePcdTeacherData(id);
        return new ResponseJson();
    }


    @PostMapping("/findPcdTeacherDataListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回响应对象,不包含总条数", response=PcdTeacherData.class)
    public ResponseJson findPcdTeacherDataListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody PcdTeacherData pcdTeacherData){
        List<PcdTeacherData> data=pcdTeacherDataService.findPcdTeacherDataListByCondition(pcdTeacherData);
        return new ResponseJson(data);
    }

    @GetMapping("/findPcdTeacherDataById")
    public ResponseJson findPcdTeacherDataById(){
        PcdTeacherData p = pcdTeacherDataService.findPcdTeacherDataById(LoginInterceptor.currentEehId());
        return new ResponseJson(p);
    }


}
