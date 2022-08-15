package com.yice.edu.cn.jw.service.yed;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.jw.yed.SchoolNotified;
import com.yice.edu.cn.jw.dao.yed.ISchoolNotifiedDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SchoolNotifiedService {
    @Autowired
    private ISchoolNotifiedDao schoolNotifiedDao;
    @Autowired
    private SequenceId sequenceId;

    @Transactional(readOnly = true)
    public SchoolNotified findSchoolNotifiedById(String id) {
        return schoolNotifiedDao.findSchoolNotifiedById(id);
    }
    @Transactional
    public void saveSchoolNotified(SchoolNotified schoolNotified) {
        schoolNotified.setId(sequenceId.nextId());
        schoolNotifiedDao.saveSchoolNotified(schoolNotified);
    }
    @Transactional(readOnly = true)
    public List<SchoolNotified> findSchoolNotifiedListByCondition(SchoolNotified schoolNotified) {
        return schoolNotifiedDao.findSchoolNotifiedListByCondition(schoolNotified);
    }
    @Transactional(readOnly = true)
    public SchoolNotified findOneSchoolNotifiedByCondition(SchoolNotified schoolNotified) {
        return schoolNotifiedDao.findOneSchoolNotifiedByCondition(schoolNotified);
    }
    @Transactional(readOnly = true)
    public long findSchoolNotifiedCountByCondition(SchoolNotified schoolNotified) {
        return schoolNotifiedDao.findSchoolNotifiedCountByCondition(schoolNotified);
    }
    @Transactional
    public void updateSchoolNotified(SchoolNotified schoolNotified) {
        schoolNotifiedDao.updateSchoolNotified(schoolNotified);
    }
    @Transactional
    public void deleteSchoolNotified(String id) {
        schoolNotifiedDao.deleteSchoolNotified(id);
    }
    @Transactional
    public void deleteSchoolNotifiedByCondition(SchoolNotified schoolNotified) {
        schoolNotifiedDao.deleteSchoolNotifiedByCondition(schoolNotified);
    }
    @Transactional
    public void batchSaveSchoolNotified(List<SchoolNotified> schoolNotifieds){
        schoolNotifieds.forEach(schoolNotified -> schoolNotified.setId(sequenceId.nextId()));
        schoolNotifiedDao.batchSaveSchoolNotified(schoolNotifieds);
    }

}
