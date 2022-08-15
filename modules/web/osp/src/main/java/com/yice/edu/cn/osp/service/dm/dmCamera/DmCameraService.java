package com.yice.edu.cn.osp.service.dm.dmCamera;

import com.yice.edu.cn.common.pojo.dm.dmCamera.DmCamera;
import com.yice.edu.cn.osp.feignClient.dm.dmCamera.DmCameraFeign;
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

    public DmCamera saveDmCamera(DmCamera dmCamera) {

        return dmCameraFeign.saveDmCamera(dmCamera);
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

    public void updateDmCamera(DmCamera dmCamera) {
        dmCameraFeign.updateDmCamera(dmCamera);
    }

    public void deleteDmCamera(String id) {
        dmCameraFeign.deleteDmCamera(id);
    }

    public void deleteDmCameraByCondition(DmCamera dmCamera) {
        dmCameraFeign.deleteDmCameraByCondition(dmCamera);
    }
}
