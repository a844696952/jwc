package com.yice.edu.cn.dm.service.cameraManage;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.dm.cameraManage.CameraManage;
import com.yice.edu.cn.dm.dao.cameraManage.ICameraManageDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CameraManageService {
    @Autowired
    private ICameraManageDao cameraManageDao;
    @Autowired
    private SequenceId sequenceId;

    @Transactional(readOnly = true)
    public CameraManage findCameraManageById(String id) {
        return cameraManageDao.findCameraManageById(id);
    }
    @Transactional
    public void saveCameraManage(CameraManage cameraManage) {
        cameraManage.setId(sequenceId.nextId());
        cameraManageDao.saveCameraManage(cameraManage);
    }
    @Transactional(readOnly = true)
    public List<CameraManage> findCameraManageListByCondition(CameraManage cameraManage) {
        String[] searchTimeZone = cameraManage.getSearchTimeZone();
        if (searchTimeZone!=null&&searchTimeZone.length>0){
            cameraManage.setSearchStearTime(searchTimeZone[0]);
            cameraManage.setSearchEndTime(searchTimeZone[1]);
        }
        return cameraManageDao.findCameraManageListByCondition(cameraManage);
    }
    @Transactional(readOnly = true)
    public CameraManage findOneCameraManageByCondition(CameraManage cameraManage) {
        return cameraManageDao.findOneCameraManageByCondition(cameraManage);
    }
    @Transactional(readOnly = true)
    public long findCameraManageCountByCondition(CameraManage cameraManage) {
        String[] searchTimeZone = cameraManage.getSearchTimeZone();
        if (searchTimeZone!=null&&searchTimeZone.length>0){
            cameraManage.setSearchStearTime(searchTimeZone[0]);
            cameraManage.setSearchEndTime(searchTimeZone[1]);
        }
        return cameraManageDao.findCameraManageCountByCondition(cameraManage);
    }
    @Transactional
    public void updateCameraManage(CameraManage cameraManage) {
        if(cameraManage.getUrlB() == null){
            cameraManage.setUrlB("");
        }
        if(cameraManage.getUrlbName() == null){
            cameraManage.setUrlbName("");
        }
        if(cameraManage.getRemark() == null){
            cameraManage.setRemark("");
        }

        cameraManageDao.updateCameraManage(cameraManage);
    }
    @Transactional
    public void deleteCameraManage(String id) {
        cameraManageDao.deleteCameraManage(id);
    }
    @Transactional
    public void deleteCameraManageByCondition(CameraManage cameraManage) {
        cameraManageDao.deleteCameraManageByCondition(cameraManage);
    }
    @Transactional
    public void batchSaveCameraManage(List<CameraManage> cameraManages){
        cameraManages.forEach(cameraManage -> cameraManage.setId(sequenceId.nextId()));
        cameraManageDao.batchSaveCameraManage(cameraManages);
    }

    @Transactional(readOnly = true)
    public List<CameraManage> findCameraManageListByCondition2(CameraManage cameraManage) {
        return cameraManageDao.findCameraManageListByCondition2(cameraManage);
    }

}
