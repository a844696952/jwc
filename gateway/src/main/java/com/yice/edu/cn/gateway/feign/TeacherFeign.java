package com.yice.edu.cn.gateway.feign;

import com.yice.edu.cn.common.pojo.jw.auth.Perm;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(value="jw",contextId = "teacherFeign",path = "/teacher")
public interface TeacherFeign {
    @GetMapping("/findTeacherFuncPermsByTeacherId/{id}")
    List<Perm> findTeacherFuncPermsByTeacherId(@PathVariable("id") String id);
}
