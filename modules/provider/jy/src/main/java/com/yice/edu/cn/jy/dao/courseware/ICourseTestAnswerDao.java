package com.yice.edu.cn.jy.dao.courseware;

import com.yice.edu.cn.common.pojo.jy.courseware.CourseTestAnswer;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ICourseTestAnswerDao {
    List<CourseTestAnswer> findCourseTestAnswerListByCondition(CourseTestAnswer courseTestAnswer);

    long findCourseTestAnswerCountByCondition(CourseTestAnswer courseTestAnswer);

    CourseTestAnswer findOneCourseTestAnswerByCondition(CourseTestAnswer courseTestAnswer);

    CourseTestAnswer findCourseTestAnswerById(@Param("id") String id);

    void saveCourseTestAnswer(CourseTestAnswer courseTestAnswer);

    void updateCourseTestAnswer(CourseTestAnswer courseTestAnswer);

    void deleteCourseTestAnswer(@Param("id") String id);

    void deleteCourseTestAnswerByCondition(CourseTestAnswer courseTestAnswer);

    void batchSaveCourseTestAnswer(List<CourseTestAnswer> courseTestAnswers);

    void deletes(List<String> testIds);
}
