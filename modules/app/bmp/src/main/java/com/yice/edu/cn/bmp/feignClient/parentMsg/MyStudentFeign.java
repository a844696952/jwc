package com.yice.edu.cn.bmp.feignClient.parentMsg;


import com.yice.edu.cn.common.pojo.jw.student.Student;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * chenshiping
 * @date
 */
@FeignClient(value="jw",path = "/student")
public interface MyStudentFeign {

    @PostMapping("/findOneStudentByCondition")
    Student findOneStudentByCondition(Student student);

    @GetMapping("/findStudentById/{id}")
    public Student findStudentById(@PathVariable String id);
}
