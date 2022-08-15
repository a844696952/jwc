package com.yice.edu.cn.osp.feignClient.jw.role;

import com.yice.edu.cn.common.pojo.jw.auth.RolePerm;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="jw",contextId = "rolePermFeign",path = "/rolePerm")
public interface RolePermFeign {
    @GetMapping("/findRolePermById/{id}")
    RolePerm findRolePermById(@PathVariable("id") String id);
    @PostMapping("/saveRolePerm")
    RolePerm saveRolePerm(RolePerm rolePerm);
    @PostMapping("/findRolePermListByCondition")
    List<RolePerm> findRolePermListByCondition(RolePerm rolePerm);
    @PostMapping("/findRolePermCountByCondition")
    long findRolePermCountByCondition(RolePerm rolePerm);
    @PostMapping("/updateRolePerm")
    void updateRolePerm(RolePerm rolePerm);
    @GetMapping("/deleteRolePerm/{id}")
    void deleteRolePerm(@PathVariable("id") String id);
    @PostMapping("/deleteRolePermByCondition")
    void deleteRolePermByCondition(RolePerm rolePerm);
}
