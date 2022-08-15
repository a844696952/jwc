package com.yice.edu.cn.yed.service.system.sysPerm;

import com.yice.edu.cn.common.pojo.yedAdmin.SysPerm;
import com.yice.edu.cn.yed.feignClient.system.sysPerm.SysPermFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysPermService {
    @Autowired
    private SysPermFeign sysPermFeign;


    public SysPerm findSysPermById(String id) {
        return sysPermFeign.findSysPermById(id);
    }

    public SysPerm saveSysPerm(SysPerm sysPerm) {
        return sysPermFeign.saveSysPerm(sysPerm);
    }

    public List<SysPerm> findSysPermListByCondition(SysPerm sysPerm) {
        return sysPermFeign.findSysPermListByCondition(sysPerm);
    }

    public long findSysPermCountByCondition(SysPerm sysPerm) {
        return sysPermFeign.findSysPermCountByCondition(sysPerm);
    }

    public void updateSysPerm(SysPerm sysPerm) {
        sysPermFeign.updateSysPerm(sysPerm);
    }

    public void deleteSysPerm(String id) {
        sysPermFeign.deleteSysPerm(id);
    }

    public List<SysPerm> findAllTreeMenu() {
        return sysPermFeign.findAllTreeMenu();
    }

    public void deleteSysPermRecursive(String id) {
        sysPermFeign.deleteSysPermRecursive(id);
    }

    public List<SysPerm> findAdminTreeMenu(String adminId) {
        return sysPermFeign.findAdminTreeMenu(adminId);
    }
}
