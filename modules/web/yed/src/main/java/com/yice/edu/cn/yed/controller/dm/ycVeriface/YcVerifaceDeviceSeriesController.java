package com.yice.edu.cn.yed.controller.dm.ycVeriface;

import cn.hutool.core.collection.CollectionUtil;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.dm.ycVeriface.deviceSeries.YcVerifaceDeviceSeries;
import com.yice.edu.cn.yed.service.dm.ycVeriface.YcVerifaceDeviceSeriesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ycVerifaceDeviceSeries")
@Api(value = "/ycVerifaceDeviceSeries",description = "人脸识别设备系列模块")
public class YcVerifaceDeviceSeriesController {
    @Autowired
    private YcVerifaceDeviceSeriesService ycVerifaceDeviceSeriesService;

    @PostMapping("/saveYcVerifaceDeviceSeries")
    @ApiOperation(value = "保存对象", notes = "返回保存好的对象", response=YcVerifaceDeviceSeries.class)
    public ResponseJson saveYcVerifaceDeviceSeries(
            @ApiParam(value = "对象", required = true)
            @RequestBody YcVerifaceDeviceSeries ycVerifaceDeviceSeries){
        YcVerifaceDeviceSeries ycVerifaceDeviceSeries1= new YcVerifaceDeviceSeries();
        ycVerifaceDeviceSeries1.setFactoryId(ycVerifaceDeviceSeries.getFactoryId());
        ycVerifaceDeviceSeries1.setSeriesName(ycVerifaceDeviceSeries.getSeriesName());
        ycVerifaceDeviceSeries1.setSeriesType(ycVerifaceDeviceSeries.getSeriesType());
        List<YcVerifaceDeviceSeries> seriesListByCondition = ycVerifaceDeviceSeriesService.findYcVerifaceDeviceSeriesListByCondition(ycVerifaceDeviceSeries1);
        if (CollectionUtil.isNotEmpty(seriesListByCondition)){
            return new ResponseJson(false,"同一厂家设备系列不能重名");
        }
        YcVerifaceDeviceSeries s=ycVerifaceDeviceSeriesService.saveYcVerifaceDeviceSeries(ycVerifaceDeviceSeries);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findYcVerifaceDeviceSeriesById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找", notes = "返回响应对象", response=YcVerifaceDeviceSeries.class)
    public ResponseJson findYcVerifaceDeviceSeriesById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        YcVerifaceDeviceSeries ycVerifaceDeviceSeries=ycVerifaceDeviceSeriesService.findYcVerifaceDeviceSeriesById(id);
        return new ResponseJson(ycVerifaceDeviceSeries);
    }

    @PostMapping("/update/updateYcVerifaceDeviceSeries")
    @ApiOperation(value = "修改对象", notes = "返回响应对象")
    public ResponseJson updateYcVerifaceDeviceSeries(
            @ApiParam(value = "被修改的对象,对象属性不为空则修改", required = true)
            @RequestBody YcVerifaceDeviceSeries ycVerifaceDeviceSeries){
        YcVerifaceDeviceSeries ycVerifaceDeviceSeries1= new YcVerifaceDeviceSeries();
        ycVerifaceDeviceSeries1.setFactoryId(ycVerifaceDeviceSeries.getFactoryId());
        ycVerifaceDeviceSeries1.setSeriesName(ycVerifaceDeviceSeries.getSeriesName());
        ycVerifaceDeviceSeries1.setSeriesType(ycVerifaceDeviceSeries.getSeriesType());
        List<YcVerifaceDeviceSeries> seriesListByCondition = ycVerifaceDeviceSeriesService.findYcVerifaceDeviceSeriesListByCondition(ycVerifaceDeviceSeries1);
        if (CollectionUtil.isNotEmpty(seriesListByCondition)){
            return new ResponseJson(false,"同一厂家设备系列不能重名");
        }
        ycVerifaceDeviceSeriesService.updateYcVerifaceDeviceSeries(ycVerifaceDeviceSeries);
        return new ResponseJson();
    }

    @GetMapping("/look/lookYcVerifaceDeviceSeriesById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找", notes = "返回响应对象", response=YcVerifaceDeviceSeries.class)
    public ResponseJson lookYcVerifaceDeviceSeriesById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        YcVerifaceDeviceSeries ycVerifaceDeviceSeries=ycVerifaceDeviceSeriesService.findYcVerifaceDeviceSeriesById(id);
        return new ResponseJson(ycVerifaceDeviceSeries);
    }

    @PostMapping("/findYcVerifaceDeviceSeriessByCondition")
    @ApiOperation(value = "根据条件查找", notes = "返回响应对象", response=YcVerifaceDeviceSeries.class)
    public ResponseJson findYcVerifaceDeviceSeriessByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody YcVerifaceDeviceSeries ycVerifaceDeviceSeries){
        List<YcVerifaceDeviceSeries> data=ycVerifaceDeviceSeriesService.findYcVerifaceDeviceSeriesListByCondition(ycVerifaceDeviceSeries);
        long count=ycVerifaceDeviceSeriesService.findYcVerifaceDeviceSeriesCountByCondition(ycVerifaceDeviceSeries);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneYcVerifaceDeviceSeriesByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "没有时返回空", response=YcVerifaceDeviceSeries.class)
    public ResponseJson findOneYcVerifaceDeviceSeriesByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody YcVerifaceDeviceSeries ycVerifaceDeviceSeries){
        YcVerifaceDeviceSeries one=ycVerifaceDeviceSeriesService.findOneYcVerifaceDeviceSeriesByCondition(ycVerifaceDeviceSeries);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteYcVerifaceDeviceSeries/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteYcVerifaceDeviceSeries(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        ycVerifaceDeviceSeriesService.deleteYcVerifaceDeviceSeries(id);
        return new ResponseJson();
    }


    @PostMapping("/findYcVerifaceDeviceSeriesListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回响应对象,不包含总条数", response=YcVerifaceDeviceSeries.class)
    public ResponseJson findYcVerifaceDeviceSeriesListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody YcVerifaceDeviceSeries ycVerifaceDeviceSeries){
        List<YcVerifaceDeviceSeries> data=ycVerifaceDeviceSeriesService.findYcVerifaceDeviceSeriesListByCondition(ycVerifaceDeviceSeries);
        return new ResponseJson(data);
    }



}
