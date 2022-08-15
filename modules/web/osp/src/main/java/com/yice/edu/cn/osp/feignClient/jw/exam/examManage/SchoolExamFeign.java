package com.yice.edu.cn.osp.feignClient.jw.exam.examManage;

import com.yice.edu.cn.common.pojo.jw.JwCourse.JwCourse;
import com.yice.edu.cn.common.pojo.jw.exam.examManage.SchoolExam;
import com.yice.edu.cn.common.pojo.jw.exam.examManage.ScoreCond;
import com.yice.edu.cn.common.pojo.jw.exam.examManage.StuScore;
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
    @PostMapping("/findSchoolExamListByCondition1")
    List<SchoolExam> findSchoolExamListByCondition1(SchoolExam schoolExam);
    @PostMapping("/findOneSchoolExamByCondition")
    SchoolExam findOneSchoolExamByCondition(SchoolExam schoolExam);
    @PostMapping("/findSchoolExamCountByCondition1")
    long findSchoolExamCountByCondition1(SchoolExam schoolExam);
    @PostMapping("/findSchoolExamListByCondition")
    List<SchoolExam> findSchoolExamListByCondition(SchoolExam schoolExam);
    @PostMapping("/findSchoolExamCountByCondition")
    long findSchoolExamCountByCondition(SchoolExam schoolExam);
    @PostMapping("/updateSchoolExam")
    void updateSchoolExam(SchoolExam schoolExam);
    @GetMapping("/deleteSchoolExam/{id}")
    void deleteSchoolExam(@PathVariable("id") String id);
    @PostMapping("/deleteSchoolExamByCondition")
    void deleteSchoolExamByCondition(SchoolExam schoolExam);
    @PostMapping("/findSchoolExamListByCondition2")
    List<SchoolExam> findSchoolExamListByCondition2(SchoolExam schoolExam);
    @PostMapping("/findSchoolExamCountByCondition2")
    long findSchoolExamCountByCondition2(SchoolExam schoolExam);
    @PostMapping("/findStuScoresByCond")
    List<StuScore> findStuScoresByCond(ScoreCond scoreCond);
    @PostMapping("/findStuScoreCountByCond")
    long findStuScoreCountByCond(ScoreCond scoreCond);
    @PostMapping("/checkSchoolExamNum")
    long checkSchoolExamNum(SchoolExam schoolExam1);

    @GetMapping("/deleteSchoolExamById/{id}")
    SchoolExam deleteSchoolExamById(@PathVariable("id") String id);

    @GetMapping("/findListCoursePaper/{schoolExamId}")
    List<JwCourse> findListCoursePaper(@PathVariable("schoolExamId") String schoolExamId);
}
