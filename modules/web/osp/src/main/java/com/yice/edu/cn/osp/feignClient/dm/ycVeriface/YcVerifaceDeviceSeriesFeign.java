package com.yice.edu.cn.osp.feignClient.dm.ycVeriface;

import com.yice.edu.cn.common.pojo.dm.ycVeriface.deviceSeries.YcVerifaceDeviceSeries;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="dm",contextId = "ycVerifaceDeviceSeriesFeign",path = "/ycVerifaceDeviceSeries")
public interface YcVerifaceDeviceSeriesFeign {
    @GetMapping("/findYcVerifaceDeviceSeriesById/{id}")
    YcVerifaceDeviceSeries findYcVerifaceDeviceSeriesById(@PathVariable("id") String id);
    @PostMapping("/saveYcVerifaceDeviceSeries")
    YcVerifaceDeviceSeries saveYcVerifaceDeviceSeries(YcVerifaceDeviceSeries ycVerifaceDeviceSeries);
    @PostMapping("/findYcVerifaceDeviceSeriesListByCondition")
    List<YcVerifaceDeviceSeries> findYcVerifaceDeviceSeriesListByCondition(YcVerifaceDeviceSeries ycVerifaceDeviceSeries);
    @PostMapping("/findOneYcVerifaceDeviceSeriesByCondition")
    YcVerifaceDeviceSeries findOneYcVerifaceDeviceSeriesByCondition(YcVerifaceDeviceSeries ycVerifaceDeviceSeries);
    @PostMapping("/findYcVerifaceDeviceSeriesCountByCondition")
    long findYcVerifaceDeviceSeriesCountByCondition(YcVerifaceDeviceSeries ycVerifaceDeviceSeries);
    @PostMapping("/updateYcVerifaceDeviceSeries")
    void updateYcVerifaceDeviceSeries(YcVerifaceDeviceSeries ycVerifaceDeviceSeries);
    @GetMapping("/deleteYcVerifaceDeviceSeries/{id}")
    void deleteYcVerifaceDeviceSeries(@PathVariable("id") String id);
    @PostMapping("/deleteYcVerifaceDeviceSeriesByCondition")
    void deleteYcVerifaceDeviceSeriesByCondition(YcVerifaceDeviceSeries ycVerifaceDeviceSeries);
}
