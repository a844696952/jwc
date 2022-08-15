package com.yice.edu.cn.osp.service.dm.ycVeriface;

import com.yice.edu.cn.common.pojo.dm.ycVeriface.device.YcVerifaceDevice;
import com.yice.edu.cn.common.pojo.dm.ycVeriface.deviceLocation.YcVerifaceDeviceLocation;
import com.yice.edu.cn.osp.feignClient.dm.ycVeriface.YcVerifaceDeviceFeign;
import com.yice.edu.cn.osp.feignClient.dm.ycVeriface.YcVerifaceDeviceLocationFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class YcVerifaceDeviceLocationService {
    @Autowired
    private YcVerifaceDeviceLocationFeign ycVerifaceDeviceLocationFeign;
    @Autowired
    private YcVerifaceDeviceFeign ycVerifaceDeviceFeign;
    public YcVerifaceDeviceLocation findYcVerifaceDeviceLocationById(String id) {
        return ycVerifaceDeviceLocationFeign.findYcVerifaceDeviceLocationById(id);
    }

    public YcVerifaceDeviceLocation saveYcVerifaceDeviceLocation(YcVerifaceDeviceLocation ycVerifaceDeviceLocation) {
        return ycVerifaceDeviceLocationFeign.saveYcVerifaceDeviceLocation(ycVerifaceDeviceLocation);
    }

    public List<YcVerifaceDeviceLocation> findYcVerifaceDeviceLocationListByCondition(YcVerifaceDeviceLocation ycVerifaceDeviceLocation) {
        return ycVerifaceDeviceLocationFeign.findYcVerifaceDeviceLocationListByCondition(ycVerifaceDeviceLocation);
    }

    public YcVerifaceDeviceLocation findOneYcVerifaceDeviceLocationByCondition(YcVerifaceDeviceLocation ycVerifaceDeviceLocation) {
        return ycVerifaceDeviceLocationFeign.findOneYcVerifaceDeviceLocationByCondition(ycVerifaceDeviceLocation);
    }

    public long findYcVerifaceDeviceLocationCountByCondition(YcVerifaceDeviceLocation ycVerifaceDeviceLocation) {
        return ycVerifaceDeviceLocationFeign.findYcVerifaceDeviceLocationCountByCondition(ycVerifaceDeviceLocation);
    }

    public void updateYcVerifaceDeviceLocation(YcVerifaceDeviceLocation ycVerifaceDeviceLocation) {
        ycVerifaceDeviceLocationFeign.updateYcVerifaceDeviceLocation(ycVerifaceDeviceLocation);
    }

    public void deleteYcVerifaceDeviceLocation(String id) {
        ycVerifaceDeviceLocationFeign.deleteYcVerifaceDeviceLocation(id);
    }

    public void deleteYcVerifaceDeviceLocationByCondition(YcVerifaceDeviceLocation ycVerifaceDeviceLocation) {
        ycVerifaceDeviceLocationFeign.deleteYcVerifaceDeviceLocationByCondition(ycVerifaceDeviceLocation);
    }
}
