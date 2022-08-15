package com.yice.edu.cn.bmp.feignClient.course;

import com.yice.edu.cn.common.pojo.jw.JwCourse.JwCourse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Repository
@FeignClient(value = "jw",contextId = "jwCourseFeign",path = "/jwCourse")
public interface JwCourseFeign {
    @GetMapping("/findJwCourseById/{id}")
    JwCourse findJwCourseById(@PathVariable("id") String id);

    @PostMapping("/saveJwCourse")
    JwCourse saveJwCourse(JwCourse jwCourse);

    @PostMapping("/findJwCourseListByCondition")
    List<JwCourse> findJwCourseListByCondition(JwCourse jwCourse);

    @PostMapping("/findJwCourseCountByCondition")
    long findJwCourseCountByCondition(JwCourse jwCourse);

    @PostMapping("/findJwCourseListByConditionGai")
    List<JwCourse> findJwCourseListByConditionGai(JwCourse jwCourse);
}

