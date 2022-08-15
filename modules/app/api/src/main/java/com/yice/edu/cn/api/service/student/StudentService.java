package com.yice.edu.cn.api.service.student;

import com.yice.edu.cn.common.pojo.jw.student.Student;
import com.yice.edu.cn.api.feign.student.StudentFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
    @Autowired
    private StudentFeign studentFeign;

    public List<Student> findStudentListByCondition(Student student) {
        return studentFeign.findStudentListByCondition(student);
    }

    public Student findStudentById(String id) {
        return studentFeign.findStudentById(id);
    }
}
