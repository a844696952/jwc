package com.yice.edu.cn.yed.controller.dm.ycVeriface;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.dm.ycVeriface.deviceSeries.YcVerifaceDeviceSeries;
import com.yice.edu.cn.common.pojo.dm.ycVeriface.factory.YcVerifaceFactory;
import com.yice.edu.cn.yed.service.dm.ycVeriface.YcVerifaceDeviceSeriesService;
import com.yice.edu.cn.yed.service.dm.ycVeriface.YcVerifaceFactoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ycVerifaceFactory")
@Api(value = "/ycVerifaceFactory",description = "人脸识别设备厂家模块")
public class YcVerifaceFactoryController {
    @Autowired
    private YcVerifaceFactoryService ycVerifaceFactoryService;
    @Autowired
    private YcVerifaceDeviceSeriesService ycVerifaceDeviceSeriesService;
    @PostMapping("/saveYcVerifaceFactory")
    @ApiOperation(value = "保存对象", notes = "返回保存好的对象", response= YcVerifaceFactory.class)
    public ResponseJson saveYcVerifaceFactory(
            @ApiParam(value = "对象", required = true)
            @RequestBody YcVerifaceFactory ycVerifaceFactory){

        YcVerifaceFactory ycVerifaceFactory1 = new YcVerifaceFactory();
        ycVerifaceFactory1.setFactoryName(ycVerifaceFactory.getFactoryName());
        List<YcVerifaceFactory> ycVerifaceFactories = ycVerifaceFactoryService.findYcVerifaceFactoryListByCondition(ycVerifaceFactory1);
        if (CollectionUtil.isNotEmpty(ycVerifaceFactories)){
            return new ResponseJson(false,"厂家不能重复");
        }
        YcVerifaceFactory s=ycVerifaceFactoryService.saveYcVerifaceFactory(ycVerifaceFactory);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findYcVerifaceFactoryById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找", notes = "返回响应对象", response=YcVerifaceFactory.class)
    public ResponseJson findYcVerifaceFactoryById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        YcVerifaceFactory ycVerifaceFactory=ycVerifaceFactoryService.findYcVerifaceFactoryById(id);
        return new ResponseJson(ycVerifaceFactory);
    }

    @PostMapping("/update/updateYcVerifaceFactory")
    @ApiOperation(value = "修改对象", notes = "返回响应对象")
    public ResponseJson updateYcVerifaceFactory(
            @ApiParam(value = "被修改的对象,对象属性不为空则修改", required = true)
            @RequestBody YcVerifaceFactory ycVerifaceFactory){
        YcVerifaceFactory ycVerifaceFactory1 = new YcVerifaceFactory();
        ycVerifaceFactory1.setFactoryName(ycVerifaceFactory.getFactoryName());
        List<YcVerifaceFactory> ycVerifaceFactories = ycVerifaceFactoryService.findYcVerifaceFactoryListByCondition(ycVerifaceFactory1);
        if (CollectionUtil.isNotEmpty(ycVerifaceFactories)){
            return new ResponseJson(false,"厂家不能重复");
        }
        ycVerifaceFactoryService.updateYcVerifaceFactory(ycVerifaceFactory);
        return new ResponseJson();
    }

    @GetMapping("/look/lookYcVerifaceFactoryById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找", notes = "返回响应对象", response=YcVerifaceFactory.class)
    public ResponseJson lookYcVerifaceFactoryById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        YcVerifaceFactory ycVerifaceFactory=ycVerifaceFactoryService.findYcVerifaceFactoryById(id);
        return new ResponseJson(ycVerifaceFactory);
    }

    @PostMapping("/findYcVerifaceFactorysByCondition")
    @ApiOperation(value = "根据条件查找", notes = "返回响应对象", response=YcVerifaceFactory.class)
    public ResponseJson findYcVerifaceFactorysByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody YcVerifaceFactory ycVerifaceFactory){
        List<YcVerifaceFactory> data=ycVerifaceFactoryService.findYcVerifaceFactoryListByCondition(ycVerifaceFactory);
        long count=ycVerifaceFactoryService.findYcVerifaceFactoryCountByCondition(ycVerifaceFactory);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneYcVerifaceFactoryByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "没有时返回空", response=YcVerifaceFactory.class)
    public ResponseJson findOneYcVerifaceFactoryByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody YcVerifaceFactory ycVerifaceFactory){
        YcVerifaceFactory one=ycVerifaceFactoryService.findOneYcVerifaceFactoryByCondition(ycVerifaceFactory);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteYcVerifaceFactory/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteYcVerifaceFactory(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){

        //查找是否有设备系列存在
        YcVerifaceDeviceSeries ycVerifaceDeviceSeries = new YcVerifaceDeviceSeries();
        ycVerifaceDeviceSeries.setFactoryId(id);
        List<YcVerifaceDeviceSeries> ycVerifaceDeviceSeriesList = ycVerifaceDeviceSeriesService.findYcVerifaceDeviceSeriesListByCondition(ycVerifaceDeviceSeries);
        if (CollectionUtil.isNotEmpty(ycVerifaceDeviceSeriesList)){
            return new ResponseJson(false,"请先删除该厂家所有的设备系列");
        }
        ycVerifaceFactoryService.deleteYcVerifaceFactory(id);
        return new ResponseJson();
    }


    @PostMapping("/findYcVerifaceFactoryListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回响应对象,不包含总条数", response=YcVerifaceFactory.class)
    public ResponseJson findYcVerifaceFactoryListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody YcVerifaceFactory ycVerifaceFactory){
       if (ycVerifaceFactory.getPager()!=null){
           ycVerifaceFactory.getPager().setPaging(false);
       }
        List<YcVerifaceFactory> data=ycVerifaceFactoryService.findYcVerifaceFactoryListByCondition(ycVerifaceFactory);
        return new ResponseJson(data);
    }



}
