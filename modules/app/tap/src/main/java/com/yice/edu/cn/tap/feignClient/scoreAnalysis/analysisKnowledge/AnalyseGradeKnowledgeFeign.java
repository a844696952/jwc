package com.yice.edu.cn.tap.feignClient.scoreAnalysis.analysisKnowledge;

import com.yice.edu.cn.common.pojo.xq.analysisKnowledge.AnalyseGradeKnowledge;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="jy",path = "/analyseGradeKnowledge")
public interface AnalyseGradeKnowledgeFeign {
    @GetMapping("/findAnalyseGradeKnowledgeById/{id}")
    AnalyseGradeKnowledge findAnalyseGradeKnowledgeById(@PathVariable("id") String id);
    @PostMapping("/saveAnalyseGradeKnowledge")
    AnalyseGradeKnowledge saveAnalyseGradeKnowledge(AnalyseGradeKnowledge analyseGradeKnowledge);
    @PostMapping("/findAnalyseGradeKnowledgeListByCondition")
    List<AnalyseGradeKnowledge> findAnalyseGradeKnowledgeListByCondition(AnalyseGradeKnowledge analyseGradeKnowledge);
    @PostMapping("/findOneAnalyseGradeKnowledgeByCondition")
    AnalyseGradeKnowledge findOneAnalyseGradeKnowledgeByCondition(AnalyseGradeKnowledge analyseGradeKnowledge);
    @PostMapping("/findAnalyseGradeKnowledgeCountByCondition")
    long findAnalyseGradeKnowledgeCountByCondition(AnalyseGradeKnowledge analyseGradeKnowledge);
    @PostMapping("/updateAnalyseGradeKnowledge")
    void updateAnalyseGradeKnowledge(AnalyseGradeKnowledge analyseGradeKnowledge);
    @GetMapping("/deleteAnalyseGradeKnowledge/{id}")
    void deleteAnalyseGradeKnowledge(@PathVariable("id") String id);
    @PostMapping("/deleteAnalyseGradeKnowledgeByCondition")
    void deleteAnalyseGradeKnowledgeByCondition(AnalyseGradeKnowledge analyseGradeKnowledge);
}
