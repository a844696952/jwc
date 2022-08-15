package com.yice.edu.cn.dm.service.dmLockScreenPhoto;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.dm.dmLockScreenPhoto.DmLockScreenPhoto;
import com.yice.edu.cn.dm.dao.dmLockScreenPhoto.IDmLockScreenPhotoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DmLockScreenPhotoService {
    @Autowired
    private IDmLockScreenPhotoDao dmLockScreenPhotoDao;
    @Autowired
    private SequenceId sequenceId;

    @Transactional(readOnly = true)
    public DmLockScreenPhoto findDmLockScreenPhotoById(String id) {
        return dmLockScreenPhotoDao.findDmLockScreenPhotoById(id);
    }
    @Transactional
    public void saveDmLockScreenPhoto(DmLockScreenPhoto dmLockScreenPhoto) {
        dmLockScreenPhoto.setId(sequenceId.nextId());
        dmLockScreenPhotoDao.saveDmLockScreenPhoto(dmLockScreenPhoto);
    }
    @Transactional(readOnly = true)
    public List<DmLockScreenPhoto> findDmLockScreenPhotoListByCondition(DmLockScreenPhoto dmLockScreenPhoto) {
        return dmLockScreenPhotoDao.findDmLockScreenPhotoListByCondition(dmLockScreenPhoto);
    }
    @Transactional(readOnly = true)
    public DmLockScreenPhoto findOneDmLockScreenPhotoByCondition(DmLockScreenPhoto dmLockScreenPhoto) {
        return dmLockScreenPhotoDao.findOneDmLockScreenPhotoByCondition(dmLockScreenPhoto);
    }
    @Transactional(readOnly = true)
    public long findDmLockScreenPhotoCountByCondition(DmLockScreenPhoto dmLockScreenPhoto) {
        return dmLockScreenPhotoDao.findDmLockScreenPhotoCountByCondition(dmLockScreenPhoto);
    }
    @Transactional
    public void updateDmLockScreenPhoto(DmLockScreenPhoto dmLockScreenPhoto) {
        dmLockScreenPhotoDao.updateDmLockScreenPhoto(dmLockScreenPhoto);
    }
    @Transactional
    public void deleteDmLockScreenPhoto(String id) {
        dmLockScreenPhotoDao.deleteDmLockScreenPhoto(id);
    }
    @Transactional
    public void deleteDmLockScreenPhotoByCondition(DmLockScreenPhoto dmLockScreenPhoto) {
        dmLockScreenPhotoDao.deleteDmLockScreenPhotoByCondition(dmLockScreenPhoto);
    }
    @Transactional
    public void batchSaveDmLockScreenPhoto(List<DmLockScreenPhoto> dmLockScreenPhotos){
        dmLockScreenPhotos.forEach(dmLockScreenPhoto -> dmLockScreenPhoto.setId(sequenceId.nextId()));
        dmLockScreenPhotoDao.batchSaveDmLockScreenPhoto(dmLockScreenPhotos);
    }

    @Transactional
    public void updateCurrentScreenPhoto(String id){
        dmLockScreenPhotoDao.updateStatus();
        dmLockScreenPhotoDao.updateStatusById(id);
    }

    @Transactional
    public void cancelCurrentScreenPhoto(String id){
        dmLockScreenPhotoDao.cancelCurrentScreenPhoto(id);
    }

    @Transactional
    public void batchdelete(DmLockScreenPhoto dmLockScreenPhoto){
        dmLockScreenPhotoDao.batchdelete(dmLockScreenPhoto);
    }
}
