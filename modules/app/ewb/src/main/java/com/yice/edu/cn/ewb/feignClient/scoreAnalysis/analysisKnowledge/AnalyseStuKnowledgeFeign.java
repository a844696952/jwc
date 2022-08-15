package com.yice.edu.cn.ewb.feignClient.scoreAnalysis.analysisKnowledge;

import com.yice.edu.cn.common.pojo.xq.analysisKnowledge.AnalyseStuKnowledge;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="jy",contextId = "analyseStuKnowledgeFeign",path = "/analyseStuKnowledge")
public interface AnalyseStuKnowledgeFeign {
    @GetMapping("/findAnalyseStuKnowledgeById/{id}")
    AnalyseStuKnowledge findAnalyseStuKnowledgeById(@PathVariable("id") String id);
    @PostMapping("/saveAnalyseStuKnowledge")
    AnalyseStuKnowledge saveAnalyseStuKnowledge(AnalyseStuKnowledge analyseStuKnowledge);
    @PostMapping("/findAnalyseStuKnowledgeListByCondition")
    List<AnalyseStuKnowledge> findAnalyseStuKnowledgeListByCondition(AnalyseStuKnowledge analyseStuKnowledge);
    @PostMapping("/findOneAnalyseStuKnowledgeByCondition")
    AnalyseStuKnowledge findOneAnalyseStuKnowledgeByCondition(AnalyseStuKnowledge analyseStuKnowledge);
    @PostMapping("/findAnalyseStuKnowledgeCountByCondition")
    long findAnalyseStuKnowledgeCountByCondition(AnalyseStuKnowledge analyseStuKnowledge);
    @PostMapping("/updateAnalyseStuKnowledge")
    void updateAnalyseStuKnowledge(AnalyseStuKnowledge analyseStuKnowledge);
    @GetMapping("/deleteAnalyseStuKnowledge/{id}")
    void deleteAnalyseStuKnowledge(@PathVariable("id") String id);
    @PostMapping("/deleteAnalyseStuKnowledgeByCondition")
    void deleteAnalyseStuKnowledgeByCondition(AnalyseStuKnowledge analyseStuKnowledge);
}
