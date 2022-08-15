package com.yice.edu.cn.jw.dao.sysRole;

import java.util.List;

import com.yice.edu.cn.common.pojo.yedAdmin.SysRole;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ISysRoleDao {
    List<SysRole> findSysRoleListByCondition(SysRole sysRole);

    long findSysRoleCountByCondition(SysRole sysRole);

    SysRole findSysRoleById(String id);

    void saveSysRole(SysRole sysRole);

    void updateSysRole(SysRole sysRole);

    void deleteSysRole(String id);

    void batchSaveSysRole(List<SysRole> sysRoles);
}
