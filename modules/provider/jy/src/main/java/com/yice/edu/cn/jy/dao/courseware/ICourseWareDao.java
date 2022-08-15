package com.yice.edu.cn.jy.dao.courseware;

import com.yice.edu.cn.common.pojo.jy.courseware.CourseWare;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ICourseWareDao {
    List<CourseWare> findCourseWareListByCondition(CourseWare courseWare);

    long findCourseWareCountByCondition(CourseWare courseWare);

    CourseWare findOneCourseWareByCondition(CourseWare courseWare);

    CourseWare findCourseWareById(@Param("id") String id);

    void saveCourseWare(CourseWare courseWare);

    void updateCourseWare(CourseWare courseWare);

    void deleteCourseWare(@Param("id") String id);

    void deleteCourseWareByCondition(CourseWare courseWare);

    void batchSaveCourseWare(List<CourseWare> courseWares);

     void deleteCourseWareByIds(@Param("ids") List<String> ids);

    void batchUpdateCourseWare(CourseWare courseWare);

    void updateCourseWareToNoType(CourseWare courseWare);

    CourseWare findRecentlyCourseWare(CourseWare courseWare);


}
