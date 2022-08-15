package com.yice.edu.cn.ewb.service.course;

import com.yice.edu.cn.common.pojo.jw.JwCourse.JwCourse;
import com.yice.edu.cn.ewb.feignClient.course.JwCourseFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JwCourseService {
    @Autowired
    private JwCourseFeign jwCourseFeign;

    public List<JwCourse> findJwCourseListByCondition(JwCourse jwCourse) {
        return jwCourseFeign.findJwCourseListByCondition(jwCourse);
    }
}
