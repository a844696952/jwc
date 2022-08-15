package com.yice.edu.cn.dy.dao.schoolManage.institution;

import com.yice.edu.cn.common.pojo.jw.schoolWeek.SchoolWeek;
import com.yice.edu.cn.common.pojo.jw.schoolYear.SchoolYear;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import com.yice.edu.cn.common.pojo.mes.schoolManage.schoolEvaluation.MesInstitutionStudent;
import com.yice.edu.cn.common.pojo.mes.schoolManage.institution.MesCommonConfig;
import com.yice.edu.cn.common.pojo.mes.schoolManage.institution.MesInstitution;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface IMesInstitutionDao {
    /*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    List<MesInstitution> findMesInstitutionListByCondition(MesInstitution mesInstitution);

    List<MesInstitution> findMesInstitutionsByCondition(MesInstitution mesInstitution);

    long findMesInstitutionCountByCondition(MesInstitution mesInstitution);

    MesInstitution findOneMesInstitutionByCondition(MesInstitution mesInstitution);

    MesInstitution findOneSortMesInstitutionByCondition(MesInstitution mesInstitution);

    MesInstitution findMesInstitutionById(@Param("id") String id);

    void saveMesInstitution(MesInstitution mesInstitution);

    void updateMesInstitution(MesInstitution mesInstitution);

    void updateMesInstitutionTimeStatusId(MesInstitution mesInstitution);

    void deleteMesInstitution(@Param("id") String id);

    void deleteMesInstitutionWhereTimeStatusIdIsNull(@Param("id") String id);

    void deleteMesInstitutionByCondition(MesInstitution mesInstitution);

    void batchSaveMesInstitution(List<MesInstitution> mesInstitutions);

    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
    List<MesInstitutionStudent> findAllJwClassesAndStudents(String schoolId);

    List<MesInstitutionStudent> findAllJwClassesBySchoolId(String schoolId);

    List<MesInstitutionStudent> findAllJwClassesByGradeId(MesCommonConfig mesInstitutionStudent);

    List<Student> findStudentsByClassId(String classId);

    List<SchoolYear> findSchoolYearsBySchoolId(@Param("schoolId") String schoolId);

    SchoolYear findSchoolYearById(@Param("id") String id);

    void saveSchoolWeek(SchoolWeek schoolWeek);

    void deleteSchoolWeekBySchoolYearId(@Param("id") String id);

    List<MesInstitution> findMesInstitutionListByTimeStatusId(@Param("timeStatusId") String timeStatusId);

    List<MesInstitution> findMesInstitutionsByTimeStatusId(@Param("timeStatusId") String timeStatusId);

    String selectSchoolNameBySchoolId(String schoolId);

    List<Student> findStudentsBySchoolId(@Param("schoolId")String schoolId);

    List<SchoolWeek> findSchoolWeekBySchoolYearId(@Param("schoolYearId")String schoolYearId);
}
