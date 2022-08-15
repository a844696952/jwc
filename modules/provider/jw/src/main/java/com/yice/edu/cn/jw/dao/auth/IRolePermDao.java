package com.yice.edu.cn.jw.dao.auth;

import java.util.List;

import com.yice.edu.cn.common.pojo.jw.auth.RolePerm;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IRolePermDao {
    List<RolePerm> findRolePermListByCondition(RolePerm rolePerm);

    long findRolePermCountByCondition(RolePerm rolePerm);

    RolePerm findRolePermById(String id);

    void saveRolePerm(RolePerm rolePerm);

    void updateRolePerm(RolePerm rolePerm);

    void deleteRolePerm(String id);

    void deleteRolePermByCondition(RolePerm rolePerm);

    void batchSaveRolePerm(List<RolePerm> rolePerms);

    void deleteRolePermByPermId(@Param("id") String id);

    void deleteRolePermByPermIdsAndSchoolId(@Param("list") List<String> deletedIds,@Param("schoolId") String schoolId);

    void deleteRolePermByPermIds(@Param("ids")List<String> ids);
}
