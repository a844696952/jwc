package com.yice.edu.cn.ewb.feignClient.scoreAnalysis.analysisStudentScore;

import com.yice.edu.cn.common.pojo.xq.analysisStudentScore.AnalysisStudentScoreAll;
import com.yice.edu.cn.common.pojo.xq.analysisStudentScore.AnalysisVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

@FeignClient(value="jy",contextId = "analysisStudentScoreAllFeign",path = "/analysisStudentScoreAll")
public interface AnalysisStudentScoreAllFeign {
    @GetMapping("/findAnalysisStudentScoreAllById/{id}")
    AnalysisStudentScoreAll findAnalysisStudentScoreAllById(@PathVariable("id") String id);
    @PostMapping("/saveAnalysisStudentScoreAll")
    AnalysisStudentScoreAll saveAnalysisStudentScoreAll(AnalysisStudentScoreAll analysisStudentScoreAll);
    @PostMapping("/findAnalysisStudentScoreAllListByCondition")
    List<AnalysisStudentScoreAll> findAnalysisStudentScoreAllListByCondition(AnalysisStudentScoreAll analysisStudentScoreAll);
    @PostMapping("/findOneAnalysisStudentScoreAllByCondition")
    AnalysisStudentScoreAll findOneAnalysisStudentScoreAllByCondition(AnalysisStudentScoreAll analysisStudentScoreAll);
    @PostMapping("/findAnalysisStudentScoreAllCountByCondition")
    long findAnalysisStudentScoreAllCountByCondition(AnalysisStudentScoreAll analysisStudentScoreAll);
    @PostMapping("/updateAnalysisStudentScoreAll")
    void updateAnalysisStudentScoreAll(AnalysisStudentScoreAll analysisStudentScoreAll);
    @GetMapping("/deleteAnalysisStudentScoreAll/{id}")
    void deleteAnalysisStudentScoreAll(@PathVariable("id") String id);
    @PostMapping("/deleteAnalysisStudentScoreAllByCondition")
    void deleteAnalysisStudentScoreAllByCondition(AnalysisStudentScoreAll analysisStudentScoreAll);
    @PostMapping("/findAnalysisStuScoreAllListByCondition")
    List<Map<String,Object>> findAnalysisStuScoreAllListByCondition(AnalysisVo analysisVo);
    @PostMapping("/findAnalysisStuScoreAllCountByCondition")
    long findAnalysisStuScoreAllCountByCondition(AnalysisVo analysisVo);

}
