package com.yice.edu.cn.yed.feignClient.jw.student;

import com.yice.edu.cn.common.pojo.jw.student.Student;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="jw",contextId = "studentFeign",path = "/student")
public interface StudentFeign {
    @GetMapping("/findStudentById/{id}")
    Student findStudentById(@PathVariable("id") String id);
    @PostMapping("/saveStudent")
    Student saveStudent(Student student);
    @PostMapping("/findStudentListByCondition")
    List<Student> findStudentListByCondition(Student student);
    @PostMapping("/findStudentCountByCondition")
    long findStudentCountByCondition(Student student);
    @PostMapping("/updateStudent")
    void updateStudent(Student student);
    @GetMapping("/deleteStudent/{id}")
    void deleteStudent(@PathVariable("id") String id);
    @PostMapping("/deleteStudentByCondition")
    void deleteStudentByCondition(Student student);
    @PostMapping("/findAllSchoolStudentListByCondition")
    List<Student> findAllSchoolStudentListByCondition(Student student);
    @PostMapping("/findAllSchoolStudentCountByCondition")
    long findAllSchoolStudentCountByCondition(Student student);

}
