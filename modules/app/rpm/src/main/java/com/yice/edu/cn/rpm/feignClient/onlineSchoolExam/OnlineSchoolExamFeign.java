package com.yice.edu.cn.rpm.feignClient.onlineSchoolExam;

import com.yice.edu.cn.common.pojo.jw.exam.examManage.ExamStudentInfo;
import com.yice.edu.cn.common.pojo.jw.exam.examManage.SchoolExam;
import com.yice.edu.cn.common.pojo.jw.exam.examManage.StuScore;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value = "jw",path = "/schoolExam")
public interface OnlineSchoolExamFeign {
    @GetMapping("/findOnlineSchoolExamNow/{schoolId}")
    List<SchoolExam> findOnlineSchoolExamNow(@PathVariable("schoolId") String schoolId);
    @PostMapping("/commitStuScore")
    String commitStuScore(List<StuScore> stuScores);
    @GetMapping("/findSchoolExamById/{schoolExamId}")
    SchoolExam findSchoolExamById(@PathVariable("schoolExamId") String schoolExamId);
    @GetMapping("/findExamStudentsBySchoolExamId/{schoolExamId}")
    List<ExamStudentInfo> findExamStudentsBySchoolExamId(@PathVariable("schoolExamId") String schoolExamId);
    @PostMapping("/updateSchoolExam")
    void updateSchoolExam(SchoolExam schoolExam);
}
