package com.yice.edu.cn.cc.service.login;

import com.yice.edu.cn.cc.feignClient.otherSchoolAccount.OtherSchoolAccountFeign;
import com.yice.edu.cn.cc.feignClient.teacher.TeacherFeign;
import com.yice.edu.cn.common.pojo.cc.otherSchoolAccount.OtherSchoolAccount;
import com.yice.edu.cn.common.pojo.jw.teacher.LoginObj;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    @Autowired
    private TeacherFeign teacherFeign;
    @Autowired
    private OtherSchoolAccountFeign otherSchoolAccountFeign;
    public Teacher teacherLogin(LoginObj loginObj){
        return teacherFeign.ccLogin(loginObj);

    }

    public OtherSchoolAccount otherLogin(LoginObj loginObj) {
        OtherSchoolAccount otherSchoolAccount = new OtherSchoolAccount();
        otherSchoolAccount.setName(loginObj.getTel());
        otherSchoolAccount.setPassword(loginObj.getPassword());
        return otherSchoolAccountFeign.findOneOtherSchoolAccountByCondition(otherSchoolAccount);
    }
}
