package com.yice.edu.cn.jw.service.appIndex;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.jw.appIndex.SchoolAppIndex;
import com.yice.edu.cn.jw.dao.appIndex.ISchoolAppIndexDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Consumer;

@Service
public class SchoolAppIndexService {
    @Autowired
    private ISchoolAppIndexDao schoolAppIndexDao;
    @Autowired
    private SequenceId sequenceId;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @Transactional(readOnly = true)
    public SchoolAppIndex findSchoolAppIndexById(String id) {
        return schoolAppIndexDao.findSchoolAppIndexById(id);
    }
    @Transactional
    public void saveSchoolAppIndex(SchoolAppIndex schoolAppIndex) {
        schoolAppIndex.setId(sequenceId.nextId());
        schoolAppIndexDao.saveSchoolAppIndex(schoolAppIndex);
    }
    @Transactional(readOnly = true)
    public List<SchoolAppIndex> findSchoolAppIndexListByCondition(SchoolAppIndex schoolAppIndex) {
        return schoolAppIndexDao.findSchoolAppIndexListByCondition(schoolAppIndex);
    }
    @Transactional(readOnly = true)
    public SchoolAppIndex findOneSchoolAppIndexByCondition(SchoolAppIndex schoolAppIndex) {
        return schoolAppIndexDao.findOneSchoolAppIndexByCondition(schoolAppIndex);
    }
    @Transactional(readOnly = true)
    public long findSchoolAppIndexCountByCondition(SchoolAppIndex schoolAppIndex) {
        return schoolAppIndexDao.findSchoolAppIndexCountByCondition(schoolAppIndex);
    }
    @Transactional
    public void updateSchoolAppIndex(SchoolAppIndex schoolAppIndex) {
        schoolAppIndexDao.updateSchoolAppIndex(schoolAppIndex);
    }
    @Transactional
    public void deleteSchoolAppIndex(String id) {
        schoolAppIndexDao.deleteSchoolAppIndex(id);
    }
    @Transactional
    public void deleteSchoolAppIndexByCondition(SchoolAppIndex schoolAppIndex) {
        schoolAppIndexDao.deleteSchoolAppIndexByCondition(schoolAppIndex);
    }
    @Transactional
    public void batchSaveSchoolAppIndex(List<SchoolAppIndex> schoolAppIndexs){
        schoolAppIndexs.forEach(schoolAppIndex -> schoolAppIndex.setId(sequenceId.nextId()));
        schoolAppIndexDao.batchSaveSchoolAppIndex(schoolAppIndexs);
    }

    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
    @Transactional
    public void moveAppIndexes(List<SchoolAppIndex> schoolAppIndices) {
        schoolAppIndices.forEach(schoolAppIndex -> schoolAppIndex.setId(sequenceId.nextId()));
        schoolAppIndexDao.moveAppIndexes(schoolAppIndices);
    }
    @Transactional
    public void upsertSchoolAppIndex(SchoolAppIndex schoolAppIndex) {
        schoolAppIndex.setId(sequenceId.nextId());
        schoolAppIndexDao.upsertSchoolAppIndex(schoolAppIndex);
    }
}
