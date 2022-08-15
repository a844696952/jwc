package com.yice.edu.cn.tap.feignClient.teacher;

import com.yice.edu.cn.common.pojo.jw.auth.Perm;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

@FeignClient(value="jw",path = "teacher")
public interface TeacherFeign {

    @PostMapping("/login")
    Teacher login(Teacher teacher);

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

    @GetMapping("/findDmssTeacherFuncPermsByTeacherId/{id}")
    List<Perm> findDmssTeacherFuncPermsByTeacherId(@PathVariable("id") String id);

    //查找该教师当班主任的班级findClassTeacherIsDirector
    @PostMapping("/findClassTeacherIsDirector")
    List<Teacher> findClassTeacherIsDirector(Teacher teacher);

    @PostMapping("/bindOpenId2Teacher")
    void bindOpenId2Teacher(Teacher teacher );
}
