package com.yice.edu.cn.jw.service.electiveCourse;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.jw.electiveCourse.ElectiveClasses;
import com.yice.edu.cn.jw.dao.electiveCourse.IElectiveClassesDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ElectiveClassesService {
    @Autowired
    private IElectiveClassesDao electiveClassesDao;
    @Autowired
    private SequenceId sequenceId;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @Transactional(readOnly = true)
    public ElectiveClasses findElectiveClassesById(String id) {
        return electiveClassesDao.findElectiveClassesById(id);
    }
    @Transactional
    public void saveElectiveClasses(ElectiveClasses electiveClasses) {
        electiveClasses.setId(sequenceId.nextId());
        electiveClassesDao.saveElectiveClasses(electiveClasses);
    }
    @Transactional(readOnly = true)
    public List<ElectiveClasses> findElectiveClassesListByCondition(ElectiveClasses electiveClasses) {
        return electiveClassesDao.findElectiveClassesListByCondition(electiveClasses);
    }
    @Transactional(readOnly = true)
    public ElectiveClasses findOneElectiveClassesByCondition(ElectiveClasses electiveClasses) {
        return electiveClassesDao.findOneElectiveClassesByCondition(electiveClasses);
    }
    @Transactional(readOnly = true)
    public long findElectiveClassesCountByCondition(ElectiveClasses electiveClasses) {
        return electiveClassesDao.findElectiveClassesCountByCondition(electiveClasses);
    }
    @Transactional
    public void updateElectiveClasses(ElectiveClasses electiveClasses) {
        electiveClassesDao.updateElectiveClasses(electiveClasses);
    }
    @Transactional
    public void deleteElectiveClasses(String id) {
        electiveClassesDao.deleteElectiveClasses(id);
    }
    @Transactional
    public void deleteElectiveClassesByCondition(ElectiveClasses electiveClasses) {
        electiveClassesDao.deleteElectiveClassesByCondition(electiveClasses);
    }
    @Transactional
    public void batchSaveElectiveClasses(List<ElectiveClasses> electiveClassess){
        electiveClassess.forEach(electiveClasses -> electiveClasses.setId(sequenceId.nextId()));
        electiveClassesDao.batchSaveElectiveClasses(electiveClassess);
    }
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
