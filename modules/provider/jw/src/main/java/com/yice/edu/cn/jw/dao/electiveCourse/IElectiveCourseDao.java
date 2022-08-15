package com.yice.edu.cn.jw.dao.electiveCourse;

import com.yice.edu.cn.common.pojo.jw.electiveCourse.ElectiveCourse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface IElectiveCourseDao {
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    List<ElectiveCourse> findElectiveCourseListByCondition(ElectiveCourse electiveCourse);

    long findElectiveCourseCountByCondition(ElectiveCourse electiveCourse);

    ElectiveCourse findOneElectiveCourseByCondition(ElectiveCourse electiveCourse);

    ElectiveCourse findElectiveCourseById(@Param("id") String id);

    ElectiveCourse findElectiveCourseAndStuCountById(@Param("id") String id);

    void saveElectiveCourse(ElectiveCourse electiveCourse);

    void updateElectiveCourse(ElectiveCourse electiveCourse);

    void deleteElectiveCourse(@Param("id") String id);

    void deleteElectiveCourseByCondition(ElectiveCourse electiveCourse);

    void batchSaveElectiveCourse(List<ElectiveCourse> electiveCourses);

    List<ElectiveCourse> findElectiveCourseWithClassInfoById(String id);

    List<ElectiveCourse> findElectiveCoursesByConditionForStu(ElectiveCourse electiveCourse);

    long findElectiveCourseCountByConditionForStu(ElectiveCourse electiveCourse);
    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
    int  updateElectiveCourseVersion(ElectiveCourse electiveCourse);
}
