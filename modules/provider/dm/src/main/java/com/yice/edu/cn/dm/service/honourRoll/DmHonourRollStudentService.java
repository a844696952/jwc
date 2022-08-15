package com.yice.edu.cn.dm.service.honourRoll;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.dm.classCard.DmDeleteData;
import com.yice.edu.cn.common.pojo.dm.honourRoll.DmHonourRollStudent;
import com.yice.edu.cn.common.pojo.dm.honourRoll.EccHonourRoll;
import com.yice.edu.cn.dm.dao.honourRoll.DmHonourRollStudentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DmHonourRollStudentService {
    @Autowired
    private DmHonourRollStudentDao dmHonourRollStudentDao;
    @Autowired
    private SequenceId sequenceId;

    @Transactional(readOnly = true)
    public DmHonourRollStudent findDmHonourRollStudentById(String id) {
        return dmHonourRollStudentDao.findDmHonourRollStudentById(id);
    }
    @Transactional
    public void saveDmHonourRollStudent(DmHonourRollStudent dmHonourRollStudent) {
        dmHonourRollStudent.setId(sequenceId.nextId());
        dmHonourRollStudentDao.saveDmHonourRollStudent(dmHonourRollStudent);
    }
    @Transactional(readOnly = true)
    public List<DmHonourRollStudent> findDmHonourRollStudentListByCondition(DmHonourRollStudent dmHonourRollStudent) {
        return dmHonourRollStudentDao.findDmHonourRollStudentListByCondition(dmHonourRollStudent);
    }
    @Transactional(readOnly = true)
    public DmHonourRollStudent findOneDmHonourRollStudentByCondition(DmHonourRollStudent dmHonourRollStudent) {
        return dmHonourRollStudentDao.findOneDmHonourRollStudentByCondition(dmHonourRollStudent);
    }
    @Transactional(readOnly = true)
    public long findDmHonourRollStudentCountByCondition(DmHonourRollStudent dmHonourRollStudent) {
        return dmHonourRollStudentDao.findDmHonourRollStudentCountByCondition(dmHonourRollStudent);
    }
    @Transactional
    public void updateDmHonourRollStudent(DmHonourRollStudent dmHonourRollStudent) {
        dmHonourRollStudentDao.updateDmHonourRollStudent(dmHonourRollStudent);
    }
    @Transactional
    public void deleteDmHonourRollStudent(String id) {
        dmHonourRollStudentDao.deleteDmHonourRollStudent(id);
    }
    @Transactional
    public void deleteDmHonourRollStudentByCondition(DmHonourRollStudent dmHonourRollStudent) {
        dmHonourRollStudentDao.deleteDmHonourRollStudentByCondition(dmHonourRollStudent);
    }
    @Transactional
    public void batchSaveDmHonourRollStudent(List<DmHonourRollStudent> dmHonourRollStudents){
        dmHonourRollStudents.forEach(dmHonourRollStudent -> dmHonourRollStudent.setId(sequenceId.nextId()));
        dmHonourRollStudentDao.batchSaveDmHonourRollStudent(dmHonourRollStudents);
    }
    @Transactional(readOnly = true)
    public List<DmHonourRollStudent> findDmHonourRollStudentListByConditions(DmHonourRollStudent dmHonourRollStudent) {
        return dmHonourRollStudentDao.findDmHonourRollStudentListByConditions(dmHonourRollStudent);
    }
    @Transactional(readOnly = true)
    public long findDmHonourRollStudentCountByConditions(DmHonourRollStudent dmHonourRollStudent) {
        return dmHonourRollStudentDao.findDmHonourRollStudentCountByConditions(dmHonourRollStudent);
    }
    @Transactional(readOnly = true)
    public List<EccHonourRoll> getHonourRollList(DmHonourRollStudent dmHonourRollStudent) {
        return dmHonourRollStudentDao.getHonourRollList(dmHonourRollStudent);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteDmHonourRollStudentByClassId(DmDeleteData dmDeleteData){
        dmHonourRollStudentDao.deleteDmHonourRollStudentByClassId(dmDeleteData);
    }
}
