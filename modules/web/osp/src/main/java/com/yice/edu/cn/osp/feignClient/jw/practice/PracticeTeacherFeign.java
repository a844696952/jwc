package com.yice.edu.cn.osp.feignClient.jw.practice;

import com.yice.edu.cn.common.pojo.jw.practice.PracticeTeacher;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="jw",contextId = "practiceTeacherFeign",path = "/practiceTeacher")
public interface PracticeTeacherFeign {
    @GetMapping("/findPracticeTeacherById/{id}")
    PracticeTeacher findPracticeTeacherById(@PathVariable("id") String id);
    @PostMapping("/savePracticeTeacher")
    PracticeTeacher savePracticeTeacher(PracticeTeacher practiceTeacher);
    @PostMapping("/findPracticeTeacherListByCondition")
    List<PracticeTeacher> findPracticeTeacherListByCondition(PracticeTeacher practiceTeacher);
    @PostMapping("/findOnePracticeTeacherByCondition")
    PracticeTeacher findOnePracticeTeacherByCondition(PracticeTeacher practiceTeacher);
    @PostMapping("/findPracticeTeacherCountByCondition")
    long findPracticeTeacherCountByCondition(PracticeTeacher practiceTeacher);
    @PostMapping("/updatePracticeTeacher")
    void updatePracticeTeacher(PracticeTeacher practiceTeacher);
    @GetMapping("/deletePracticeTeacher/{id}")
    void deletePracticeTeacher(@PathVariable("id") String id);
    @PostMapping("/deletePracticeTeacherByCondition")
    void deletePracticeTeacherByCondition(PracticeTeacher practiceTeacher);
    @GetMapping("/findPracticeTeacherListById/{id}")
    List<PracticeTeacher> findPracticeTeacherListById(@PathVariable("id") String id);
    @GetMapping("/findPracticeTeacherNameById/{id}")
    List<PracticeTeacher> findPracticeTeacherNameById(@PathVariable("id") String id);

}
