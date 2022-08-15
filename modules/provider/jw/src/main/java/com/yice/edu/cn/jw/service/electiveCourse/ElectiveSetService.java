package com.yice.edu.cn.jw.service.electiveCourse;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.jw.electiveCourse.ElectiveSet;
import com.yice.edu.cn.jw.dao.electiveCourse.IElectiveSetDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ElectiveSetService {
    @Autowired
    private IElectiveSetDao electiveSetDao;
    @Autowired
    private SequenceId sequenceId;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @Transactional(readOnly = true)
    public ElectiveSet findElectiveSetById(String id) {
        return electiveSetDao.findElectiveSetById(id);
    }
    @Transactional
    public void saveElectiveSet(ElectiveSet electiveSet) {
        electiveSet.setId(sequenceId.nextId());
        electiveSetDao.saveElectiveSet(electiveSet);
    }
    @Transactional(readOnly = true)
    public List<ElectiveSet> findElectiveSetListByCondition(ElectiveSet electiveSet) {
        return electiveSetDao.findElectiveSetListByCondition(electiveSet);
    }
    @Transactional(readOnly = true)
    public ElectiveSet findOneElectiveSetByCondition(ElectiveSet electiveSet) {
        return electiveSetDao.findOneElectiveSetByCondition(electiveSet);
    }
    @Transactional(readOnly = true)
    public long findElectiveSetCountByCondition(ElectiveSet electiveSet) {
        return electiveSetDao.findElectiveSetCountByCondition(electiveSet);
    }
    @Transactional
    public void updateElectiveSet(ElectiveSet electiveSet) {
        electiveSetDao.updateElectiveSet(electiveSet);
    }
    @Transactional
    public void deleteElectiveSet(String id) {
        electiveSetDao.deleteElectiveSet(id);
    }
    @Transactional
    public void deleteElectiveSetByCondition(ElectiveSet electiveSet) {
        electiveSetDao.deleteElectiveSetByCondition(electiveSet);
    }
    @Transactional
    public void batchSaveElectiveSet(List<ElectiveSet> electiveSets){
        electiveSets.forEach(electiveSet -> electiveSet.setId(sequenceId.nextId()));
        electiveSetDao.batchSaveElectiveSet(electiveSets);
    }
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
