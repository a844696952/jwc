package com.yice.edu.cn.jw.feign.analysisScore;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value="jy",contextId = "AnalysisScoreFeign",path = "/analyseClassScore")
public interface AnalysisScoreFeign {
    @PostMapping("/saveTopClazzScoreAnalyse")
    public void saveTopClazzScoreAnalyse(List<String> clazzIdList);
}
