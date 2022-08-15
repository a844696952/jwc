package com.yice.edu.cn.pcd.controller.pcdData;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.xw.pcdData.PcdEducationData;
import com.yice.edu.cn.pcd.interceptor.LoginInterceptor;
import com.yice.edu.cn.pcd.service.pcdData.PcdEducationDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/pcdEducationData")
@Api(value = "/pcdEducationData",description = "模块")
public class PcdEducationDataController {
    @Autowired
    private PcdEducationDataService pcdEducationDataService;
    @PostMapping("/savePcdEducationData")
    @ApiOperation(value = "保存对象", notes = "返回保存好的对象", response=PcdEducationData.class)
    public ResponseJson savePcdEducationData(
            @ApiParam(value = "对象", required = true)
            @RequestBody PcdEducationData pcdEducationData){
        pcdEducationData.setId(LoginInterceptor.currentEehId());
        pcdEducationDataService.savePcdEducationDataKong(pcdEducationData);
        return new ResponseJson();
    }

    @GetMapping("/findPcdEducationDataById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找", notes = "返回响应对象", response=PcdEducationData.class)
    public ResponseJson findPcdEducationDataById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        PcdEducationData pcdEducationData=pcdEducationDataService.findPcdEducationDataById(id);
        return new ResponseJson(pcdEducationData);
    }

    @PostMapping("/updatePcdEducationData")
    @ApiOperation(value = "修改对象非空字段", notes = "返回响应对象")
    public ResponseJson updatePcdEducationData(
            @ApiParam(value = "被修改的对象,对象属性不为空则修改", required = true)
            @RequestBody PcdEducationData pcdEducationData){
        pcdEducationDataService.updatePcdEducationData(pcdEducationData);
        return new ResponseJson();
    }

    @PostMapping("/updatePcdEducationDataForAll")
    @ApiOperation(value = "修改对象所有字段", notes = "返回响应对象")
    public ResponseJson updatePcdEducationDataForAll(
            @ApiParam(value = "被修改的对象", required = true)
            @RequestBody PcdEducationData pcdEducationData){
        pcdEducationDataService.updatePcdEducationDataForAll(pcdEducationData);
        return new ResponseJson();
    }


    @PostMapping("/findPcdEducationDatasByCondition")
    @ApiOperation(value = "根据条件查找", notes = "返回响应对象", response=PcdEducationData.class)
    public ResponseJson findPcdEducationDatasByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody PcdEducationData pcdEducationData){
        List<PcdEducationData> data=pcdEducationDataService.findPcdEducationDataListByCondition(pcdEducationData);
        long count=pcdEducationDataService.findPcdEducationDataCountByCondition(pcdEducationData);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOnePcdEducationDataByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "没有时返回空", response=PcdEducationData.class)
    public ResponseJson findOnePcdEducationDataByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody PcdEducationData pcdEducationData){
        PcdEducationData one=pcdEducationDataService.findOnePcdEducationDataByCondition(pcdEducationData);
        return new ResponseJson(one);
    }
    @GetMapping("/deletePcdEducationData/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deletePcdEducationData(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        pcdEducationDataService.deletePcdEducationData(id);
        return new ResponseJson();
    }


    @PostMapping("/findPcdEducationDataListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回响应对象,不包含总条数", response=PcdEducationData.class)
    public ResponseJson findPcdEducationDataListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody PcdEducationData pcdEducationData){
        List<PcdEducationData> data=pcdEducationDataService.findPcdEducationDataListByCondition(pcdEducationData);
        return new ResponseJson(data);
    }


    @GetMapping("/findPcdEducationDataById")
    public ResponseJson findPcdEducationDataById(){
        PcdEducationData p = pcdEducationDataService.findPcdEducationDataById(LoginInterceptor.currentEehId());
        return new ResponseJson(p);
    }

    @GetMapping("/findIndexDataByEehId")
    public ResponseJson findIndexDataByEehId(){
        Map<String, Object> indexDataByEehId = pcdEducationDataService.findIndexDataByEehId(LoginInterceptor.currentEehId());
        return new ResponseJson(indexDataByEehId);
    }


}
