package com.yice.edu.cn.oa.feignClient;

import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "jw",contextId = "teacherFeign",path = "/teacher")
public interface TeacherFeign {
    @GetMapping("/findTeacherById/{id}")
    Teacher findTeacherById(@PathVariable("id") String id);
}
