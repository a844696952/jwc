package com.yice.edu.cn.ewb.feignClient.teacher;

import com.yice.edu.cn.common.pojo.jw.auth.Perm;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

@FeignClient(value="jw",contextId = "teacherFeign",path = "teacher")
public interface TeacherFeign {

    @PostMapping("/login")
    Teacher login(Teacher teacher);

    @GetMapping("/findTeacherByTel/{tel}")
    Teacher findTeacherByTel(@PathVariable("tel")String tel);

    @PostMapping("/findTeacherCountByCondition")
    long findTeacherCountByCondition(Teacher teacher);


    @PostMapping("/updateTeacherAdmin")
     void updateTeacherAdmin(Teacher teacher);

    @GetMapping("/findTeacherById/{id}")
     Teacher findTeacherById(@PathVariable("id") String id);

    @PostMapping("/findCorrespondencesByTeacher")
    List<Teacher> findCorrespondencesByTeacher(Teacher teacher);

    @PostMapping("/findTeacherListByCondition")
     List<Teacher> findTeacherListByCondition(Teacher teacher);

    @PostMapping("/findTeacherCountByCondition4Like")
    long findTeacherCountByCondition4Like(Teacher teacher);

    @PostMapping("/findTeacherListByConditionRegister")
    List<Teacher> findTeacherListByConditionRegister(Teacher teacher);

    @PostMapping("/findTeacherOrstudentByName")
    Map<String,List> findTeacherOrstudentByName(Teacher teacher);

    @PostMapping("/findTeacherListByCondition4Like")
    List<Teacher> findTeacherListByCondition4Like(Teacher teacher);

    @GetMapping("/findTeacherMenu4App/{id}")
    List<Perm> findTeacherMenu4App(@PathVariable("id") String id);
}
