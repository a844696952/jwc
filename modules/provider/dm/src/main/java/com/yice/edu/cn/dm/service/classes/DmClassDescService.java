package com.yice.edu.cn.dm.service.classes;


import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.dm.classCard.DmClassCard;
import com.yice.edu.cn.common.pojo.dm.classes.DmClassPhoto;
import com.yice.edu.cn.common.pojo.dm.classes.DmClassVideo;
import com.yice.edu.cn.common.pojo.jw.classes.JwClasses;
import com.yice.edu.cn.dm.dao.classes.IDmClassPhotoDao;
import com.yice.edu.cn.dm.dao.classes.IDmClassVideoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.yice.edu.cn.dm.dao.classes.IDmClassDescDao;
import com.yice.edu.cn.common.pojo.dm.classes.DmClassDesc;

import java.util.List;

@Service
public class DmClassDescService {
    @Autowired
    private IDmClassDescDao dmClassDescDao;
    @Autowired
    private IDmClassPhotoDao dmClassPhotoDao;
    @Autowired
    private IDmClassVideoDao dmClassVideoDao;

    @Autowired
    private SequenceId sequenceId;

    @Transactional(readOnly = true)
    public DmClassDesc findDmClassDescById(String id) {
        return dmClassDescDao.findDmClassDescById(id);
    }
    @Transactional
    public void saveDmClassDesc(DmClassDesc dmClassDesc) {
        DmClassDesc dd = this.findOneDmClassDescByCondition(dmClassDesc);
        if(dd==null) {
            dmClassDesc.setId(sequenceId.nextId());
            dmClassDescDao.saveDmClassDesc(dmClassDesc);
        }else{
            this.updateDmClassDesc(dmClassDesc);
        }
    }
    @Transactional(readOnly = true)
    public List<DmClassDesc> findDmClassDescListByCondition(DmClassDesc dmClassDesc) {
        return dmClassDescDao.findDmClassDescListByCondition(dmClassDesc);
    }
    @Transactional(readOnly = true)
    public DmClassDesc findOneDmClassDescByCondition(DmClassDesc dmClassDesc) {
        return dmClassDescDao.findOneDmClassDescByCondition(dmClassDesc);
    }
    @Transactional(readOnly = true)
    public long findDmClassDescCountByCondition(DmClassDesc dmClassDesc) {
        return dmClassDescDao.findDmClassDescCountByCondition(dmClassDesc);
    }
    @Transactional
    public void updateDmClassDesc(DmClassDesc dmClassDesc) {
        if(dmClassDesc!=null){
            if(dmClassDesc.getClassAlias() == null){
                dmClassDesc.setClassAlias("");
            }
        }
        dmClassDescDao.updateDmClassDesc(dmClassDesc);
    }
    @Transactional
    public void deleteDmClassDesc(String id) {
        dmClassDescDao.deleteDmClassDesc(id);
    }
    @Transactional
    public void deleteDmClassDescByCondition(DmClassDesc dmClassDesc) {
        dmClassDescDao.deleteDmClassDescByCondition(dmClassDesc);
    }
    @Transactional
    public void batchSaveDmClassDesc(List<DmClassDesc> dmClassDescs){
        dmClassDescDao.batchSaveDmClassDesc(dmClassDescs);
    }

    @Transactional
    public void clearClassDescAndPhoto(String classId){
        DmClassDesc dmClassDesc = new DmClassDesc();
        dmClassDesc.setClassId(classId);
        dmClassDesc = dmClassDescDao.findOneDmClassDescByCondition(dmClassDesc);
        if(dmClassDesc!=null){
            //因升班需要，班级信息清除后，需要删除整个记录
            dmClassDescDao.deleteDmClassDescByCondition(dmClassDesc);

            DmClassPhoto photo = new DmClassPhoto();
            photo.setClassId(dmClassDesc.getClassId());
            photo.setSchoolId(dmClassDesc.getSchoolId());
            dmClassPhotoDao.deleteDmClassPhotoByCondition(photo);

            DmClassVideo video = new DmClassVideo();
            video.setClassId(dmClassDesc.getClassId());
            video.setSchoolId(dmClassDesc.getSchoolId());
            dmClassVideoDao.deleteDmClassVideoByCondition(video);
        }
    }

    @Transactional
    public void batchClearClassDescAndPhoto(String classIds){
        String[] arr = classIds.split(",");
        for(String classId : arr){
            this.clearClassDescAndPhoto(classId);
        }
    }

    public List<JwClasses> findJwClassesListByCardCondition(JwClasses jwClasses) {
        return dmClassDescDao.findJwClassesListByCardCondition(jwClasses);
    }
    public List<JwClasses> findDmClassesListByCardCondition(JwClasses jwClasses) {
        return dmClassDescDao.findDmClassesListByCardCondition(jwClasses);
    }

    public long findJwClassesCountByCardCondition(JwClasses jwClasses) {
        return dmClassDescDao.findJwClassesCountByCardCondition(jwClasses);
    }
    public long findDmClassesCountByCardCondition(JwClasses jwClasses) {
        return dmClassDescDao.findDmClassesCountByCardCondition(jwClasses);
    }
}
