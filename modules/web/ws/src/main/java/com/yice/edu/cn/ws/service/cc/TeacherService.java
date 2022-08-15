package com.yice.edu.cn.ws.service.cc;

import com.yice.edu.cn.common.pojo.cc.otherSchoolAccount.OtherSchoolAccount;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import com.yice.edu.cn.ws.feignClient.cc.OtherSchoolAccountFeign;
import com.yice.edu.cn.ws.feignClient.cc.TeacherFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeacherService {
    @Autowired
    private TeacherFeign teacherFeign;

    @Autowired
    private OtherSchoolAccountFeign otherSchoolAccountFeign;

    public Teacher getTeacher(String userId){
        Teacher teacher = teacherFeign.findTeacherById(userId);
        return teacher;
    }

    public OtherSchoolAccount getOtherSchoolAccount(String userId){
        OtherSchoolAccount otherSchoolAccount = otherSchoolAccountFeign.findOtherSchoolAccountById(userId);
        return otherSchoolAccount;
    }
}
