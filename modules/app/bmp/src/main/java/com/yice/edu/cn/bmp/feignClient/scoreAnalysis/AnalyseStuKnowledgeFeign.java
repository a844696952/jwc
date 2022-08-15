package com.yice.edu.cn.bmp.feignClient.scoreAnalysis;


import com.yice.edu.cn.common.pojo.xq.analysisKnowledge.AnalyseStuKnowledge;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="jy",path = "/analyseStuKnowledge")
public interface AnalyseStuKnowledgeFeign {

    @PostMapping("/findAnalyseStuKnowledgeListByCondition")
    List<AnalyseStuKnowledge> findAnalyseStuKnowledgeListByCondition(AnalyseStuKnowledge analyseStuKnowledge);

}
