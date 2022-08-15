package com.yice.edu.cn.yed.feignClient.jw.adminPerm;

import com.yice.edu.cn.common.pojo.jw.adminPerm.AdminPerm;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="jw",contextId = "adminPermFeign",path = "/adminPerm")
public interface AdminPermFeign {
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @GetMapping("/findAdminPermById/{id}")
    AdminPerm findAdminPermById(@PathVariable("id") String id);
    @PostMapping("/saveAdminPerm")
    AdminPerm saveAdminPerm(AdminPerm adminPerm);
    @PostMapping("/batchSaveAdminPerm")
    void batchSaveAdminPerm(List<AdminPerm> adminPerms);
    @PostMapping("/findAdminPermListByCondition")
    List<AdminPerm> findAdminPermListByCondition(AdminPerm adminPerm);
    @PostMapping("/findOneAdminPermByCondition")
    AdminPerm findOneAdminPermByCondition(AdminPerm adminPerm);
    @PostMapping("/findAdminPermCountByCondition")
    long findAdminPermCountByCondition(AdminPerm adminPerm);
    @PostMapping("/updateAdminPerm")
    void updateAdminPerm(AdminPerm adminPerm);
    @PostMapping("/updateAdminPermForAll")
    void updateAdminPermForAll(AdminPerm adminPerm);
    @GetMapping("/deleteAdminPerm/{id}")
    void deleteAdminPerm(@PathVariable("id") String id);
    @PostMapping("/deleteAdminPermByCondition")
    void deleteAdminPermByCondition(AdminPerm adminPerm);

    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
    @GetMapping("/deleteAdminPermRecursive/{id}")
    void deleteAdminPermRecursive(@PathVariable("id") String id);
    @PostMapping("/batchUpdateSortNum")
    void batchUpdateSortNum(List<AdminPerm> adminPerms);
}
