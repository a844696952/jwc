package com.yice.edu.cn.dm.controller.ycVeriface;

import com.yice.edu.cn.common.pojo.dm.ycVeriface.deviceSeries.YcVerifaceDeviceSeries;
import com.yice.edu.cn.dm.service.ycVeriface.YcVerifaceDeviceSeriesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ycVerifaceDeviceSeries")
@Api(value = "/ycVerifaceDeviceSeries",description = "人脸识别设备系列")
public class YcVerifaceDeviceSeriesController {
    @Autowired
    private YcVerifaceDeviceSeriesService ycVerifaceDeviceSeriesService;

    @GetMapping("/findYcVerifaceDeviceSeriesById/{id}")
    @ApiOperation(value = "通过id查找", notes = "返回对象")
    public YcVerifaceDeviceSeries findYcVerifaceDeviceSeriesById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return ycVerifaceDeviceSeriesService.findYcVerifaceDeviceSeriesById(id);
    }

    @PostMapping("/saveYcVerifaceDeviceSeries")
    @ApiOperation(value = "保存", notes = "返回对象")
    public YcVerifaceDeviceSeries saveYcVerifaceDeviceSeries(
            @ApiParam(value = "对象", required = true)
            @RequestBody YcVerifaceDeviceSeries ycVerifaceDeviceSeries){
        ycVerifaceDeviceSeriesService.saveYcVerifaceDeviceSeries(ycVerifaceDeviceSeries);
        return ycVerifaceDeviceSeries;
    }

    @PostMapping("/findYcVerifaceDeviceSeriesListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
    public List<YcVerifaceDeviceSeries> findYcVerifaceDeviceSeriesListByCondition(
            @ApiParam(value = "对象")
            @RequestBody YcVerifaceDeviceSeries ycVerifaceDeviceSeries){
        return ycVerifaceDeviceSeriesService.findYcVerifaceDeviceSeriesListByCondition(ycVerifaceDeviceSeries);
    }
    @PostMapping("/findYcVerifaceDeviceSeriesCountByCondition")
    @ApiOperation(value = "根据条件查找列表个数", notes = "返回总个数")
    public long findYcVerifaceDeviceSeriesCountByCondition(
            @ApiParam(value = "对象")
            @RequestBody YcVerifaceDeviceSeries ycVerifaceDeviceSeries){
        return ycVerifaceDeviceSeriesService.findYcVerifaceDeviceSeriesCountByCondition(ycVerifaceDeviceSeries);
    }

    @PostMapping("/updateYcVerifaceDeviceSeries")
    @ApiOperation(value = "修改", notes = "对象必传")
    public void updateYcVerifaceDeviceSeries(
            @ApiParam(value = "对象,对象属性不为空则修改", required = true)
            @RequestBody YcVerifaceDeviceSeries ycVerifaceDeviceSeries){
        ycVerifaceDeviceSeriesService.updateYcVerifaceDeviceSeries(ycVerifaceDeviceSeries);
    }

    @GetMapping("/deleteYcVerifaceDeviceSeries/{id}")
    @ApiOperation(value = "通过id删除")
    public void deleteYcVerifaceDeviceSeries(
            @ApiParam(value = "对象", required = true)
            @PathVariable String id){
        ycVerifaceDeviceSeriesService.deleteYcVerifaceDeviceSeries(id);
    }
    @PostMapping("/deleteYcVerifaceDeviceSeriesByCondition")
    @ApiOperation(value = "根据条件删除")
    public void deleteYcVerifaceDeviceSeriesByCondition(
            @ApiParam(value = "对象")
            @RequestBody YcVerifaceDeviceSeries ycVerifaceDeviceSeries){
        ycVerifaceDeviceSeriesService.deleteYcVerifaceDeviceSeriesByCondition(ycVerifaceDeviceSeries);
    }
    @PostMapping("/findOneYcVerifaceDeviceSeriesByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "返回单个,没有时为空")
    public YcVerifaceDeviceSeries findOneYcVerifaceDeviceSeriesByCondition(
            @ApiParam(value = "对象")
            @RequestBody YcVerifaceDeviceSeries ycVerifaceDeviceSeries){
        return ycVerifaceDeviceSeriesService.findOneYcVerifaceDeviceSeriesByCondition(ycVerifaceDeviceSeries);
    }
}
