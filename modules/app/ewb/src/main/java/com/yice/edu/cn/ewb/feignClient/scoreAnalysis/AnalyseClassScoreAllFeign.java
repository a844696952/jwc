package com.yice.edu.cn.ewb.feignClient.scoreAnalysis;


import com.yice.edu.cn.common.pojo.xq.AnalyseClassScore.AnalyseClassScoreAll;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="jy",contextId = "analyseClassScoreAllFeign",path = "/analyseClassScoreAll")
public interface AnalyseClassScoreAllFeign {
    @GetMapping("/findAnalyseClassScoreAllById/{id}")
    AnalyseClassScoreAll findAnalyseClassScoreAllById(@PathVariable("id") String id);
    @PostMapping("/saveAnalyseClassScoreAll")
    AnalyseClassScoreAll saveAnalyseClassScoreAll(AnalyseClassScoreAll analyseClassScoreAll);
    @PostMapping("/findAnalyseClassScoreAllListByCondition")
    List<AnalyseClassScoreAll> findAnalyseClassScoreAllListByCondition(AnalyseClassScoreAll analyseClassScoreAll);
    @PostMapping("/findOneAnalyseClassScoreAllByCondition")
    AnalyseClassScoreAll findOneAnalyseClassScoreAllByCondition(AnalyseClassScoreAll analyseClassScoreAll);
    @PostMapping("/findAnalyseClassScoreAllCountByCondition")
    long findAnalyseClassScoreAllCountByCondition(AnalyseClassScoreAll analyseClassScoreAll);
    @PostMapping("/updateAnalyseClassScoreAll")
    void updateAnalyseClassScoreAll(AnalyseClassScoreAll analyseClassScoreAll);
    @GetMapping("/deleteAnalyseClassScoreAll/{id}")
    void deleteAnalyseClassScoreAll(@PathVariable("id") String id);
    @PostMapping("/deleteAnalyseClassScoreAllByCondition")
    void deleteAnalyseClassScoreAllByCondition(AnalyseClassScoreAll analyseClassScoreAll);
}
