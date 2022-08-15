package com.yice.edu.cn.ewb.feignClient.courseware;

import com.yice.edu.cn.common.pojo.jy.courseware.CourseWare;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="jy",contextId = "courseWareFeign",path = "/courseWare")
public interface CourseWareFeign {
    @GetMapping("/findCourseWareById/{id}")
    CourseWare findCourseWareById(@PathVariable("id") String id);
    @PostMapping("/saveCourseWare")
    CourseWare saveCourseWare(CourseWare courseWare);
    @PostMapping("/findCourseWareListByCondition")
    List<CourseWare> findCourseWareListByCondition(CourseWare courseWare);
    @PostMapping("/findOneCourseWareByCondition")
    CourseWare findOneCourseWareByCondition(CourseWare courseWare);
    @PostMapping("/findCourseWareCountByCondition")
    long findCourseWareCountByCondition(CourseWare courseWare);
    @PostMapping("/updateCourseWare")
    void updateCourseWare(CourseWare courseWare);
    @GetMapping("/deleteCourseWare/{id}")
    void deleteCourseWare(@PathVariable("id") String id);
    @PostMapping("/deleteCourseWareByCondition")
    void deleteCourseWareByCondition(CourseWare courseWare);

    @PostMapping("/deleteCourseWareByIds")
    void deleteCourseWareByIds(List<String> ids);

    @PostMapping("/batchUpdateCourseWare")
    void batchUpdateCourseWare(CourseWare courseWare);
    @PostMapping("/findRecentlyCourseWare")
    CourseWare findRecentlyCourseWare(CourseWare courseWare);


}
