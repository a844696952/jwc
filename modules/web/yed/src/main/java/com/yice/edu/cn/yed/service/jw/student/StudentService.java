package com.yice.edu.cn.yed.service.jw.student;

import com.yice.edu.cn.common.pojo.jw.student.Student;
import com.yice.edu.cn.yed.feignClient.jw.student.StudentFeign;
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

    public Student saveStudent(Student student) {
        return studentFeign.saveStudent(student);
    }

    public List<Student> findStudentListByCondition(Student student) {
        return studentFeign.findStudentListByCondition(student);
    }

    public long findStudentCountByCondition(Student student) {
        return studentFeign.findStudentCountByCondition(student);
    }

    public void updateStudent(Student student) {
        studentFeign.updateStudent(student);
    }

    public void deleteStudent(String id) {
        studentFeign.deleteStudent(id);
    }

    public void deleteStudentByCondition(Student student) {
        studentFeign.deleteStudentByCondition(student);
    }
    //按条件查询所有学校的学生信息
    public List<Student> findAllSchoolStudentListByCondition(Student student){
        return studentFeign.findAllSchoolStudentListByCondition(student);
    }
    public long findAllSchoolStudentCountByCondition(Student student) {
        return studentFeign.findAllSchoolStudentCountByCondition(student);
    }
}
