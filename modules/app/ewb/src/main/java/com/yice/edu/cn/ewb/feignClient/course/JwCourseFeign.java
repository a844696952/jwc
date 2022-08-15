package com.yice.edu.cn.ewb.feignClient.course;

import com.yice.edu.cn.common.pojo.jw.JwCourse.JwCourse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
@Repository
@FeignClient(value = "jw", path = "/jwCourse")
public interface JwCourseFeign {
    @PostMapping("/findJwCourseListByConditionGai")
    List<JwCourse> findJwCourseListByConditionGai(JwCourse jwCourse);

    @PostMapping("/findJwCourseListByCondition")
    List<JwCourse> findJwCourseListByCondition(JwCourse jwCourse);
}
