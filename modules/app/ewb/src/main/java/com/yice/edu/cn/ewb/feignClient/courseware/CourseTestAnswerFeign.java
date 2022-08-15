package com.yice.edu.cn.ewb.feignClient.courseware;

import com.yice.edu.cn.common.pojo.jy.courseware.CourseTestAnswer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="jy",path = "/courseTestAnswer")
public interface CourseTestAnswerFeign {
    @GetMapping("/findCourseTestAnswerById/{id}")
    CourseTestAnswer findCourseTestAnswerById(@PathVariable("id") String id);
    @PostMapping("/saveCourseTestAnswer")
    CourseTestAnswer saveCourseTestAnswer(CourseTestAnswer courseTestAnswer);
    @PostMapping("/findCourseTestAnswerListByCondition")
    List<CourseTestAnswer> findCourseTestAnswerListByCondition(CourseTestAnswer courseTestAnswer);
    @PostMapping("/findOneCourseTestAnswerByCondition")
    CourseTestAnswer findOneCourseTestAnswerByCondition(CourseTestAnswer courseTestAnswer);
    @PostMapping("/findCourseTestAnswerCountByCondition")
    long findCourseTestAnswerCountByCondition(CourseTestAnswer courseTestAnswer);
    @PostMapping("/updateCourseTestAnswer")
    void updateCourseTestAnswer(CourseTestAnswer courseTestAnswer);
    @GetMapping("/deleteCourseTestAnswer/{id}")
    void deleteCourseTestAnswer(@PathVariable("id") String id);
    @PostMapping("/deleteCourseTestAnswerByCondition")
    void deleteCourseTestAnswerByCondition(CourseTestAnswer courseTestAnswer);
}
