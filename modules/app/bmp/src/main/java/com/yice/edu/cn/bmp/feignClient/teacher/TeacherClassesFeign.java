package com.yice.edu.cn.bmp.feignClient.teacher;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.classes.JwClasses;
import com.yice.edu.cn.common.pojo.jw.teacher.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@FeignClient(value = "jw", path = "/teacherClasses")
public interface TeacherClassesFeign {

    @PostMapping("/findHeadmasterByClasses")
    Teacher findHeadmasterByClasses(@RequestBody TeacherClasses teacherClasses);

}