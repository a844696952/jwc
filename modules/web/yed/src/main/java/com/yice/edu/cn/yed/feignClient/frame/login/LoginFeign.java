package com.yice.edu.cn.yed.feignClient.frame.login;


import com.yice.edu.cn.common.pojo.yedAdmin.Admin;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(value="jw",contextId = "loginFeign")
public interface LoginFeign {
    @RequestMapping("/admin/findAdminById/{id}")
    Admin findAdminById(@PathVariable("id") String id);

    @RequestMapping("/admin/adminLogin")
    Admin adminLogin(Admin admin);
}
