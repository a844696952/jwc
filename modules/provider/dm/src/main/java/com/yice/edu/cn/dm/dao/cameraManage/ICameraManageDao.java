package com.yice.edu.cn.dm.dao.cameraManage;

import java.util.List;

import com.yice.edu.cn.common.pojo.dm.cameraManage.CameraManage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ICameraManageDao {
    List<CameraManage> findCameraManageListByCondition(CameraManage cameraManage);

    long findCameraManageCountByCondition(CameraManage cameraManage);

    CameraManage findOneCameraManageByCondition(CameraManage cameraManage);

    CameraManage findCameraManageById(@Param("id") String id);

    void saveCameraManage(CameraManage cameraManage);

    void updateCameraManage(CameraManage cameraManage);

    void deleteCameraManage(@Param("id") String id);

    void deleteCameraManageByCondition(CameraManage cameraManage);

    void batchSaveCameraManage(List<CameraManage> cameraManages);

    //
    List<CameraManage> findCameraManageListByCondition2(CameraManage cameraManage);
}
