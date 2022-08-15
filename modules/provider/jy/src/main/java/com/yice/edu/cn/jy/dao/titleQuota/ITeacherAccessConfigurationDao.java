package com.yice.edu.cn.jy.dao.titleQuota;

import com.yice.edu.cn.common.pojo.jy.titleQuota.TeacherAccessConfiguration;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ITeacherAccessConfigurationDao {
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    List<TeacherAccessConfiguration> findTeacherAccessConfigurationListByCondition(TeacherAccessConfiguration teacherAccessConfiguration);

    long findTeacherAccessConfigurationCountByCondition(TeacherAccessConfiguration teacherAccessConfiguration);

    TeacherAccessConfiguration findOneTeacherAccessConfigurationByCondition(TeacherAccessConfiguration teacherAccessConfiguration);

    TeacherAccessConfiguration findTeacherAccessConfigurationById(@Param("id") String id);

    void saveTeacherAccessConfiguration(TeacherAccessConfiguration teacherAccessConfiguration);

    void updateTeacherAccessConfiguration(TeacherAccessConfiguration teacherAccessConfiguration);

    void updateTeacherAccessConfigurationForAll(TeacherAccessConfiguration teacherAccessConfiguration);

    void deleteTeacherAccessConfiguration(@Param("id") String id);

    void deleteTeacherAccessConfigurationByCondition(TeacherAccessConfiguration teacherAccessConfiguration);

    void batchSaveTeacherAccessConfiguration(List<TeacherAccessConfiguration> teacherAccessConfigurations);


    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
    List<TeacherAccessConfiguration> findTeacherAccessConfigurationListByCondition4Like(TeacherAccessConfiguration teacherAccessConfiguration);

    void updateTeacherAccessConfigurationForAll4Like(TeacherAccessConfiguration teacherAccessConfiguration);

    long findTeacherAccessConfigurationListByCondition4LikeCount(TeacherAccessConfiguration teacherAccessConfiguration);

    TeacherAccessConfiguration findTeacherAccessConfigurationsByConditioOne(TeacherAccessConfiguration teacherAccessConfiguration);
}
