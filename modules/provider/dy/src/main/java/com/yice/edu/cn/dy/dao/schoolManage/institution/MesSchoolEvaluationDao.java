package com.yice.edu.cn.dy.dao.schoolManage.institution;

import com.yice.edu.cn.common.pojo.jw.classes.JwClasses;
import org.apache.ibatis.annotations.Mapper;

import com.yice.edu.cn.common.pojo.jw.schoolWeek.SchoolWeek;
import com.yice.edu.cn.common.pojo.jw.schoolYear.SchoolYear;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface MesSchoolEvaluationDao {

    SchoolYear findSchoolYearById(String schoolYearId);

    String findSchoolNameBySchoolId(@Param("schoolId") String schoolId);

    List<String> findGradeIdBySchoolId(@Param("schoolId") String schoolId);

    List<JwClasses> findJwClassesBySchoolId(@Param("schoolId") String schoolId);

    List<JwClasses> findJwClassesBySchoolIdAndGradeId(@Param("schoolId") String schoolId,@Param("gradeId")String gradeId);

    SchoolWeek findSchoolWeekBySchoolYearIdAndTerm(@Param("schoolYearId") String schoolYearId, @Param("term") Integer term);

    SchoolYear findSchoolYearBySchoolId(@Param("schoolId") String schoolId);

    String findGradeIdByClassesId(@Param("classesId")String classesId);
}
