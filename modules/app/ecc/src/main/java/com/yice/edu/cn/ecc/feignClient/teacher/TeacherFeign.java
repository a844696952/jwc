package com.yice.edu.cn.ecc.feignClient.teacher;

import com.yice.edu.cn.common.pojo.jw.teacher.Teacher4Classes;
import com.yice.edu.cn.common.pojo.jw.teacher.TeacherClasses;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(value = "jw",contextId = "teacherFeign",path = "/teacherClasses")
public interface TeacherFeign {

    @PostMapping("/findClassTeacherListByClasses")
    List<Teacher4Classes> findClassTeacherListByClasses(@RequestBody TeacherClasses teacherClasses);
}
