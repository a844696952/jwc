package com.yice.edu.cn.jw.controller.user;

import com.yice.edu.cn.common.pojo.general.user.User;
import com.yice.edu.cn.jw.service.user.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/findUserById/{id}")
    @ApiOperation("根据id查询用户(老师或者学生)")
    public User findUserById(@PathVariable String id){
        return userService.findUserById(id);
    }
}
