package com.yice.edu.cn.osp.service.dm.ycVeriface;

import com.yice.edu.cn.common.pojo.dm.ycVeriface.deviceSeries.YcVerifaceDeviceSeries;
import com.yice.edu.cn.osp.feignClient.dm.ycVeriface.YcVerifaceDeviceSeriesFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class YcVerifaceDeviceSeriesService {
    @Autowired
    private YcVerifaceDeviceSeriesFeign ycVerifaceDeviceSeriesFeign;

    public YcVerifaceDeviceSeries findYcVerifaceDeviceSeriesById(String id) {
        return ycVerifaceDeviceSeriesFeign.findYcVerifaceDeviceSeriesById(id);
    }

    public YcVerifaceDeviceSeries saveYcVerifaceDeviceSeries(YcVerifaceDeviceSeries ycVerifaceDeviceSeries) {
        return ycVerifaceDeviceSeriesFeign.saveYcVerifaceDeviceSeries(ycVerifaceDeviceSeries);
    }

    public List<YcVerifaceDeviceSeries> findYcVerifaceDeviceSeriesListByCondition(YcVerifaceDeviceSeries ycVerifaceDeviceSeries) {
        return ycVerifaceDeviceSeriesFeign.findYcVerifaceDeviceSeriesListByCondition(ycVerifaceDeviceSeries);
    }

    public YcVerifaceDeviceSeries findOneYcVerifaceDeviceSeriesByCondition(YcVerifaceDeviceSeries ycVerifaceDeviceSeries) {
        return ycVerifaceDeviceSeriesFeign.findOneYcVerifaceDeviceSeriesByCondition(ycVerifaceDeviceSeries);
    }

    public long findYcVerifaceDeviceSeriesCountByCondition(YcVerifaceDeviceSeries ycVerifaceDeviceSeries) {
        return ycVerifaceDeviceSeriesFeign.findYcVerifaceDeviceSeriesCountByCondition(ycVerifaceDeviceSeries);
    }

    public void updateYcVerifaceDeviceSeries(YcVerifaceDeviceSeries ycVerifaceDeviceSeries) {
        ycVerifaceDeviceSeriesFeign.updateYcVerifaceDeviceSeries(ycVerifaceDeviceSeries);
    }

    public void deleteYcVerifaceDeviceSeries(String id) {
        ycVerifaceDeviceSeriesFeign.deleteYcVerifaceDeviceSeries(id);
    }

    public void deleteYcVerifaceDeviceSeriesByCondition(YcVerifaceDeviceSeries ycVerifaceDeviceSeries) {
        ycVerifaceDeviceSeriesFeign.deleteYcVerifaceDeviceSeriesByCondition(ycVerifaceDeviceSeries);
    }
}
