package com.yice.edu.cn.osp.feignClient.login;

import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value="jw",contextId = "loginFeign",path = "teacher")
public interface LoginFeign {

    @PostMapping("/login")
    Teacher login(Teacher teacher);
    @GetMapping("/findTeacherInfoById/{id}")
    Teacher findTeacherInfoById(@PathVariable("id") String id);
}
