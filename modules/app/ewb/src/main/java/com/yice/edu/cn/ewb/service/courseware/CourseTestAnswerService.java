package com.yice.edu.cn.ewb.service.courseware;

import com.yice.edu.cn.common.pojo.jy.courseware.CourseTestAnswer;
import com.yice.edu.cn.ewb.feignClient.courseware.CourseTestAnswerFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseTestAnswerService {
    @Autowired
    private CourseTestAnswerFeign courseTestAnswerFeign;

    public CourseTestAnswer findCourseTestAnswerById(String id) {
        return courseTestAnswerFeign.findCourseTestAnswerById(id);
    }

    public CourseTestAnswer saveCourseTestAnswer(CourseTestAnswer courseTestAnswer) {
        return courseTestAnswerFeign.saveCourseTestAnswer(courseTestAnswer);
    }

    public List<CourseTestAnswer> findCourseTestAnswerListByCondition(CourseTestAnswer courseTestAnswer) {
        return courseTestAnswerFeign.findCourseTestAnswerListByCondition(courseTestAnswer);
    }

    public CourseTestAnswer findOneCourseTestAnswerByCondition(CourseTestAnswer courseTestAnswer) {
        return courseTestAnswerFeign.findOneCourseTestAnswerByCondition(courseTestAnswer);
    }

    public long findCourseTestAnswerCountByCondition(CourseTestAnswer courseTestAnswer) {
        return courseTestAnswerFeign.findCourseTestAnswerCountByCondition(courseTestAnswer);
    }

    public void updateCourseTestAnswer(CourseTestAnswer courseTestAnswer) {
        courseTestAnswerFeign.updateCourseTestAnswer(courseTestAnswer);
    }

    public void deleteCourseTestAnswer(String id) {
        courseTestAnswerFeign.deleteCourseTestAnswer(id);
    }

    public void deleteCourseTestAnswerByCondition(CourseTestAnswer courseTestAnswer) {
        courseTestAnswerFeign.deleteCourseTestAnswerByCondition(courseTestAnswer);
    }
}
