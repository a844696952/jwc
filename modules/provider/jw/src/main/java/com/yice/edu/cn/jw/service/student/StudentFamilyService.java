package com.yice.edu.cn.jw.service.student;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.jw.student.StudentFamily;
import com.yice.edu.cn.jw.dao.student.IStudentFamilyDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StudentFamilyService {
    @Autowired
    private IStudentFamilyDao studentFamilyDao;
    @Autowired
    private SequenceId sequenceId;

    @Transactional(readOnly = true)
    public List<StudentFamily> findStudentFamilyById(String id) {
        return studentFamilyDao.findStudentFamilyById(id);
    }
    @Transactional
    public void saveStudentFamily(StudentFamily studentFamily) {

        studentFamily.setId(sequenceId.nextId());
        studentFamilyDao.saveStudentFamily(studentFamily);
    }
    @Transactional(readOnly = true)
    public List<StudentFamily> findStudentFamilyListByCondition(StudentFamily studentFamily) {
        return studentFamilyDao.findStudentFamilyListByCondition(studentFamily);
    }
    @Transactional(readOnly = true)
    public long findStudentFamilyCountByCondition(StudentFamily studentFamily) {
        return studentFamilyDao.findStudentFamilyCountByCondition(studentFamily);
    }
    @Transactional
    public void updateStudentFamily(StudentFamily studentFamily) {
        studentFamilyDao.updateStudentFamily(studentFamily);
    }
    @Transactional
    public void deleteStudentFamily(String id) {
        studentFamilyDao.deleteStudentFamily(id);
    }
    @Transactional
    public void deleteStudentFamilyByCondition(StudentFamily studentFamily) {
        studentFamilyDao.deleteStudentFamilyByCondition(studentFamily);
    }

    @Transactional
    public void batchSaveStudentFamily(List<StudentFamily> studentFamilyList){
        studentFamilyDao.batchSaveStudentFamily(studentFamilyList);
    }
}
