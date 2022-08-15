package com.yice.edu.cn.bmp.service.course;

import com.yice.edu.cn.bmp.feignClient.course.JwCourseFeign;
import com.yice.edu.cn.common.pojo.jw.JwCourse.JwCourse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JwCourseService {
    @Autowired
    private JwCourseFeign jwCourseFeign;

    public JwCourse findJwCourseById(String id) {
        return jwCourseFeign.findJwCourseById(id);
    }


    public List<JwCourse> findJwCourseListByCondition(JwCourse jwCourse) {
        return jwCourseFeign.findJwCourseListByConditionGai(jwCourse);
    }

    public long findJwCourseCountByCondition(JwCourse jwCourse) {
        return jwCourseFeign.findJwCourseCountByCondition(jwCourse);
    }
}
