package com.yice.edu.cn.jw.service.loc.locSchool;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.loc.LocSchool;
import com.yice.edu.cn.common.pojo.loc.LocSchoolExt;
import com.yice.edu.cn.common.pojo.loc.LocSchoolYear;
import com.yice.edu.cn.jw.dao.loc.locSchool.LocSchoolDao;
import com.yice.edu.cn.jw.dao.loc.locSchoolYear.LocSchoolYearDao;
import com.yice.edu.cn.jw.service.loc.locSchoolYear.LocSchoolYearService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LocSchoolService {
    @Autowired
    private LocSchoolDao locSchoolDao;
    @Autowired
    private SequenceId sequenceId;
    @Autowired
    private LocSchoolYearDao locSchoolYearDao;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @Transactional(readOnly = true)
    public LocSchool findLocSchoolById(String id) {
        return locSchoolDao.findLocSchoolById(id);
    }
    @Transactional
    public void saveLocSchool(LocSchool locSchool) {
        locSchool.setId(sequenceId.nextId());
        locSchoolDao.saveLocSchool(locSchool);
    }
    @Transactional(readOnly = true)
    public List<LocSchool> findLocSchoolListByCondition(LocSchool locSchool) {
        return locSchoolDao.findLocSchoolListByCondition(locSchool);
    }
    @Transactional(readOnly = true)
    public LocSchool findOneLocSchoolByCondition(LocSchool locSchool) {
        return locSchoolDao.findOneLocSchoolByCondition(locSchool);
    }
    @Transactional(readOnly = true)
    public long findLocSchoolCountByCondition(LocSchool locSchool) {
        return locSchoolDao.findLocSchoolCountByCondition(locSchool);
    }
    @Transactional
    public void updateLocSchool(LocSchool locSchool) {
        locSchoolDao.updateLocSchool(locSchool);
    }
    @Transactional
    public void updateLocSchoolForAll(LocSchool locSchool) {
        locSchoolDao.updateLocSchoolForAll(locSchool);
    }
    @Transactional
    public void deleteLocSchool(String id) {
        locSchoolDao.deleteLocSchool(id);
    }
    @Transactional
    public void deleteLocSchoolByCondition(LocSchool locSchool) {
        locSchoolDao.deleteLocSchoolByCondition(locSchool);
    }
    @Transactional
    public void batchSaveLocSchool(List<LocSchool> locSchools){
        locSchools.forEach(locSchool -> locSchool.setId(sequenceId.nextId()));
        locSchoolDao.batchSaveLocSchool(locSchools);
    }
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/


    @Transactional
    public void saveLocSchoolAndSaveLocSchoolYear(LocSchoolExt locSchoolExt){
        this.saveLocSchool(locSchoolExt);
        LocSchoolYear locSchoolYear = new LocSchoolYear();
        locSchoolYear.setId(sequenceId.nextId());
        locSchoolYear.setSchoolId(locSchoolExt.getId());
        locSchoolYear.setFromYear(locSchoolExt.getFromYear());
        locSchoolYear.setToYear(locSchoolExt.getToYear());
        locSchoolYear.setFromTo(locSchoolExt.getFromTo());
        locSchoolYear.setLastTermBegin(locSchoolExt.getLastTermBegin());
        locSchoolYear.setNextTermBegin(locSchoolExt.getNextTermBegin());
        locSchoolYearDao.saveLocSchoolYear(locSchoolYear);

    }

    @Transactional
    public void deleteLocSchoolAndLocSchoolYear(String id){
        locSchoolDao.deleteLocSchool(id);
        LocSchoolYear locSchoolYear = new LocSchoolYear();
        locSchoolYear.setSchoolId(id);
        locSchoolYearDao.deleteLocSchoolYearByCondition(locSchoolYear);
    }

    @Transactional(readOnly = true)
    public long findIpRepetitionCount(LocSchool locSchool){
        return locSchoolDao.findIpRepetitionCount(locSchool);
    }

}
