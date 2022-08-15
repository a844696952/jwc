package com.yice.edu.cn.dm.service.dmCamera;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.dm.dmCamera.DmCamera;
import com.yice.edu.cn.dm.dao.dmCamera.DmCameraDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DmCameraService {
    @Autowired
    private DmCameraDao dmCameraDao;
    @Autowired
    private SequenceId sequenceId;

    @Transactional(readOnly = true)
    public DmCamera findDmCameraById(String id) {
        return dmCameraDao.findDmCameraById(id);
    }
    @Transactional
    public void saveDmCamera(DmCamera dmCamera) {
        dmCamera.setId(sequenceId.nextId());
        dmCameraDao.saveDmCamera(dmCamera);
    }
    @Transactional(readOnly = true)
    public List<DmCamera> findDmCameraListByCondition(DmCamera dmCamera) {
        return dmCameraDao.findDmCameraListByCondition(dmCamera);
    }
    @Transactional(readOnly = true)
    public DmCamera findOneDmCameraByCondition(DmCamera dmCamera) {
        return dmCameraDao.findOneDmCameraByCondition(dmCamera);
    }
    @Transactional(readOnly = true)
    public long findDmCameraCountByCondition(DmCamera dmCamera) {
        return dmCameraDao.findDmCameraCountByCondition(dmCamera);
    }
    @Transactional
    public void updateDmCamera(DmCamera dmCamera) {
        dmCameraDao.updateDmCamera(dmCamera);
    }
    @Transactional
    public void deleteDmCamera(String id) {
        dmCameraDao.deleteDmCamera(id);
    }
    @Transactional
    public void deleteDmCameraByCondition(DmCamera dmCamera) {
        dmCameraDao.deleteDmCameraByCondition(dmCamera);
    }
    @Transactional
    public void batchSaveDmCamera(List<DmCamera> dmCameras){
        dmCameras.forEach(dmCamera -> dmCamera.setId(sequenceId.nextId()));
        dmCameraDao.batchSaveDmCamera(dmCameras);
    }

}
