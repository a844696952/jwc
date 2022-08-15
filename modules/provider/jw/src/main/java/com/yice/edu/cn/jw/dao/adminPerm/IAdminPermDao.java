package com.yice.edu.cn.jw.dao.adminPerm;

import java.util.List;

import com.yice.edu.cn.common.pojo.jw.adminPerm.AdminPerm;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IAdminPermDao {
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    List<AdminPerm> findAdminPermListByCondition(AdminPerm adminPerm);

    long findAdminPermCountByCondition(AdminPerm adminPerm);

    AdminPerm findOneAdminPermByCondition(AdminPerm adminPerm);

    AdminPerm findAdminPermById(@Param("id") String id);

    void saveAdminPerm(AdminPerm adminPerm);

    void updateAdminPerm(AdminPerm adminPerm);

    void updateAdminPermForAll(AdminPerm adminPerm);

    void deleteAdminPerm(@Param("id") String id);

    void deleteAdminPermByCondition(AdminPerm adminPerm);

    void batchSaveAdminPerm(List<AdminPerm> adminPerms);

    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
    void batchUpdateSortNum(List<AdminPerm> adminPerms);
}
