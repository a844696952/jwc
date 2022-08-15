package com.yice.edu.cn.jw.service.loc.locSchoolYear;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.loc.LocSchoolYear;
import com.yice.edu.cn.jw.dao.loc.locSchoolYear.LocSchoolYearDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LocSchoolYearService {
    @Autowired
    private LocSchoolYearDao locSchoolYearDao;
    @Autowired
    private SequenceId sequenceId;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @Transactional(readOnly = true)
    public LocSchoolYear findLocSchoolYearById(String id) {
        return locSchoolYearDao.findLocSchoolYearById(id);
    }
    @Transactional
    public void saveLocSchoolYear(LocSchoolYear locSchoolYear) {
        locSchoolYear.setId(sequenceId.nextId());
        locSchoolYearDao.saveLocSchoolYear(locSchoolYear);
    }
    @Transactional(readOnly = true)
    public List<LocSchoolYear> findLocSchoolYearListByCondition(LocSchoolYear locSchoolYear) {
        return locSchoolYearDao.findLocSchoolYearListByCondition(locSchoolYear);
    }
    @Transactional(readOnly = true)
    public LocSchoolYear findOneLocSchoolYearByCondition(LocSchoolYear locSchoolYear) {
        return locSchoolYearDao.findOneLocSchoolYearByCondition(locSchoolYear);
    }
    @Transactional(readOnly = true)
    public long findLocSchoolYearCountByCondition(LocSchoolYear locSchoolYear) {
        return locSchoolYearDao.findLocSchoolYearCountByCondition(locSchoolYear);
    }
    @Transactional
    public void updateLocSchoolYear(LocSchoolYear locSchoolYear) {
        locSchoolYearDao.updateLocSchoolYear(locSchoolYear);
    }
    @Transactional
    public void updateLocSchoolYearForAll(LocSchoolYear locSchoolYear) {
        locSchoolYearDao.updateLocSchoolYearForAll(locSchoolYear);
    }
    @Transactional
    public void deleteLocSchoolYear(String id) {
        locSchoolYearDao.deleteLocSchoolYear(id);
    }
    @Transactional
    public void deleteLocSchoolYearByCondition(LocSchoolYear locSchoolYear) {
        locSchoolYearDao.deleteLocSchoolYearByCondition(locSchoolYear);
    }
    @Transactional
    public void batchSaveLocSchoolYear(List<LocSchoolYear> locSchoolYears){
        locSchoolYears.forEach(locSchoolYear -> locSchoolYear.setId(sequenceId.nextId()));
        locSchoolYearDao.batchSaveLocSchoolYear(locSchoolYears);
    }
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
