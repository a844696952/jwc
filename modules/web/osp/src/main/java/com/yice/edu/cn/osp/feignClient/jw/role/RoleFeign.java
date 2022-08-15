package com.yice.edu.cn.osp.feignClient.jw.role;

import com.yice.edu.cn.common.pojo.jw.auth.Perm;
import com.yice.edu.cn.common.pojo.jw.auth.Role;
import com.yice.edu.cn.common.pojo.jw.auth.RolePerm;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="jw",contextId = "roleFeign",path = "/role")
public interface RoleFeign {
    @GetMapping("/findRoleById/{id}")
    Role findRoleById(@PathVariable("id") String id);
    @PostMapping("/saveRole")
    Role saveRole(Role role);
    @PostMapping("/findRoleListByCondition")
    List<Role> findRoleListByCondition(Role role);
    @PostMapping("/findRoleCountByCondition")
    long findRoleCountByCondition(Role role);
    @PostMapping("/updateRole")
    void updateRole(Role role);
    @GetMapping("/deleteRole/{id}")
    void deleteRole(@PathVariable("id") String id);
    @PostMapping("/deleteRoleByCondition")
    void deleteRoleByCondition(Role role);
    @GetMapping("/findAllPermTreeBySchoolId/{id}")
    List<Perm> findAllPermTreeBySchoolId(@PathVariable("id") String id);
    @GetMapping("/findCheckedPermIdsByRoleId/{id}")
    List<String> findCheckedPermIdsByRoleId(@PathVariable("id") String id);
    @PostMapping("/delsertRolePerms")
    void delsertRolePerms(RolePerm rolePerm);
    @GetMapping("/deleteRoleRelated/{id}")
    void deleteRoleRelated(@PathVariable("id") String id);
}
