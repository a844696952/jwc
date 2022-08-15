package com.yice.edu.cn.tap.feignClient.scoreAnalysis.analysisKnowledge;

import com.yice.edu.cn.common.pojo.xq.analysisKnowledge.AnalyseClassKnowledge;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="jy",path = "/analyseClassKnowledge")
public interface AnalyseClassKnowledgeFeign {
    @GetMapping("/findAnalyseClassKnowledgeById/{id}")
    AnalyseClassKnowledge findAnalyseClassKnowledgeById(@PathVariable("id") String id);
    @PostMapping("/saveAnalyseClassKnowledge")
    AnalyseClassKnowledge saveAnalyseClassKnowledge(AnalyseClassKnowledge analyseClassKnowledge);
    @PostMapping("/findAnalyseClassKnowledgeListByCondition")
    List<AnalyseClassKnowledge> findAnalyseClassKnowledgeListByCondition(AnalyseClassKnowledge analyseClassKnowledge);
    @PostMapping("/findOneAnalyseClassKnowledgeByCondition")
    AnalyseClassKnowledge findOneAnalyseClassKnowledgeByCondition(AnalyseClassKnowledge analyseClassKnowledge);
    @PostMapping("/findAnalyseClassKnowledgeCountByCondition")
    long findAnalyseClassKnowledgeCountByCondition(AnalyseClassKnowledge analyseClassKnowledge);
    @PostMapping("/updateAnalyseClassKnowledge")
    void updateAnalyseClassKnowledge(AnalyseClassKnowledge analyseClassKnowledge);
    @GetMapping("/deleteAnalyseClassKnowledge/{id}")
    void deleteAnalyseClassKnowledge(@PathVariable("id") String id);
    @PostMapping("/deleteAnalyseClassKnowledgeByCondition")
    void deleteAnalyseClassKnowledgeByCondition(AnalyseClassKnowledge analyseClassKnowledge);
}
