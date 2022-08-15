package com.yice.edu.cn.tap.controller.index;


import cn.hutool.crypto.digest.DigestUtil;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import com.yice.edu.cn.tap.service.teacher.TeacherService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.yice.edu.cn.tap.interceptor.LoginInterceptor.myId;

@RestController
@RequestMapping("/index")
public class IndexController {
    @Autowired
    private TeacherService teacherService;

    @PostMapping("/updateTeacherPassword")
    @ApiOperation(value = "主页修改密码，传入手机号（tel）新旧密码的值，password与newPassword需要经过md5加密后全部转成小写传入", notes = "返回更新结果")
    public ResponseJson updateTeacherAdmin(@RequestBody Teacher teacher){
        teacher.setId(myId());
        Teacher t = teacherService.findTeacherById(myId());
        String newPassword=DigestUtil.sha1Hex(teacher.getNewPassword());
        String oldPassword=DigestUtil.sha1Hex(teacher.getPassword());
        if(!t.getPassword().equals(oldPassword)){
            return new ResponseJson(false,"旧密码输入错误，请重新输入");
        }
        t.setPassword(newPassword);
        teacherService.updateTeacherAdmin(t);
        return new ResponseJson();
    }


    @GetMapping("/findTeacherById")
    @ApiOperation(value = "通过id查找教师信息", notes = "返回教师信息对象")
    public ResponseJson findTeacherById(){
        String id=myId();
        Teacher teacher= teacherService.findTeacherById(id);
        return  new ResponseJson(teacher);
    }

    @PostMapping("login/updateTeacherPassword")
    @ApiOperation(value = "给登录页面修改密码：传入手机号(tel),新密码的值(newPassword)需要经过md5加密后全部转成小写传入", notes = "返回更新结果")
    public ResponseJson updateTeacherPassword(@RequestBody Teacher teacher){
        List<Teacher> list= teacherService.findTeacherListByCondition(teacher);
        Teacher t=list.get(0);
        String newPassword=DigestUtil.sha1Hex(teacher.getNewPassword());
        t.setPassword(newPassword);
        teacherService.updateTeacherAdmin(t);
        return new ResponseJson();
    }




}
