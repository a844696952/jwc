package com.yice.edu.cn.dm.dao.dmCamera;

import java.util.List;

import com.yice.edu.cn.common.pojo.dm.dmCamera.DmCamera;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface DmCameraDao {
    List<DmCamera> findDmCameraListByCondition(DmCamera dmCamera);

    long findDmCameraCountByCondition(DmCamera dmCamera);

    DmCamera findOneDmCameraByCondition(DmCamera dmCamera);

    DmCamera findDmCameraById(@Param("id") String id);

    void saveDmCamera(DmCamera dmCamera);

    void updateDmCamera(DmCamera dmCamera);

    void deleteDmCamera(@Param("id") String id);

    void deleteDmCameraByCondition(DmCamera dmCamera);

    void batchSaveDmCamera(List<DmCamera> dmCameras);
}
