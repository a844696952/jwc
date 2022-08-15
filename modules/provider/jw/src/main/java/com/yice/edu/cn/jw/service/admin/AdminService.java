package com.yice.edu.cn.jw.service.admin;

import cn.hutool.crypto.digest.DigestUtil;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.yedAdmin.Admin;
import com.yice.edu.cn.common.pojo.yedAdmin.SysPerm;
import com.yice.edu.cn.jw.dao.admin.IAdminDao;
import com.yice.edu.cn.jw.dao.adminSysRole.IAdminSysRoleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AdminService {
    @Autowired
    private IAdminDao adminDao;
    @Autowired
    private SequenceId sequenceId;
    @Autowired
    private IAdminSysRoleDao adminSysRoleDao;

    @Transactional(readOnly = true)
    public Admin findAdminById(String id) {
        return adminDao.findAdminById(id);
    }
    @Transactional
    public void saveAdmin(Admin admin) {
        admin.setId(sequenceId.nextId());
        adminDao.saveAdmin(admin);
    }
    @Transactional(readOnly = true)
    public List<Admin> findAdminListByCondition(Admin admin) {
        return adminDao.findAdminListByCondition(admin);
    }
    @Transactional(readOnly = true)
    public long findAdminCountByCondition(Admin admin) {
        return adminDao.findAdminCountByCondition(admin);
    }
    @Transactional
    public void updateAdmin(Admin admin) {
        adminDao.updateAdmin(admin);
    }
    @Transactional
    public void deleteAdmin(String id) {
        adminDao.deleteAdmin(id);
        //同时删除admin和jw_role的中间表
        adminSysRoleDao.deleteAdminSysRoleByAdminId(id);
    }

    @Transactional(readOnly = true)
    public Admin adminLogin(Admin admin) {
        admin.setPassword(DigestUtil.sha1Hex(admin.getPassword()));
        List<Admin> admins = adminDao.findAdminListByCondition(admin);
        if(admins.size()==0)return null;
        return admins.get(0);
    }
    @Transactional(readOnly = true)
    public List<String> findCheckedRoloIdsByAdminId(String adminId) {
        return adminDao.findCheckedRoloIdsByAdminId(adminId);
    }
    @Transactional(readOnly = true)
    public List<SysPerm> findSysFuncPermsByAdminId(String adminId) {
        return adminDao.findSysFuncPermsByAdminId(adminId);
    }
}
