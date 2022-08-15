package com.yice.edu.cn.jw.service.sysPerm;

import cn.hutool.core.util.StrUtil;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.yedAdmin.RoleSysPerm;
import com.yice.edu.cn.common.pojo.yedAdmin.SysPerm;
import com.yice.edu.cn.common.util.object.ObjectKit;
import com.yice.edu.cn.jw.dao.roleSysPerm.IRoleSysPermDao;
import com.yice.edu.cn.jw.dao.sysPerm.ISysPermDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class SysPermService {
    @Autowired
    private ISysPermDao sysPermDao;
    @Autowired
    private SequenceId sequenceId;

    @Autowired
    private IRoleSysPermDao roleSysPermDao;


    @Transactional(readOnly = true)
    public SysPerm findSysPermById(String id) {
        return sysPermDao.findSysPermById(id);
    }

    @Transactional
    public void saveSysPerm(SysPerm sysPerm) {
        sysPerm.setId(sequenceId.nextId());
        sysPermDao.saveSysPerm(sysPerm);
    }

    @Transactional(readOnly = true)
    public List<SysPerm> findSysPermListByCondition(SysPerm sysPerm) {
        return sysPermDao.findSysPermListByCondition(sysPerm);
    }

    @Transactional(readOnly = true)
    public long findSysPermCountByCondition(SysPerm sysPerm) {
        return sysPermDao.findSysPermCountByCondition(sysPerm);
    }

    @Transactional
    public void updateSysPerm(SysPerm sysPerm) {
        sysPermDao.updateSysPerm(sysPerm);
    }

    @Transactional
    public void deleteSysPerm(String id) {
        sysPermDao.deleteSysPerm(id);
    }

    @Transactional(readOnly = true)
    public List<SysPerm> findAllTreeMenu() {
        return ObjectKit.buildTree(sysPermDao.findSysPermListByCondition(new SysPerm()),"-1");
    }


    @Transactional
    public void deleteSysPermRecursive(String id) {
        sysPermDao.deleteSysPerm(id);
        roleSysPermDao.deleteRoleSysPermByPermId(id);
        deleteSysPermByPId(id);
    }
    @Transactional
    public void deleteSysPermByPId(String pId) {
        List<SysPerm> perms = sysPermDao.findSysPermByPId(pId);
        for (SysPerm perm : perms) {
            sysPermDao.deleteSysPerm(perm.getId());
            roleSysPermDao.deleteRoleSysPermByPermId(perm.getId());
            deleteSysPermByPId(perm.getId());
        }
    }


    @Transactional(readOnly = true)
    public List<SysPerm> findAllSysPermTree() {
        return this.findAllTreeMenu();
    }

    @Transactional(readOnly = true)
    public List<String> findSysPermChecked(String roleId) {
        return sysPermDao.findSysPermChecked(roleId);
    }

    public List<SysPerm> findAdminTreeMenuV2(String adminId) {
        List<SysPerm> perms=sysPermDao.findAminTreeMenuV2(adminId);
        return ObjectKit.buildTree(perms,"-1");
    }
}
