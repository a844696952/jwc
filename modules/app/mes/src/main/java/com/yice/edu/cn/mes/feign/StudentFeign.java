package com.yice.edu.cn.mes.feign;

import com.yice.edu.cn.common.pojo.jw.student.Student;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value = "jw", contextId = "studentFeign", path = "/student")
public interface StudentFeign {

    @GetMapping("/getStudentLoginInfo/{id}")
    Student getStudentLoginInfo(@PathVariable("id") String id);

    @PostMapping("/findStudentListByCondition")
    List<Student> findStudentListByCondition(Student student);

    @PostMapping("/findStudentListByCondition")
    List<Student> findStudentsByCondition(Student student);

    @GetMapping("/findStudentById/{id}")
    Student findStudentById(@PathVariable("id") String id);

}
