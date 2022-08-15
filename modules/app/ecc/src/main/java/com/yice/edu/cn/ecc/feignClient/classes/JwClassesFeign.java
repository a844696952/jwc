package com.yice.edu.cn.ecc.feignClient.classes;

import com.yice.edu.cn.common.pojo.jw.classes.ClassesInfoViewVo;
import com.yice.edu.cn.common.pojo.jw.classes.JwClasses;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;



@FeignClient(value="jw",contextId = "jwClassesFeign",path = "/jwClasses")
public interface JwClassesFeign {
     @PostMapping("/getClassesTeacherAndMaster")
     ClassesInfoViewVo getClassesTeacherAndMaster(JwClasses jwClasses);
     @GetMapping("/findJwClassesById/{id}")
     JwClasses findJwClassesById(@PathVariable("id") String id);
}
