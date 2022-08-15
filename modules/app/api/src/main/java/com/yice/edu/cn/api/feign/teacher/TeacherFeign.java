package com.yice.edu.cn.api.feign.teacher;

import com.yice.edu.cn.common.pojo.jw.auth.Perm;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="jw",contextId = "teacherFeign",path = "teacher")
public interface TeacherFeign {

    @PostMapping("/findTeacherListByCondition4Like")
    List<Teacher> findTeacherListByCondition4Like(Teacher teacher);

    @GetMapping("/findTeacherFuncPermsByTeacherId/{id}")
    List<Perm> findTeacherFuncPermsByTeacherId(@PathVariable("id") String id);

    @GetMapping("/findTeacherById/{id}")
    Teacher findTeacherById(@PathVariable("id") String id);

    @PostMapping("/login")
    Teacher login(Teacher teacher);
}
