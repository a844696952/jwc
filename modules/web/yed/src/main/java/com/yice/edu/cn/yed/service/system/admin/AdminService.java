package com.yice.edu.cn.yed.service.system.admin;

import com.alicp.jetcache.anno.CacheInvalidate;
import com.alicp.jetcache.anno.CacheUpdate;
import com.alicp.jetcache.anno.Cached;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.yedAdmin.Admin;
import com.yice.edu.cn.common.pojo.yedAdmin.SysPerm;
import com.yice.edu.cn.common.pojo.yedAdmin.SysRole;
import com.yice.edu.cn.yed.feignClient.system.admin.AdminFeign;
import com.yice.edu.cn.yed.feignClient.system.sysRole.SysRoleFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {
    @Autowired
    private AdminFeign adminFeign;
    @Autowired
    private SysRoleFeign sysRoleFeign;
    public Admin findAdminById(String id) {
        return adminFeign.findAdminById(id);
    }

    public Admin saveAdmin(Admin admin) {
        return adminFeign.saveAdmin(admin);
    }

    public List<Admin> findAdminListByCondition(Admin admin) {
        return adminFeign.findAdminListByCondition(admin);
    }

    public long findAdminCountByCondition(Admin admin) {
        return adminFeign.findAdminCountByCondition(admin);
    }

    public void updateAdmin(Admin admin) {
        adminFeign.updateAdmin(admin);
    }

    public void deleteAdmin(String id) {
        adminFeign.deleteAdmin(id);
    }

    public ResponseJson findSysRolesByAdminId(String id) {
        List<SysRole> roles=sysRoleFeign.findSysRoleListByCondition(new SysRole());
        List<String> checkedIds=adminFeign.findCheckedRoloIdsByAdminId(id);
        return new ResponseJson(roles,checkedIds);
    }
    public List<SysPerm> findSysFuncPermsByAdminId(String adminId) {
        return adminFeign.findSysFuncPermsByAdminId(adminId);
    }
    /*gzw*/
    public Admin updateMySelf(Admin admin) {
       adminFeign.updateAdmin(admin);
       Admin admin1 = adminFeign.findAdminById(admin.getId());
       admin1.setPassword(null);
       return admin1;
    }
}
