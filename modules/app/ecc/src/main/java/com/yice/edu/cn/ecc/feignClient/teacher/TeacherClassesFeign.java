package com.yice.edu.cn.ecc.feignClient.teacher;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.classes.JwClasses;
import com.yice.edu.cn.common.pojo.jw.teacher.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@FeignClient(value = "jw",contextId = "teacherClassesFeign",path = "/teacherClasses")
public interface TeacherClassesFeign {

    @PostMapping("/findClassTeacherListByClasses")
    List<Teacher4Classes> findClassTeacherListByClasses(TeacherClasses teacherClasses);

}