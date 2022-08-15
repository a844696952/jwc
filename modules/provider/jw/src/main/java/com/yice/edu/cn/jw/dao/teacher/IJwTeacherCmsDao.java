package com.yice.edu.cn.jw.dao.teacher;

import com.yice.edu.cn.common.pojo.jw.teacher.JwTeacherCms;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface IJwTeacherCmsDao {
    /*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    List<JwTeacherCms> findJwTeacherCmsListByCondition(JwTeacherCms jwTeacherCms);

    long findJwTeacherCmsCountByCondition(JwTeacherCms jwTeacherCms);

    JwTeacherCms findOneJwTeacherCmsByCondition(JwTeacherCms jwTeacherCms);

    JwTeacherCms findJwTeacherCmsById(@Param("id") String id);

    void saveJwTeacherCms(JwTeacherCms jwTeacherCms);

    void updateJwTeacherCms(JwTeacherCms jwTeacherCms);

    void deleteJwTeacherCms(@Param("id") String id);

    void deleteJwTeacherCmsByCondition(JwTeacherCms jwTeacherCms);

    void batchSaveJwTeacherCms(List<JwTeacherCms> jwTeacherCmss);
    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
