package com.yice.edu.cn.jy.feignClient.prepareLessons;

import com.yice.edu.cn.common.pojo.jw.teacher.TeacherClasses;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

@FeignClient(value = "jw",contextId = "teacherClassesFeign",path = "/teacherClasses")
public interface TeacherClassesFeign {

    @PostMapping("/findTeacherClassPostCourseList")
    List<Map<String, Object>> findTeacherClassPostCourseList(
            @RequestBody TeacherClasses teacherClasses);
}
