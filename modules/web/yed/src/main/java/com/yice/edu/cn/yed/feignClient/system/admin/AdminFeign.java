package com.yice.edu.cn.yed.feignClient.system.admin;

import com.yice.edu.cn.common.pojo.yedAdmin.Admin;
import com.yice.edu.cn.common.pojo.yedAdmin.SysPerm;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(value="jw",contextId = "adminFeign",path = "/admin")
public interface AdminFeign {
    @GetMapping("/findAdminById/{id}")
    Admin findAdminById(@PathVariable("id") String id);
    @PostMapping("/saveAdmin")
    Admin saveAdmin(@RequestBody Admin admin);
    @PostMapping("/findAdminListByCondition")
    List<Admin> findAdminListByCondition(Admin admin);
    @PostMapping("/findAdminCountByCondition")
    long findAdminCountByCondition(Admin admin);
    @PostMapping("/updateAdmin")
    void updateAdmin(Admin admin);
    @GetMapping("/deleteAdmin/{id}")
    void deleteAdmin(@PathVariable("id") String id);
    @GetMapping("/findCheckedRoloIdsByAdminId/{adminId}")
    List<String> findCheckedRoloIdsByAdminId(@PathVariable("adminId") String adminId);
    @GetMapping("/findSysFuncPermsByAdminId/{adminId}")
    List<SysPerm> findSysFuncPermsByAdminId(@PathVariable("adminId") String adminId);
}
