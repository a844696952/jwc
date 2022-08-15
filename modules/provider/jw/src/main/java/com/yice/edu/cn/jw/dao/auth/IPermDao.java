package com.yice.edu.cn.jw.dao.auth;

import java.util.List;

import com.yice.edu.cn.common.pojo.jw.auth.Perm;
import com.yice.edu.cn.common.pojo.jw.auth.SchoolPerm;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IPermDao {
    /*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    List<Perm> findPermListByCondition(Perm perm);

    long findPermCountByCondition(Perm perm);

    Perm findOnePermByCondition(Perm perm);

    Perm findPermById(@Param("id") String id);

    void savePerm(Perm perm);

    void updatePerm(Perm perm);

    void updatePermForNotNull(Perm perm);

    void deletePerm(@Param("id") String id);

    void deletePermByCondition(Perm perm);

    void batchSavePerm(List<Perm> perms);
    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/

    void deletePermByPId(@Param("pId")String pId);

    List<Perm> findAllPermTreeByPIdAndSchoolId(@Param("pId")String pId, @Param("schoolId")String schoolId);

    List<String> findCheckedPermIdsByRoleId(@Param("id")String id);

    List<Perm> findTeacherTreeMenuByTId(@Param("teacherId") String tId);

    void deletePermBySchoolId(@Param("schoolId")String schoolId);

    void batchUpdate(@Param("list")List<SchoolPerm> schoolPerms);

    List<Perm> findTeacherAdminTreeMenu(@Param("schoolId") String schoolId);

    void deletePermByIds(List<String> ids);

    List<Perm> findCheckedPermsByRoleId(@Param("id")String id);

    List<Perm> findPermsForH5BySchoolId(@Param("schoolId") String schoolId);

    void batchUpdateSortNum(@Param("list")List<SchoolPerm> perms);
}
