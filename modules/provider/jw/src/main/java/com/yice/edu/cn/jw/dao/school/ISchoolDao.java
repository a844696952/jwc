package com.yice.edu.cn.jw.dao.school;

import java.util.List;

import com.yice.edu.cn.common.pojo.jw.auth.SchoolPerm;
import com.yice.edu.cn.common.pojo.jw.school.School;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ISchoolDao {
    List<School> findSchoolListByCondition(School school);

    long findSchoolCountByCondition(School school);

    School findOneSchoolByCondition(School school);

    School findSchoolById(@Param("id") String id);

    void saveSchool(School school);

    void updateSchool(School school);

    void deleteSchool(@Param("id") String id);

    void deleteSchoolByCondition(School school);

    void batchSaveSchool(List<School> schools);

    long findSchoolNoRepetitionSchoolName(School school);

}
