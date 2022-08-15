package com.yice.edu.cn.pcd.controller.pcdData;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.xw.pcdData.PcdTeachingData;
import com.yice.edu.cn.pcd.interceptor.LoginInterceptor;
import com.yice.edu.cn.pcd.service.pcdData.PcdTeachingDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@RestController
@RequestMapping("/pcdTeachingData")
@Api(value = "/pcdTeachingData",description = "模块")
public class PcdTeachingDataController {
    @Autowired
    private PcdTeachingDataService pcdTeachingDataService;
    @PostMapping("/savePcdTeachingData")
    @ApiOperation(value = "保存对象", notes = "返回保存好的对象", response=PcdTeachingData.class)
    public ResponseJson savePcdTeachingData(
            @ApiParam(value = "对象", required = true)
            @RequestBody PcdTeachingData pcdTeachingData){
        pcdTeachingData.setId(LoginInterceptor.currentEehId());
        pcdTeachingDataService.savePcdTeachingDataKong(pcdTeachingData);
        return new ResponseJson();
    }

    @GetMapping("/findPcdTeachingDataById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找", notes = "返回响应对象", response=PcdTeachingData.class)
    public ResponseJson findPcdTeachingDataById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        PcdTeachingData pcdTeachingData=pcdTeachingDataService.findPcdTeachingDataById(id);
        return new ResponseJson(pcdTeachingData);
    }

    @PostMapping("/updatePcdTeachingData")
    @ApiOperation(value = "修改对象非空字段", notes = "返回响应对象")
    public ResponseJson updatePcdTeachingData(
            @ApiParam(value = "被修改的对象,对象属性不为空则修改", required = true)
            @RequestBody PcdTeachingData pcdTeachingData){
        pcdTeachingDataService.updatePcdTeachingData(pcdTeachingData);
        return new ResponseJson();
    }

    @PostMapping("/updatePcdTeachingDataForAll")
    @ApiOperation(value = "修改对象所有字段", notes = "返回响应对象")
    public ResponseJson updatePcdTeachingDataForAll(
            @ApiParam(value = "被修改的对象", required = true)
            @RequestBody PcdTeachingData pcdTeachingData){
        pcdTeachingDataService.updatePcdTeachingDataForAll(pcdTeachingData);
        return new ResponseJson();
    }


    @PostMapping("/findPcdTeachingDatasByCondition")
    @ApiOperation(value = "根据条件查找", notes = "返回响应对象", response=PcdTeachingData.class)
    public ResponseJson findPcdTeachingDatasByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody PcdTeachingData pcdTeachingData){
        List<PcdTeachingData> data=pcdTeachingDataService.findPcdTeachingDataListByCondition(pcdTeachingData);
        long count=pcdTeachingDataService.findPcdTeachingDataCountByCondition(pcdTeachingData);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOnePcdTeachingDataByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "没有时返回空", response=PcdTeachingData.class)
    public ResponseJson findOnePcdTeachingDataByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody PcdTeachingData pcdTeachingData){
        PcdTeachingData one=pcdTeachingDataService.findOnePcdTeachingDataByCondition(pcdTeachingData);
        return new ResponseJson(one);
    }
    @GetMapping("/deletePcdTeachingData/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deletePcdTeachingData(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        pcdTeachingDataService.deletePcdTeachingData(id);
        return new ResponseJson();
    }


    @PostMapping("/findPcdTeachingDataListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回响应对象,不包含总条数", response=PcdTeachingData.class)
    public ResponseJson findPcdTeachingDataListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody PcdTeachingData pcdTeachingData){
        List<PcdTeachingData> data=pcdTeachingDataService.findPcdTeachingDataListByCondition(pcdTeachingData);
        return new ResponseJson(data);
    }

    @GetMapping("/findPcdTeachingDataById")
    public ResponseJson findPcdTeachingDataById(){
        PcdTeachingData p = pcdTeachingDataService.findPcdTeachingDataByIdKong(LoginInterceptor.currentEehId());
        return new ResponseJson(p);
    }

}
