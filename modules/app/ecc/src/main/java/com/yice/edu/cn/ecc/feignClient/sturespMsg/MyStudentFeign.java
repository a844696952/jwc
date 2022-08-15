package com.yice.edu.cn.ecc.feignClient.sturespMsg;


import com.yice.edu.cn.common.pojo.jw.student.Student;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

/**
 * chenshiping
 * @date
 */
@FeignClient(value="jw",path = "/student")
public interface MyStudentFeign {

    @PostMapping("/findOneStudentByCondition")
    Student findOneStudentByCondition(Student student);
}
