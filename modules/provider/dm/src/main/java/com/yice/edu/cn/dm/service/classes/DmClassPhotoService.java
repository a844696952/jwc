package com.yice.edu.cn.dm.service.classes;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.dm.classes.DmClassPhoto;
import com.yice.edu.cn.dm.dao.classes.IDmClassPhotoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DmClassPhotoService {
    @Autowired
    private IDmClassPhotoDao dmClassPhotoDao;
    @Autowired
    private SequenceId sequenceId;

    @Transactional(readOnly = true)
    public DmClassPhoto findDmClassPhotoById(String id) {
        return dmClassPhotoDao.findDmClassPhotoById(id);
    }
    @Transactional
    public void saveDmClassPhoto(DmClassPhoto dmClassPhoto) {
        dmClassPhoto.setId(sequenceId.nextId());
        dmClassPhotoDao.saveDmClassPhoto(dmClassPhoto);
    }
    @Transactional(readOnly = true)
    public List<DmClassPhoto> findDmClassPhotoListByCondition(DmClassPhoto dmClassPhoto) {
        return dmClassPhotoDao.findDmClassPhotoListByCondition(dmClassPhoto);
    }
    @Transactional(readOnly = true)
    public DmClassPhoto findOneDmClassPhotoByCondition(DmClassPhoto dmClassPhoto) {
        return dmClassPhotoDao.findOneDmClassPhotoByCondition(dmClassPhoto);
    }
    @Transactional(readOnly = true)
    public long findDmClassPhotoCountByCondition(DmClassPhoto dmClassPhoto) {
        return dmClassPhotoDao.findDmClassPhotoCountByCondition(dmClassPhoto);
    }
    @Transactional
    public void updateDmClassPhoto(DmClassPhoto dmClassPhoto) {
        dmClassPhotoDao.updateDmClassPhoto(dmClassPhoto);
    }
    @Transactional
    public void deleteDmClassPhoto(String id) {
        dmClassPhotoDao.deleteDmClassPhoto(id);
    }
    @Transactional
    public void deleteDmClassPhotoByCondition(DmClassPhoto dmClassPhoto) {
        dmClassPhotoDao.deleteDmClassPhotoByCondition(dmClassPhoto);
    }
    @Transactional
    public void batchSaveDmClassPhoto(List<DmClassPhoto> dmClassPhotos){
        dmClassPhotoDao.batchSaveDmClassPhoto(dmClassPhotos);
    }

    @Transactional
    public void batchDeleteDmClassPhoto(List<String> idlist) {
        dmClassPhotoDao.batchDeleteDmClassPhoto(idlist);
    }
}
