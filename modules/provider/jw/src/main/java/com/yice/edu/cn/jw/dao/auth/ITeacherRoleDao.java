package com.yice.edu.cn.jw.dao.auth;

import java.util.List;

import com.yice.edu.cn.common.pojo.jw.auth.TeacherRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ITeacherRoleDao {
    List<TeacherRole> findTeacherRoleListByCondition(TeacherRole teacherRole);

    long findTeacherRoleCountByCondition(TeacherRole teacherRole);

    TeacherRole findTeacherRoleById(String id);

    void saveTeacherRole(TeacherRole teacherRole);

    void updateTeacherRole(TeacherRole teacherRole);

    void deleteTeacherRole(String id);

    void deleteTeacherRoleByCondition(TeacherRole teacherRole);

    void batchSaveTeacherRole(List<TeacherRole> teacherRoles);

    void batchDeleteByTeacherIds(@Param("teacherArr") String[] teacherArr);
}
