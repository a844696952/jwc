package com.yice.edu.cn.jw.service.classSchedule;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.jw.classSchedule.ClassScheduleNoonBreak;
import com.yice.edu.cn.jw.dao.classSchedule.IClassScheduleNoonBreakDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClassScheduleNoonBreakService {
    @Autowired
    private IClassScheduleNoonBreakDao classScheduleNoonBreakDao;
    @Autowired
    private SequenceId sequenceId;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @Transactional(readOnly = true)
    public ClassScheduleNoonBreak findClassScheduleNoonBreakById(String id) {
        return classScheduleNoonBreakDao.findClassScheduleNoonBreakById(id);
    }
    @Transactional
    public void saveClassScheduleNoonBreak(ClassScheduleNoonBreak classScheduleNoonBreak) {
        classScheduleNoonBreak.setId(sequenceId.nextId());
        classScheduleNoonBreakDao.saveClassScheduleNoonBreak(classScheduleNoonBreak);
    }
    @Transactional(readOnly = true)
    public List<ClassScheduleNoonBreak> findClassScheduleNoonBreakListByCondition(ClassScheduleNoonBreak classScheduleNoonBreak) {
        return classScheduleNoonBreakDao.findClassScheduleNoonBreakListByCondition(classScheduleNoonBreak);
    }
    @Transactional(readOnly = true)
    public ClassScheduleNoonBreak findOneClassScheduleNoonBreakByCondition(ClassScheduleNoonBreak classScheduleNoonBreak) {
        return classScheduleNoonBreakDao.findOneClassScheduleNoonBreakByCondition(classScheduleNoonBreak);
    }
    @Transactional(readOnly = true)
    public long findClassScheduleNoonBreakCountByCondition(ClassScheduleNoonBreak classScheduleNoonBreak) {
        return classScheduleNoonBreakDao.findClassScheduleNoonBreakCountByCondition(classScheduleNoonBreak);
    }
    @Transactional
    public void updateClassScheduleNoonBreak(ClassScheduleNoonBreak classScheduleNoonBreak) {
        classScheduleNoonBreakDao.updateClassScheduleNoonBreak(classScheduleNoonBreak);
    }
    @Transactional
    public void updateClassScheduleNoonBreakForAll(ClassScheduleNoonBreak classScheduleNoonBreak) {
        classScheduleNoonBreakDao.updateClassScheduleNoonBreakForAll(classScheduleNoonBreak);
    }
    @Transactional
    public void deleteClassScheduleNoonBreak(String id) {
        classScheduleNoonBreakDao.deleteClassScheduleNoonBreak(id);
    }
    @Transactional
    public void deleteClassScheduleNoonBreakByCondition(ClassScheduleNoonBreak classScheduleNoonBreak) {
        classScheduleNoonBreakDao.deleteClassScheduleNoonBreakByCondition(classScheduleNoonBreak);
    }
    @Transactional
    public void batchSaveClassScheduleNoonBreak(List<ClassScheduleNoonBreak> classScheduleNoonBreaks){
        classScheduleNoonBreaks.forEach(classScheduleNoonBreak -> classScheduleNoonBreak.setId(sequenceId.nextId()));
        classScheduleNoonBreakDao.batchSaveClassScheduleNoonBreak(classScheduleNoonBreaks);
    }
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/

    @Transactional
    public void updateClassScheduleNoobBreakNumber(ClassScheduleNoonBreak classScheduleNoonBreak){
        classScheduleNoonBreakDao.updateClassScheduleNoobBreakNumber(classScheduleNoonBreak);
    }
}
