package com.yice.edu.cn.jw.dao.electiveCourse;

import com.yice.edu.cn.common.pojo.jw.electiveCourse.ElectiveCourse;
import com.yice.edu.cn.common.pojo.jw.electiveCourse.ElectiveStudent;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface IElectiveStudentDao {
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    List<ElectiveStudent> findElectiveStudentListByCondition(ElectiveStudent electiveStudent);

    long findElectiveStudentCountByCondition(ElectiveStudent electiveStudent);

    ElectiveStudent findOneElectiveStudentByCondition(ElectiveStudent electiveStudent);

    ElectiveStudent findElectiveStudentById(@Param("id") String id);

    void saveElectiveStudent(ElectiveStudent electiveStudent);

    void updateElectiveStudent(ElectiveStudent electiveStudent);

    void deleteElectiveStudent(@Param("id") String id);

    void deleteElectiveStudentByCondition(ElectiveStudent electiveStudent);

    void batchSaveElectiveStudent(List<ElectiveStudent> electiveStudents);


    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/

    List<ElectiveStudent> findMyElectiveStudentListByCondition(ElectiveStudent electiveStudent);

    long findMyElectiveStudentCountByCondition(ElectiveStudent electiveStudent);

    List<ElectiveCourse> getCanSelectCourseList(ElectiveStudent electiveStudent);

    List<ElectiveStudent> findSchoolYearElectiveStudentsByCondition(ElectiveStudent electiveStudent);

    long findSchoolYearElectiveStudentsCountByCondition(ElectiveStudent electiveStudent);

    List<ElectiveCourse> getAlreadySelectCourseList(ElectiveStudent electiveStudent);

    List<ElectiveStudent> findSchoolYearStudentScoreListByCondition(ElectiveStudent electiveStudent);

    long findSchoolYearStudentScoreCountByCondition(ElectiveStudent electiveStudent);

    long getAlreadySelectCourseCount(ElectiveStudent electiveStudent);

    long checkTimeRepeatCount(ElectiveStudent es);

    List<ElectiveStudent> findElectiveStudentScoreListByCondition(ElectiveStudent electiveStudent);

    long findElectiveStudentScoreCountByCondition(ElectiveStudent electiveStudent);
}
