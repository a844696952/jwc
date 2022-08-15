package com.yice.edu.cn.jy.feignClient.examManage;

import com.yice.edu.cn.common.pojo.jw.exam.examManage.SchoolExam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="jw",contextId = "schoolExamFeign",path = "/schoolExam")
public interface SchoolExamFeign {
    @GetMapping("/findSchoolExamById/{id}")
    SchoolExam findSchoolExamById(@PathVariable("id") String id);
    @PostMapping("/saveSchoolExam")
    SchoolExam saveSchoolExam(SchoolExam schoolExam);
    @PostMapping("/findSchoolExamListByCondition")
    List<SchoolExam> findSchoolExamListByCondition(SchoolExam schoolExam);
    @PostMapping("/findOneSchoolExamByCondition")
    SchoolExam findOneSchoolExamByCondition(SchoolExam schoolExam);
    @PostMapping("/findSchoolExamCountByCondition")
    long findSchoolExamCountByCondition(SchoolExam schoolExam);
    @PostMapping("/updateSchoolExam")
    void updateSchoolExam(SchoolExam schoolExam);
    @GetMapping("/deleteSchoolExam/{id}")
    void deleteSchoolExam(@PathVariable("id") String id);
    @PostMapping("/deleteSchoolExamByCondition")
    void deleteSchoolExamByCondition(SchoolExam schoolExam);
}
