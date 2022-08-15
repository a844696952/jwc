package com.yice.edu.cn.jw.dao.auth;

import java.util.List;

import com.yice.edu.cn.common.pojo.jw.auth.Role;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IRoleDao {
    List<Role> findRoleListByCondition(Role role);

    long findRoleCountByCondition(Role role);

    Role findRoleById(String id);

    void saveRole(Role role);

    void updateRole(Role role);

    void deleteRole(String id);

    void deleteRoleByCondition(Role role);

    void batchSaveRole(List<Role> roles);
}
