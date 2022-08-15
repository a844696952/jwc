package com.yice.edu.cn.api.feign.teacher;

import com.yice.edu.cn.common.pojo.jw.teacher.TeacherPost;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="jw",contextId = "teacherPostFeign",path = "/teacherPost")
public interface TeacherPostFeign {
    @PostMapping("/findTeacherPostListByCondition")
    List<TeacherPost> findTeacherPostListByCondition(TeacherPost teacherPost);

}
