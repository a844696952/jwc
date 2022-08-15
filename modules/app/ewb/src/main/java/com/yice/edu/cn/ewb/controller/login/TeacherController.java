package com.yice.edu.cn.ewb.controller.login;


import cn.hutool.core.date.DateUtil;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.json.JSONUtil;
import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CreateCache;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.parent.Parent;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import com.yice.edu.cn.common.pojo.jw.teacher.LoginErrorInfo;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import com.yice.edu.cn.common.pojo.jw.teacher.TeacherClasses;
import com.yice.edu.cn.common.pojo.validateClass.GroupOne;
import com.yice.edu.cn.common.util.jwt.JwtUtil;
import com.yice.edu.cn.ewb.service.parent.ParentService;
import com.yice.edu.cn.ewb.service.student.StudentService;
import com.yice.edu.cn.ewb.service.teacher.TeacherClassesService;
import com.yice.edu.cn.ewb.service.teacher.TeacherService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.yice.edu.cn.ewb.interceptor.LoginInterceptor.myId;


@RestController
@RequestMapping("/teacher")
@Validated
//@RefreshScope
public class TeacherController {
    @Autowired
    TeacherClassesService teacherClassesService;
    @Autowired
    private TeacherService teacherService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private ParentService parentService;
    @PostMapping("/findTeacherCountByCondition")
    @ApiOperation(value = "根据条件查找教师信息列表个数", notes = "返回教师信息总个数")

    public ResponseJson findTeacherCountByCondition(
            @ApiParam(value = "教师信息对象")
            @RequestBody Teacher teacher){
        long count= teacherService.findTeacherCountByCondition(teacher);
        return  new ResponseJson(count);
    }
    @PostMapping("/ignore/updateMyPassword")
    public ResponseJson updateMyPassword(@Validated(value = GroupOne.class)
          @ApiParam(value = "新密码(newPassword)  原密码(password)") @RequestBody Teacher teacher){
        teacher.setId(myId());
        Teacher t = teacherService.findTeacherById(myId());
        String newPassword= DigestUtil.sha1Hex(teacher.getNewPassword());
        String oldPassword=DigestUtil.sha1Hex(teacher.getPassword());
        if(!t.getPassword().equals(oldPassword)){
            return new ResponseJson(false,"原密码错误");
        }
        t.setPassword(newPassword);
        teacherService.updateTeacherAdmin(t);
        return new ResponseJson();
    }



}
