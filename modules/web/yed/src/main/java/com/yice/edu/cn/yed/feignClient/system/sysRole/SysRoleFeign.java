package com.yice.edu.cn.yed.feignClient.system.sysRole;

import com.yice.edu.cn.common.pojo.yedAdmin.SysRole;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

@FeignClient(value="jw",contextId = "sysRoleFeign",path = "/sysRole")
public interface SysRoleFeign {
    @GetMapping("/findSysRoleById/{id}")
    SysRole findSysRoleById(@PathVariable("id") String id);
    @PostMapping("/saveSysRole")
    SysRole saveSysRole(SysRole sysRole);
    @PostMapping("/findSysRoleListByCondition")
    List<SysRole> findSysRoleListByCondition(SysRole sysRole);
    @PostMapping("/findSysRoleCountByCondition")
    long findSysRoleCountByCondition(SysRole sysRole);
    @PostMapping("/updateSysRole")
    void updateSysRole(SysRole sysRole);
    @GetMapping("/deleteSysRole/{id}")
    void deleteSysRole(@PathVariable("id") String id);
    @PostMapping("/delsertRolePerms")
    void delsertRolePerms(Map<String, String> map);
}
