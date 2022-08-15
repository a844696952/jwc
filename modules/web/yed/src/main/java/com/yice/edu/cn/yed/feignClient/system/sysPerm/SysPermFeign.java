package com.yice.edu.cn.yed.feignClient.system.sysPerm;

import com.yice.edu.cn.common.pojo.yedAdmin.SysPerm;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="jw",contextId = "sysPermFeign",path = "/sysPerm")
public interface SysPermFeign {
    @GetMapping("/findSysPermById/{id}")
    SysPerm findSysPermById(@PathVariable("id") String id);
    @PostMapping("/saveSysPerm")
    SysPerm saveSysPerm(@RequestBody SysPerm sysPerm);
    @PostMapping("/findSysPermListByCondition")
    List<SysPerm> findSysPermListByCondition(SysPerm sysPerm);
    @PostMapping("/findSysPermCountByCondition")
    long findSysPermCountByCondition(SysPerm sysPerm);
    @PostMapping("/updateSysPerm")
    void updateSysPerm(SysPerm sysPerm);
    @GetMapping("/deleteSysPerm/{id}")
    void deleteSysPerm(@PathVariable("id") String id);

    @GetMapping("/findAllTreeMenu")
    List<SysPerm> findAllTreeMenu();
    @GetMapping("/deleteSysPermRecursive/{id}")
    void deleteSysPermRecursive(@PathVariable("id") String id);
    @GetMapping("/findAdminTreeMenu/{adminId}")
    List<SysPerm> findAdminTreeMenu(@PathVariable("adminId") String adminId);
    @GetMapping("/findAllSysPermTree")
    List<SysPerm> findAllSysPermTree();

    @GetMapping("/findSysPermChecked/{roleId}")
    List<String> findSysPermChecked(@PathVariable("roleId") String roleId);
}
