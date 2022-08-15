package com.yice.edu.cn.jw.dao.schoolYear;

import java.util.List;

import com.yice.edu.cn.common.pojo.jw.schoolYear.SchoolYear;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ISchoolYearDao {
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    List<SchoolYear> findSchoolYearListByCondition(SchoolYear schoolYear);

    long findSchoolYearCountByCondition(SchoolYear schoolYear);

    SchoolYear findOneSchoolYearByCondition(SchoolYear schoolYear);

    SchoolYear findSchoolYearById(@Param("id") String id);

    SchoolYear findMaxSchoolYear(@Param("schoolId")String schoolId);

    void saveSchoolYear(SchoolYear schoolYear);

    void updateSchoolYear(SchoolYear schoolYear);

    void updateSchoolYearForAll(SchoolYear schoolYear);

    void deleteSchoolYear(@Param("id") String id);

    void deleteSchoolYearByCondition(SchoolYear schoolYear);

    void batchSaveSchoolYear(List<SchoolYear> schoolYears);
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
