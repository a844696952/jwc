package com.yice.edu.cn.yed.feignClient.system.adminSysRole;

import com.yice.edu.cn.common.pojo.yedAdmin.AdminSysRole;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

@FeignClient(value="jw",contextId = "adminSysRoleFeign",path = "/adminSysRole")
public interface AdminSysRoleFeign {
    @GetMapping("/findAdminSysRoleById/{id}")
    AdminSysRole findAdminSysRoleById(@PathVariable("id") String id);
    @PostMapping("/saveAdminSysRole")
    AdminSysRole saveAdminSysRole(@RequestBody AdminSysRole adminSysRole);
    @PostMapping("/findAdminSysRoleListByCondition")
    List<AdminSysRole> findAdminSysRoleListByCondition(AdminSysRole adminSysRole);
    @PostMapping("/findAdminSysRoleCountByCondition")
    long findAdminSysRoleCountByCondition(AdminSysRole adminSysRole);
    @PostMapping("/updateAdminSysRole")
    void updateAdminSysRole(AdminSysRole adminSysRole);
    @GetMapping("/deleteAdminSysRole/{id}")
    void deleteAdminSysRole(@PathVariable("id") String id);
    @PostMapping("/batchSaveAdminSysRole")
    void batchSaveAdminSysRole(List<AdminSysRole> adminSysRoles);
    @PostMapping("/delsertAdminSysRoles")
    void delsertAdminSysRoles(@RequestBody Map<String, String> adminSysRoles);
}
