package com.yice.edu.cn.osp.service.dm.cameraManage;

import com.yice.edu.cn.common.pojo.dm.cameraManage.CameraManage;
import com.yice.edu.cn.osp.feignClient.dm.cameraManage.CameraManageFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CameraManageService {
    @Autowired
    private CameraManageFeign cameraManageFeign;

    public CameraManage findCameraManageById(String id) {
        return cameraManageFeign.findCameraManageById(id);
    }

    public CameraManage saveCameraManage(CameraManage cameraManage) {
        return cameraManageFeign.saveCameraManage(cameraManage);
    }

    public List<CameraManage> findCameraManageListByCondition(CameraManage cameraManage) {
        return cameraManageFeign.findCameraManageListByCondition(cameraManage);
    }

    public CameraManage findOneCameraManageByCondition(CameraManage cameraManage) {
        return cameraManageFeign.findOneCameraManageByCondition(cameraManage);
    }

    public long findCameraManageCountByCondition(CameraManage cameraManage) {
        return cameraManageFeign.findCameraManageCountByCondition(cameraManage);
    }

    public void updateCameraManage(CameraManage cameraManage) {
        cameraManageFeign.updateCameraManage(cameraManage);
    }

    public void deleteCameraManage(String id) {
        cameraManageFeign.deleteCameraManage(id);
    }

    public void deleteCameraManageByCondition(CameraManage cameraManage) {
        cameraManageFeign.deleteCameraManageByCondition(cameraManage);
    }


    public List<CameraManage> findCameraManageListByCondition2(CameraManage cameraManage) {
        return cameraManageFeign.findCameraManageListByCondition2(cameraManage);
    }
}
