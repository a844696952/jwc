package com.yice.edu.cn.jw.service.practice;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.jw.practice.PracticeTeacher;
import com.yice.edu.cn.jw.dao.practice.IPracticeTeacherDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PracticeTeacherService {
    @Autowired
    private IPracticeTeacherDao practiceTeacherDao;
    @Autowired
    private SequenceId sequenceId;

    @Transactional(readOnly = true)
    public PracticeTeacher findPracticeTeacherById(String id) {
        return practiceTeacherDao.findPracticeTeacherById(id);
    }
    @Transactional
    public void savePracticeTeacher(PracticeTeacher practiceTeacher) {
        practiceTeacher.setId(sequenceId.nextId());
        practiceTeacherDao.savePracticeTeacher(practiceTeacher);
    }
    @Transactional(readOnly = true)
    public List<PracticeTeacher> findPracticeTeacherListByCondition(PracticeTeacher practiceTeacher) {
        return practiceTeacherDao.findPracticeTeacherListByCondition(practiceTeacher);
    }
    @Transactional(readOnly = true)
    public PracticeTeacher findOnePracticeTeacherByCondition(PracticeTeacher practiceTeacher) {
        return practiceTeacherDao.findOnePracticeTeacherByCondition(practiceTeacher);
    }
    @Transactional(readOnly = true)
    public long findPracticeTeacherCountByCondition(PracticeTeacher practiceTeacher) {
        return practiceTeacherDao.findPracticeTeacherCountByCondition(practiceTeacher);
    }
    @Transactional
    public void updatePracticeTeacher(PracticeTeacher practiceTeacher) {
        practiceTeacherDao.updatePracticeTeacher(practiceTeacher);
    }
    @Transactional
    public void deletePracticeTeacher(String id) {
        practiceTeacherDao.deletePracticeTeacher(id);
    }
    @Transactional
    public void deletePracticeTeacherByCondition(PracticeTeacher practiceTeacher) {
        practiceTeacherDao.deletePracticeTeacherByCondition(practiceTeacher);
    }
    @Transactional
    public void batchSavePracticeTeacher(List<PracticeTeacher> practiceTeachers){
        practiceTeachers.forEach(practiceTeacher -> practiceTeacher.setId(sequenceId.nextId()));
        practiceTeacherDao.batchSavePracticeTeacher(practiceTeachers);
    }
    @Transactional
    public List<PracticeTeacher> findPracticeTeacherListById(String id){
       return practiceTeacherDao.findPracticeTeacherListById(id);
    }

    @Transactional
    public List<PracticeTeacher> findPracticeTeacherNameById(String id){
        return practiceTeacherDao.findPracticeTeacherNameById(id);
    }

}
