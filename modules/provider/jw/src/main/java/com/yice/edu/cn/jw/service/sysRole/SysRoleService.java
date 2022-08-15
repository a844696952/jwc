package com.yice.edu.cn.jw.service.sysRole;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.yedAdmin.RoleSysPerm;
import com.yice.edu.cn.common.pojo.yedAdmin.SysRole;
import com.yice.edu.cn.jw.dao.adminSysRole.IAdminSysRoleDao;
import com.yice.edu.cn.jw.dao.roleSysPerm.IRoleSysPermDao;
import com.yice.edu.cn.jw.dao.sysRole.ISysRoleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class SysRoleService {
    @Autowired
    private ISysRoleDao sysRoleDao;
    @Autowired
    private SequenceId sequenceId;
    @Autowired
    private IAdminSysRoleDao adminSysRoleDao;
    @Autowired
    private IRoleSysPermDao roleSysPermDao;
    @Transactional(readOnly = true)
    public SysRole findSysRoleById(String id) {
        return sysRoleDao.findSysRoleById(id);
    }
    @Transactional
    public void saveSysRole(SysRole sysRole) {
        sysRole.setId(sequenceId.nextId());
        sysRoleDao.saveSysRole(sysRole);
    }
    @Transactional(readOnly = true)
    public List<SysRole> findSysRoleListByCondition(SysRole sysRole) {
        return sysRoleDao.findSysRoleListByCondition(sysRole);
    }
    @Transactional(readOnly = true)
    public long findSysRoleCountByCondition(SysRole sysRole) {
        return sysRoleDao.findSysRoleCountByCondition(sysRole);
    }
    @Transactional
    public void updateSysRole(SysRole sysRole) {
        sysRoleDao.updateSysRole(sysRole);
    }
    @Transactional
    public void deleteSysRole(String id) {
        sysRoleDao.deleteSysRole(id);
        //同时删除adminRole和SysRolePerm的数据
        adminSysRoleDao.deleteAdminSysRoleByRoleId(id);
        roleSysPermDao.deleteRoleSysPermByRoleId(id);
    }
    @Transactional
    public void delsertRolePerms(Map<String, String> map) {
        String roleId = map.get("roleId");
        String permIds = map.get("permIds");
        roleSysPermDao.deleteRoleSysPermByRoleId(roleId);
        if(permIds!=null){
            String[] permArr = permIds.split(",");
            if(permArr.length>0){
                List<RoleSysPerm> roleSysPerms = new ArrayList<>();
                for (String permId : permArr) {
                    RoleSysPerm roleSysPerm = new RoleSysPerm();
                    roleSysPerm.setId(sequenceId.nextId());
                    roleSysPerm.setRoleId(roleId);
                    roleSysPerm.setPermId(permId);
                    roleSysPerms.add(roleSysPerm);
                }
                roleSysPermDao.batchSaveRoleSysPerm(roleSysPerms);
            }
        }
    }
}
