package com.yice.edu.cn.yed.service.frame.login;

import com.alicp.jetcache.anno.Cached;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.yedAdmin.Admin;
import com.yice.edu.cn.yed.feignClient.frame.login.LoginFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    @Autowired
    private LoginFeign loginFeign;

    public Admin adminLogin(Admin admin) {
        return loginFeign.adminLogin(admin);
    }



}
