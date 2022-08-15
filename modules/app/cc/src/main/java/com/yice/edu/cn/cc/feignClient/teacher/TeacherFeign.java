package com.yice.edu.cn.cc.feignClient.teacher;

import com.yice.edu.cn.common.pojo.jw.teacher.LoginObj;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "jw",path = "/teacher")
public interface TeacherFeign {
    @PostMapping("/ccLogin")
    Teacher ccLogin(LoginObj loginObj);
}
