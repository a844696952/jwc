package com.yice.edu.cn.yed.service.jw.adminPerm;

import com.yice.edu.cn.common.pojo.jw.adminPerm.AdminPerm;
import com.yice.edu.cn.yed.feignClient.jw.adminPerm.AdminPermFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminPermService {
    @Autowired
    private AdminPermFeign adminPermFeign;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    public AdminPerm findAdminPermById(String id) {
        return adminPermFeign.findAdminPermById(id);
    }

    public AdminPerm saveAdminPerm(AdminPerm adminPerm) {
        return adminPermFeign.saveAdminPerm(adminPerm);
    }

    public void batchSaveAdminPerm(List<AdminPerm> adminPerms){
        adminPermFeign.batchSaveAdminPerm(adminPerms);
    }

    public List<AdminPerm> findAdminPermListByCondition(AdminPerm adminPerm) {
        return adminPermFeign.findAdminPermListByCondition(adminPerm);
    }

    public AdminPerm findOneAdminPermByCondition(AdminPerm adminPerm) {
        return adminPermFeign.findOneAdminPermByCondition(adminPerm);
    }

    public long findAdminPermCountByCondition(AdminPerm adminPerm) {
        return adminPermFeign.findAdminPermCountByCondition(adminPerm);
    }

    public void updateAdminPerm(AdminPerm adminPerm) {
        adminPermFeign.updateAdminPerm(adminPerm);
    }

    public void updateAdminPermForAll(AdminPerm adminPerm) {
        adminPermFeign.updateAdminPermForAll(adminPerm);
    }

    public void deleteAdminPerm(String id) {
        adminPermFeign.deleteAdminPerm(id);
    }

    public void deleteAdminPermByCondition(AdminPerm adminPerm) {
        adminPermFeign.deleteAdminPermByCondition(adminPerm);
    }


    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
    public void deleteAdminPermRecursive(String id) {
        adminPermFeign.deleteAdminPermRecursive(id);
    }

    public void batchUpdateSortNum(List<AdminPerm> adminPerms) {
        adminPermFeign.batchUpdateSortNum(adminPerms);
    }
}
