package com.yice.edu.cn.jw.service.student;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.jw.student.StudentEnrolment;
import com.yice.edu.cn.jw.dao.student.IStudentEnrolmentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StudentEnrolmentService {
    @Autowired
    private IStudentEnrolmentDao studentEnrolmentDao;
    @Autowired
    private SequenceId sequenceId;

    @Transactional(readOnly = true)
    public StudentEnrolment findStudentEnrolmentById(String id) {
        return studentEnrolmentDao.findStudentEnrolmentById(id);
    }
    @Transactional
    public void saveStudentEnrolment(StudentEnrolment studentEnrolment) {
        studentEnrolment.setId(sequenceId.nextId());
        studentEnrolmentDao.saveStudentEnrolment(studentEnrolment);
    }
    @Transactional(readOnly = true)
    public List<StudentEnrolment> findStudentEnrolmentListByCondition(StudentEnrolment studentEnrolment) {
        return studentEnrolmentDao.findStudentEnrolmentListByCondition(studentEnrolment);
    }
    @Transactional(readOnly = true)
    public StudentEnrolment findOneStudentEnrolmentByCondition(StudentEnrolment studentEnrolment) {
        return studentEnrolmentDao.findOneStudentEnrolmentByCondition(studentEnrolment);
    }
    @Transactional(readOnly = true)
    public long findStudentEnrolmentCountByCondition(StudentEnrolment studentEnrolment) {
        return studentEnrolmentDao.findStudentEnrolmentCountByCondition(studentEnrolment);
    }
    @Transactional
    public void updateStudentEnrolment(StudentEnrolment studentEnrolment) {
        studentEnrolmentDao.updateStudentEnrolment(studentEnrolment);
    }
    @Transactional
    public void deleteStudentEnrolment(String id) {
        studentEnrolmentDao.deleteStudentEnrolment(id);
    }
    @Transactional
    public void deleteStudentEnrolmentByCondition(StudentEnrolment studentEnrolment) {
        studentEnrolmentDao.deleteStudentEnrolmentByCondition(studentEnrolment);
    }
    @Transactional
    public void batchSaveStudentEnrolment(List<StudentEnrolment> studentEnrolments){
        studentEnrolmentDao.batchSaveStudentEnrolment(studentEnrolments);
    }

}
