package com.yice.edu.cn.mes.service;

import com.yice.edu.cn.common.pojo.mes.schoolManage.institution.MesUserAuthInstitution;
import com.yice.edu.cn.mes.feign.MesUserAuthInstitutionFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MesUserAuthInstitutionService {
    @Autowired
    private MesUserAuthInstitutionFeign mesUserAuthInstitutionFeign;

    /**
     * 查询用户是否有检查权限
     */
    public Boolean haveCheckPermission(MesUserAuthInstitution mesUserAuthInstitution) {
        return mesUserAuthInstitutionFeign.haveCheckPermission(mesUserAuthInstitution);
    }


}
