package com.yice.edu.cn.osp.feignClient.jw.exam.examManage;

import com.yice.edu.cn.common.pojo.jw.exam.examManage.ExamStudent;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="jw",contextId = "examStudentFeign",path = "/examStudent")
public interface ExamStudentFeign {
    @GetMapping("/findExamStudentById/{id}")
    ExamStudent findExamStudentById(@PathVariable("id") String id);
    @PostMapping("/saveExamStudent")
    ExamStudent saveExamStudent(ExamStudent examStudent);
    @PostMapping("/findExamStudentListByCondition")
    List<ExamStudent> findExamStudentListByCondition(ExamStudent examStudent);
    @PostMapping("/findOneExamStudentByCondition")
    ExamStudent findOneExamStudentByCondition(ExamStudent examStudent);
    @PostMapping("/findExamStudentCountByCondition")
    long findExamStudentCountByCondition(ExamStudent examStudent);
    @PostMapping("/updateExamStudent")
    void updateExamStudent(ExamStudent examStudent);
    @GetMapping("/deleteExamStudent/{id}")
    void deleteExamStudent(@PathVariable("id") String id);
    @PostMapping("/deleteExamStudentByCondition")
    void deleteExamStudentByCondition(ExamStudent examStudent);
    @PostMapping("/batchSaveExamStudent")
    void batchSaveExamStudent(List<ExamStudent> examStudents);
}
