package com.yice.edu.cn.jw.dao.roleSysPerm;

import java.util.List;

import com.yice.edu.cn.common.pojo.yedAdmin.RoleSysPerm;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IRoleSysPermDao {
    List<RoleSysPerm> findRoleSysPermListByCondition(RoleSysPerm roleSysPerm);

    long findRoleSysPermCountByCondition(RoleSysPerm roleSysPerm);

    RoleSysPerm findRoleSysPermById(String id);

    void saveRoleSysPerm(RoleSysPerm roleSysPerm);

    void updateRoleSysPerm(RoleSysPerm roleSysPerm);

    void deleteRoleSysPerm(String id);

    void batchSaveRoleSysPerm(List<RoleSysPerm> roleSysPerms);

    void deleteRoleSysPermByRoleId(@Param("roleId")String roleId);

    void deleteRoleSysPermByPermId(@Param("permId")String permId);
}
