package com.yice.edu.cn.jw.service.auth;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.jw.auth.RolePerm;
import com.yice.edu.cn.jw.dao.auth.IRolePermDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RolePermService {
    @Autowired
    private IRolePermDao rolePermDao;
    @Autowired
    private SequenceId sequenceId;

    @Transactional(readOnly = true)
    public RolePerm findRolePermById(String id) {
        return rolePermDao.findRolePermById(id);
    }
    @Transactional
    public void saveRolePerm(RolePerm rolePerm) {
        rolePerm.setId(sequenceId.nextId());
        rolePermDao.saveRolePerm(rolePerm);
    }
    @Transactional(readOnly = true)
    public List<RolePerm> findRolePermListByCondition(RolePerm rolePerm) {
        return rolePermDao.findRolePermListByCondition(rolePerm);
    }
    @Transactional(readOnly = true)
    public long findRolePermCountByCondition(RolePerm rolePerm) {
        return rolePermDao.findRolePermCountByCondition(rolePerm);
    }
    @Transactional
    public void updateRolePerm(RolePerm rolePerm) {
        rolePermDao.updateRolePerm(rolePerm);
    }
    @Transactional
    public void deleteRolePerm(String id) {
        rolePermDao.deleteRolePerm(id);
    }
    @Transactional
    public void deleteRolePermByCondition(RolePerm rolePerm) {
        rolePermDao.deleteRolePermByCondition(rolePerm);
    }
}
