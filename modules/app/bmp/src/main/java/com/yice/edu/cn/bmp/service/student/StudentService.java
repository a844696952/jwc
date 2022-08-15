package com.yice.edu.cn.bmp.service.student;

import com.yice.edu.cn.bmp.feignClient.student.StudentFeign;
import com.yice.edu.cn.common.pojo.jw.student.Student;
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

    public long findStudentCountByCondition(Student student) {
        return studentFeign.findStudentCountByCondition(student);
    }

    public List<Student> findCorrespondencesByStudent(Student student) {
        return studentFeign.findCorrespondencesByStudent(student);
    }

    public List<Student> findStudentListByCondition(Student student) {
        return studentFeign.findStudentListByCondition(student);
    }
    public Student findOneStudentByCondition(Student student){
        return studentFeign.findOneStudentByCondition(student);
    }
    public void updateStudent(Student student) {
        studentFeign.updateStudent(student);
    }
}
