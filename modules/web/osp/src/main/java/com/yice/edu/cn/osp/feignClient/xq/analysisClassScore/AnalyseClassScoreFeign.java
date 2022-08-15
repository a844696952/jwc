package com.yice.edu.cn.osp.feignClient.xq.analysisClassScore;


import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.yice.edu.cn.common.pojo.xq.AnalyseClassScore.AnalyseClassScore;

import io.swagger.annotations.ApiOperation;

@FeignClient(value="jy",contextId = "analyseClassScoreFeign",path = "/analyseClassScore")
public interface AnalyseClassScoreFeign {
    @GetMapping("/findAnalyseClassScoreById/{id}")
    AnalyseClassScore findAnalyseClassScoreById(@PathVariable("id") String id);
    @PostMapping("/saveAnalyseClassScore")
    AnalyseClassScore saveAnalyseClassScore(AnalyseClassScore analyseClassScore);
    @PostMapping("/findAnalyseClassScoreListByCondition")
    List<AnalyseClassScore> findAnalyseClassScoreListByCondition(AnalyseClassScore analyseClassScore);
    @PostMapping("/findOneAnalyseClassScoreByCondition")
    AnalyseClassScore findOneAnalyseClassScoreByCondition(AnalyseClassScore analyseClassScore);
    @PostMapping("/findAnalyseClassScoreCountByCondition")
    long findAnalyseClassScoreCountByCondition(AnalyseClassScore analyseClassScore);
    @PostMapping("/updateAnalyseClassScore")
    void updateAnalyseClassScore(AnalyseClassScore analyseClassScore);
    @GetMapping("/deleteAnalyseClassScore/{id}")
    void deleteAnalyseClassScore(@PathVariable("id") String id);
    @PostMapping("/deleteAnalyseClassScoreByCondition")
    void deleteAnalyseClassScoreByCondition(AnalyseClassScore analyseClassScore);
    @PostMapping("/saveTopClazzScoreAnalyse")
    public void saveTopClazzScoreAnalyse(List<String> clazzIdList);
}
