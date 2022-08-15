package com.yice.edu.cn.osp.service.jw.role;

import com.yice.edu.cn.common.pojo.jw.auth.Perm;
import com.yice.edu.cn.common.pojo.jw.auth.Role;
import com.yice.edu.cn.common.pojo.jw.auth.RolePerm;
import com.yice.edu.cn.osp.feignClient.jw.role.RoleFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
    @Autowired
    private RoleFeign roleFeign;
    public Role findRoleById(String id) {
        return roleFeign.findRoleById(id);
    }

    public Role saveRole(Role role) {
        return roleFeign.saveRole(role);
    }

    public List<Role> findRoleListByCondition(Role role) {
        return roleFeign.findRoleListByCondition(role);
    }

    public long findRoleCountByCondition(Role role) {
        return roleFeign.findRoleCountByCondition(role);
    }

    public void updateRole(Role role) {
        roleFeign.updateRole(role);
    }

    public void deleteRole(String id) {
        roleFeign.deleteRole(id);
    }

    public void deleteRoleByCondition(Role role) {
        roleFeign.deleteRoleByCondition(role);
    }

    public List<Perm> findAllPermTreeBySchoolId(String id) {
        return roleFeign.findAllPermTreeBySchoolId(id);
    }

    public List<String> findCheckedPermIdsByRoleId(String id) {
        return roleFeign.findCheckedPermIdsByRoleId(id);
    }

    public void delsertRolePerms(RolePerm rolePerm) {
        roleFeign.delsertRolePerms(rolePerm);
    }

    public void deleteRoleRelated(String id) {
        roleFeign.deleteRoleRelated(id);
    }
}
