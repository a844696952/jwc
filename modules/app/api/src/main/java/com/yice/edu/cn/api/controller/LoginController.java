package com.yice.edu.cn.api.controller;

import cn.hutool.json.JSONUtil;
import com.yice.edu.cn.api.service.teacher.TeacherService;
import com.yice.edu.cn.api.service.thirdParty.ThirdPartyService;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.api.thirdParty.ThirdParty;
import com.yice.edu.cn.common.pojo.jw.school.School;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import com.yice.edu.cn.common.util.jwt.JwtUtil;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private ThirdPartyService thirdPartyService;

    @Autowired
    private TeacherService teacherService;


    @PostMapping("/login")
    @ApiOperation(value = "第三方登录", notes = "传递appKey,返回token")
    public ResponseJson login(@RequestBody  ThirdParty thirdParty) {
        if(thirdParty.getAppKey()==null){
            return new ResponseJson(false, "appKey必传");
        }
        ThirdParty exist=thirdPartyService.findOneThirdPartyByCondition(new ThirdParty(){{this.setAppKey(thirdParty.getAppKey());}});
        if(exist==null){
            return new ResponseJson(false, "appKey错误");
        }
        String token = JwtUtil.createJWT(exist.getId(), "{}", null, 3600000);
        return new ResponseJson(token);
    }



    @PostMapping("/getToken")
    @ApiOperation(value = "第三方获取token", notes = "传递accessKey和accessValue,返回token")
    public ResponseJson getToken(@RequestParam("accessKey")String accessKey, @RequestParam("accessValue")String accessValue){
        if(StringUtils.isNotEmpty(accessKey) && StringUtils.isNotEmpty(accessValue)){
            Teacher teacher=new Teacher();
            teacher.setTel(accessKey);
            teacher.setPassword(accessValue);
            Teacher t=teacherService.login(teacher);
            if(t!=null){
                Teacher subject = getTeacher(t);
                String token = JwtUtil.createJWT(t.getId(), JSONUtil.toJsonStr(subject), null, Constant.Redis.OSP_TEACHER_TIMEOUT*1000);
                return new ResponseJson(true, token);
            }
        }
        return new ResponseJson(false,"获取token失败");
    }

    private Teacher getTeacher(Teacher t) {
        Teacher subject = new Teacher();
        subject.setId(t.getId());
        subject.setSchoolId(t.getSchoolId());
        subject.setSchoolName(t.getSchoolName());
        subject.setTel(t.getTel());
        subject.setName(t.getName());
        subject.setImgUrl(t.getImgUrl());
        subject.setStatus(t.getStatus());
        subject.setSchool(new School(){{
            this.setTypeId(t.getSchool().getTypeId());
        }});
        return subject;
    }

}
