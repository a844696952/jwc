package com.yice.edu.cn.rpm.service.login;

import com.alicp.jetcache.anno.Cached;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import com.yice.edu.cn.rpm.feignClient.login.LoginFeign;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    @Autowired
    private LoginFeign loginFeign;
    public Teacher login(Teacher t) {
        return loginFeign.login(t);
    }

    public Teacher findTeacherById(String id){
        return loginFeign.findTeacherById(id);
    }
}
