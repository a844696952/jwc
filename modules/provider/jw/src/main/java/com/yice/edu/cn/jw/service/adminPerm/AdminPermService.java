package com.yice.edu.cn.jw.service.adminPerm;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.jw.adminPerm.AdminPerm;
import com.yice.edu.cn.jw.dao.adminPerm.IAdminPermDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AdminPermService {
    @Autowired
    private IAdminPermDao adminPermDao;
    @Autowired
    private SequenceId sequenceId;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @Transactional(readOnly = true)
    public AdminPerm findAdminPermById(String id) {
        return adminPermDao.findAdminPermById(id);
    }
    @Transactional
    public void saveAdminPerm(AdminPerm adminPerm) {
        adminPerm.setId(sequenceId.nextId());
        adminPermDao.saveAdminPerm(adminPerm);
    }
    @Transactional(readOnly = true)
    public List<AdminPerm> findAdminPermListByCondition(AdminPerm adminPerm) {
        return adminPermDao.findAdminPermListByCondition(adminPerm);
    }
    @Transactional(readOnly = true)
    public AdminPerm findOneAdminPermByCondition(AdminPerm adminPerm) {
        return adminPermDao.findOneAdminPermByCondition(adminPerm);
    }
    @Transactional(readOnly = true)
    public long findAdminPermCountByCondition(AdminPerm adminPerm) {
        return adminPermDao.findAdminPermCountByCondition(adminPerm);
    }
    @Transactional
    public void updateAdminPerm(AdminPerm adminPerm) {
        adminPermDao.updateAdminPerm(adminPerm);
    }
    @Transactional
    public void updateAdminPermForAll(AdminPerm adminPerm) {
        adminPermDao.updateAdminPermForAll(adminPerm);
    }
    @Transactional
    public void deleteAdminPerm(String id) {
        adminPermDao.deleteAdminPerm(id);
    }
    @Transactional
    public void deleteAdminPermByCondition(AdminPerm adminPerm) {
        adminPermDao.deleteAdminPermByCondition(adminPerm);
    }
    @Transactional
    public void batchSaveAdminPerm(List<AdminPerm> adminPerms){
        adminPerms.forEach(adminPerm -> adminPerm.setId(sequenceId.nextId()));
        adminPermDao.batchSaveAdminPerm(adminPerms);
    }


    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
    @Transactional
    public void deleteAdminPermRecursive(String id) {
        adminPermDao.deleteAdminPerm(id);
        deleteByPId(id);
    }
    @Transactional
    public void deleteByPId(String pId){
        AdminPerm adminPerm = new AdminPerm();
        adminPerm.setParentId(pId);
        List<AdminPerm> list = adminPermDao.findAdminPermListByCondition(adminPerm);
        adminPermDao.deleteAdminPermByCondition(adminPerm);
        for (AdminPerm perm :list){
            deleteByPId(perm.getId());
        }
    }
    @Transactional
    public void batchUpdateSortNum(List<AdminPerm> adminPerms) {
        adminPermDao.batchUpdateSortNum(adminPerms);
    }
}
