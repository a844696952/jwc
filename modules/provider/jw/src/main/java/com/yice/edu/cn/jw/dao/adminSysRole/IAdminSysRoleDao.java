package com.yice.edu.cn.jw.dao.adminSysRole;

import java.util.List;

import com.yice.edu.cn.common.pojo.yedAdmin.AdminSysRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IAdminSysRoleDao {
    List<AdminSysRole> findAdminSysRoleListByCondition(AdminSysRole adminSysRole);

    AdminSysRole findOneAdminSysRoleByCondition(AdminSysRole adminSysRole);

    long findAdminSysRoleCountByCondition(AdminSysRole adminSysRole);

    AdminSysRole findAdminSysRoleById(@Param("id") String id);

    void saveAdminSysRole(AdminSysRole adminSysRole);

    void updateAdminSysRole(AdminSysRole adminSysRole);

    void deleteAdminSysRole(@Param("id") String id);

    void deleteAdminSysRoleByCondition(AdminSysRole adminSysRole);

    void batchSaveAdminSysRole(List<AdminSysRole> adminSysRoles);

    void deleteAdminSysRoleByAdminId(@Param("adminId")String adminId);

    void deleteAdminSysRoleByRoleId(@Param("roleId")String roleId);
}
