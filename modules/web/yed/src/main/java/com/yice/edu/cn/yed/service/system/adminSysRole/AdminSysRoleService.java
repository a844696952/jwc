package com.yice.edu.cn.yed.service.system.adminSysRole;

import com.yice.edu.cn.common.pojo.yedAdmin.AdminSysRole;
import com.yice.edu.cn.yed.feignClient.system.adminSysRole.AdminSysRoleFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AdminSysRoleService {
    @Autowired
    private AdminSysRoleFeign adminSysRoleFeign;

    public AdminSysRole findAdminSysRoleById(String id) {
        return adminSysRoleFeign.findAdminSysRoleById(id);
    }

    public AdminSysRole saveAdminSysRole(AdminSysRole adminSysRole) {
        return adminSysRoleFeign.saveAdminSysRole(adminSysRole);
    }

    public List<AdminSysRole> findAdminSysRoleListByCondition(AdminSysRole adminSysRole) {
        return adminSysRoleFeign.findAdminSysRoleListByCondition(adminSysRole);
    }

    public long findAdminSysRoleCountByCondition(AdminSysRole adminSysRole) {
        return adminSysRoleFeign.findAdminSysRoleCountByCondition(adminSysRole);
    }

    public void updateAdminSysRole(AdminSysRole adminSysRole) {
        adminSysRoleFeign.updateAdminSysRole(adminSysRole);
    }

    public void deleteAdminSysRole(String id) {
        adminSysRoleFeign.deleteAdminSysRole(id);
    }

    public void batchSaveAdminSysRole(List<AdminSysRole> adminSysRoles) {
        adminSysRoleFeign.batchSaveAdminSysRole(adminSysRoles);
    }

    public void delsertAdminSysRoles(Map<String, String> adminSysRoles) {
        adminSysRoleFeign.delsertAdminSysRoles(adminSysRoles);
    }
}
