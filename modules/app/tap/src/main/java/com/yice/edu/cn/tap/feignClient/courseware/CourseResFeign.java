package com.yice.edu.cn.tap.feignClient.courseware;

import com.yice.edu.cn.common.pojo.jy.courseware.CourseRes;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="jy",path = "/courseRes")
public interface CourseResFeign {
    @GetMapping("/findCourseResById/{id}")
    CourseRes findCourseResById(@PathVariable("id") String id);
    @PostMapping("/saveCourseRes")
    CourseRes saveCourseRes(CourseRes courseRes);
    @PostMapping("/findCourseResListByCondition")
    List<CourseRes> findCourseResListByCondition(CourseRes courseRes);
    @PostMapping("/findOneCourseResByCondition")
    CourseRes findOneCourseResByCondition(CourseRes courseRes);
    @PostMapping("/findCourseResCountByCondition")
    long findCourseResCountByCondition(CourseRes courseRes);
    @PostMapping("/updateCourseRes")
    void updateCourseRes(CourseRes courseRes);
    @GetMapping("/deleteCourseRes/{id}")
    void deleteCourseRes(@PathVariable("id") String id);
    @PostMapping("/deleteCourseResByCondition")
    void deleteCourseResByCondition(CourseRes courseRes);

    @PostMapping("/mvs")
    void mvs(CourseRes courseRes);

    @PostMapping("/deletes")
    void deletes(List<String> ids);

    @PostMapping("/rename")
    CourseRes rename(CourseRes courseRes);

    @PostMapping("/mv")
    CourseRes mv(CourseRes courseRes);

    @PostMapping("/saveTestCourseRes")
    CourseRes saveTestCourseRes(CourseRes courseRes);

    @PostMapping("/remark")
    void remark(CourseRes courseRes);
}
