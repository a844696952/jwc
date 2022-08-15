package com.yice.edu.cn.jw.dao.auth;

import java.util.List;

import com.yice.edu.cn.common.pojo.jw.auth.SchoolPerm;
import com.yice.edu.cn.common.pojo.yedAdmin.SysPerm;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ISchoolPermDao {
    /*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    List<SchoolPerm> findSchoolPermListByCondition(SchoolPerm schoolPerm);

    long findSchoolPermCountByCondition(SchoolPerm schoolPerm);

    SchoolPerm findOneSchoolPermByCondition(SchoolPerm schoolPerm);

    SchoolPerm findSchoolPermById(@Param("id") String id);

    void saveSchoolPerm(SchoolPerm schoolPerm);

    void updateSchoolPerm(SchoolPerm schoolPerm);

    void updateSchoolPermForNotNull(SchoolPerm schoolPerm);

    void deleteSchoolPerm(@Param("id") String id);

    void deleteSchoolPermByCondition(SchoolPerm schoolPerm);

    void batchSaveSchoolPerm(List<SchoolPerm> schoolPerms);
    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/


    List<SysPerm> findSysPermsByPId(@Param("pId") String pId);

    List<SchoolPerm> findSchoolPermByPId(String pId);

    void deleteSchoolPermByPid(String pId);


    List<SchoolPerm> findSchoolPermByIds(List<String> checkedIds);

    List<SchoolPerm> findAllSchoolPerms();
    /*查找出jw_perm在school_perm不存在的数据id列表(就是开发的时候吧school_perm删了)*/
    List<String> findNotExistsJwPermIds();
    /**
     * 调用权限同步存储过程
     */
    void syncSchoolPermByPro();

    void batchUpdateSortNum(@Param("list")List<SchoolPerm> perms);
}
