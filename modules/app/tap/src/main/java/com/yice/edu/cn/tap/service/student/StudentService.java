package com.yice.edu.cn.tap.service.student;

import com.yice.edu.cn.common.pojo.jw.student.Student;
import com.yice.edu.cn.tap.feignClient.student.StudentFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
    @Autowired
    private StudentFeign studentFeign;

    public Student findStudentById(String id) {
        return studentFeign.findStudentById(id);
    }
    public List<Student> findStudentListByCondition(Student student) {
        return studentFeign.findStudentListByCondition(student);
    }

    public long findStudentCountByCondition(Student student) {
        return studentFeign.findStudentCountByCondition(student);
    }

    public List<Student> findStudentsByCondition(Student student){
        return studentFeign.findStudentsByCondition(student);
    }

    public long findStudentListCountByCondition(Student student) {
        return studentFeign.findStudentListCountByCondition(student);
    }

    public List<Student> findCorrespondencesByStudent(Student student) {
        return studentFeign.findCorrespondencesByStudent(student);
    }


    public List<Student> findTeacherClassList(Student student) {
        return studentFeign.findTeacherClassList(student);
    }

    public List<Student> findClassStudentByclassTitle(Student student) {
        return studentFeign.findClassStudentByclassTitle(student);
    }

    public List<Student> findStudentByTitleim(Student student) {
        return studentFeign.findStudentByTitleim(student);
    }

    public List<Student> findTeacherClassListim(Student student) {
        return studentFeign.findTeacherClassListim(student);
    }

    public List<Student> findStudentListByConditionim(Student s) {
        return studentFeign.findStudentListByConditionim(s);
    }


    public List<Student> findStudentInfoAndFamily(Student student) {
        return studentFeign.findStudentInfoAndFamily(student);
    }
    public void updateStudent(Student student) {
        studentFeign.updateStudent(student);
    }

}
