package com.yice.edu.cn.osp.feignClient.dm.cameraManage;

import com.yice.edu.cn.common.pojo.dm.cameraManage.CameraManage;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="dm",contextId = "cameraManageFeign",path = "/cameraManage")
public interface CameraManageFeign {
    @GetMapping("/findCameraManageById/{id}")
    CameraManage findCameraManageById(@PathVariable("id") String id);
    @PostMapping("/saveCameraManage")
    CameraManage saveCameraManage(CameraManage cameraManage);
    @PostMapping("/findCameraManageListByCondition")
    List<CameraManage> findCameraManageListByCondition(CameraManage cameraManage);
    @PostMapping("/findOneCameraManageByCondition")
    CameraManage findOneCameraManageByCondition(CameraManage cameraManage);
    @PostMapping("/findCameraManageCountByCondition")
    long findCameraManageCountByCondition(CameraManage cameraManage);
    @PostMapping("/updateCameraManage")
    void updateCameraManage(CameraManage cameraManage);
    @GetMapping("/deleteCameraManage/{id}")
    void deleteCameraManage(@PathVariable("id") String id);
    @PostMapping("/deleteCameraManageByCondition")
    void deleteCameraManageByCondition(CameraManage cameraManage);

    //
    @PostMapping("/findCameraManageListByCondition2")
    List<CameraManage> findCameraManageListByCondition2(CameraManage cameraManage);
}
