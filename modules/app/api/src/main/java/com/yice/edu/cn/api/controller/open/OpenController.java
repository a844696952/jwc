package com.yice.edu.cn.api.controller.open;

import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/open")
public class OpenController {
    @GetMapping("/teacherProtocol")
    @ApiOperation(value = "教师端用户协议", notes = "返回html代码")
    public String teacherProtocol(){
        return "/userProtocol/teacher.html";
    }
    @GetMapping("/studentProtocol")
    @ApiOperation(value = "学生端用户协议", notes = "返回html代码")
    public String studentProtocol(){
        return "/userProtocol/student.html";
    }
}
