package com.yice.edu.cn.yed.service.system.sysRole;

import com.yice.edu.cn.common.pojo.yedAdmin.SysPerm;
import com.yice.edu.cn.common.pojo.yedAdmin.SysRole;
import com.yice.edu.cn.yed.feignClient.system.sysPerm.SysPermFeign;
import com.yice.edu.cn.yed.feignClient.system.sysRole.SysRoleFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Service
public class SysRoleService {
    @Autowired
    private SysRoleFeign sysRoleFeign;
    @Autowired
    private SysPermFeign sysPermFeign;

    public SysRole findSysRoleById(String id) {
        return sysRoleFeign.findSysRoleById(id);
    }

    public SysRole saveSysRole(SysRole sysRole) {
        return sysRoleFeign.saveSysRole(sysRole);
    }

    public List<SysRole> findSysRoleListByCondition(SysRole sysRole) {
        return sysRoleFeign.findSysRoleListByCondition(sysRole);
    }

    public long findSysRoleCountByCondition(SysRole sysRole) {
        return sysRoleFeign.findSysRoleCountByCondition(sysRole);
    }

    public void updateSysRole(SysRole sysRole) {
        sysRoleFeign.updateSysRole(sysRole);
    }

    public void deleteSysRole(String id) {
        sysRoleFeign.deleteSysRole(id);
    }

    public List<SysPerm> findAllSysPermTree() {
        return sysPermFeign.findAllSysPermTree();
    }

    public List<String> findSysPermChecked(String roleId) {
        return sysPermFeign.findSysPermChecked(roleId);
    }

    public void delsertRolePerms(Map<String, String> map) {
        sysRoleFeign.delsertRolePerms(map);
    }

}
