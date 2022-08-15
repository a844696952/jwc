package com.yice.edu.cn.yed.feignClient.jy.subjectSource;


import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.yice.edu.cn.common.pojo.jy.subjectSourse.ExamPointKnowledge;

@FeignClient(value="jy",contextId = "examPointKnowledgeFeign",path = "/examPointKnowledge")
public interface ExamPointKnowledgeFeign {
    @GetMapping("/findExamPointKnowledgeById/{id}")
    ExamPointKnowledge findExamPointKnowledgeById(@PathVariable("id") String id);
    @PostMapping("/saveExamPointKnowledge")
    ExamPointKnowledge saveExamPointKnowledge(ExamPointKnowledge examPointKnowledge);
    @PostMapping("/findExamPointKnowledgeListByCondition")
    List<ExamPointKnowledge> findExamPointKnowledgeListByCondition(ExamPointKnowledge examPointKnowledge);
    @PostMapping("/findOneExamPointKnowledgeByCondition")
    ExamPointKnowledge findOneExamPointKnowledgeByCondition(ExamPointKnowledge examPointKnowledge);
    @PostMapping("/findExamPointKnowledgeCountByCondition")
    long findExamPointKnowledgeCountByCondition(ExamPointKnowledge examPointKnowledge);
    @PostMapping("/updateExamPointKnowledge")
    void updateExamPointKnowledge(ExamPointKnowledge examPointKnowledge);
    @GetMapping("/deleteExamPointKnowledge/{id}")
    void deleteExamPointKnowledge(@PathVariable("id") String id);
    @PostMapping("/deleteExamPointKnowledgeByCondition")
    void deleteExamPointKnowledgeByCondition(ExamPointKnowledge examPointKnowledge);
}
