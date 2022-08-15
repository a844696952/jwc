package com.yice.edu.cn.mes.feign;

import com.yice.edu.cn.common.pojo.mes.schoolManage.institution.MesUserAuthInstitution;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "dy", contextId = "mesUserAuthInstitutionFeign", path = "/mesUserAuthInstitution")
public interface MesUserAuthInstitutionFeign {

    /**
     * 查询用户是否有检查权限
     *
     * @param mesUserAuthInstitution 参数
     * @return
     */
    @PostMapping("/haveCheckPermission")
    Boolean haveCheckPermission(MesUserAuthInstitution mesUserAuthInstitution);

}
