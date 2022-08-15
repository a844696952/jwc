package com.yice.edu.cn.jw.service.eehManagement;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.jw.eehManagement.EehSchool;
import com.yice.edu.cn.jw.dao.eehManagement.IEehSchoolDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EehSchoolService {
    @Autowired
    private IEehSchoolDao eehSchoolDao;
    @Autowired
    private SequenceId sequenceId;

    @Transactional(readOnly = true)
    public EehSchool findEehSchoolById(String id) {
        return eehSchoolDao.findEehSchoolById(id);
    }
    @Transactional
    public void saveEehSchool(EehSchool eehSchool) {
        eehSchool.setId(sequenceId.nextId());
        eehSchoolDao.saveEehSchool(eehSchool);
    }
    @Transactional(readOnly = true)
    public List<EehSchool> findEehSchoolListByCondition(EehSchool eehSchool) {
        return eehSchoolDao.findEehSchoolListByCondition(eehSchool);
    }
    @Transactional(readOnly = true)
    public EehSchool findOneEehSchoolByCondition(EehSchool eehSchool) {
        return eehSchoolDao.findOneEehSchoolByCondition(eehSchool);
    }
    @Transactional(readOnly = true)
    public long findEehSchoolCountByCondition(EehSchool eehSchool) {
        return eehSchoolDao.findEehSchoolCountByCondition(eehSchool);
    }
    @Transactional
    public void updateEehSchool(EehSchool eehSchool) {
        eehSchoolDao.updateEehSchool(eehSchool);
    }
    @Transactional
    public void deleteEehSchool(String id) {
        eehSchoolDao.deleteEehSchool(id);
    }
    @Transactional
    public void deleteEehSchoolByCondition(EehSchool eehSchool) {
        eehSchoolDao.deleteEehSchoolByCondition(eehSchool);
    }
    @Transactional
    public void batchSaveEehSchool(List<EehSchool> eehSchools){
        eehSchools.forEach(eehSchool -> eehSchool.setId(sequenceId.nextId()));
        eehSchoolDao.batchSaveEehSchool(eehSchools);
    }


    @Transactional
    public void saveEehSchoolNew(EehSchool eehSchool) {
        this.deleteEehSchoolByCondition(eehSchool);
        for (int i = 0; i < eehSchool.getSchoolIds().length; i++) {
            eehSchool.setId(sequenceId.nextId());
            eehSchool.setSchoolId(eehSchool.getSchoolIds()[i]);
            eehSchoolDao.saveEehSchool(eehSchool);
        }
    }

    @Transactional
    public List<EehSchool> findCheckEehSchoolListById(String id){
        return  eehSchoolDao.findCheckEehSchoolListById(id);
    }

    @Transactional(readOnly = true)
    public List<EehSchool> findEehSchoolListByEehIds(List<String> eehids) {
        return eehSchoolDao.findEehSchoolListByEehIds(eehids);
    }
}
