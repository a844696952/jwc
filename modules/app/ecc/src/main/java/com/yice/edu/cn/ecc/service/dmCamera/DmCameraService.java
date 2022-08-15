package com.yice.edu.cn.ecc.service.dmCamera;

import com.yice.edu.cn.common.pojo.dm.dmCamera.DmCamera;
import com.yice.edu.cn.ecc.feignClient.dmCamera.DmCameraFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DmCameraService {
    @Autowired
    private DmCameraFeign dmCameraFeign;

    public DmCamera findDmCameraById(String id) {
        return dmCameraFeign.findDmCameraById(id);
    }

    public List<DmCamera> findDmCameraListByCondition(DmCamera dmCamera) {
        return dmCameraFeign.findDmCameraListByCondition(dmCamera);
    }

    public DmCamera findOneDmCameraByCondition(DmCamera dmCamera) {
        return dmCameraFeign.findOneDmCameraByCondition(dmCamera);
    }

    public long findDmCameraCountByCondition(DmCamera dmCamera) {
        return dmCameraFeign.findDmCameraCountByCondition(dmCamera);
    }

}
