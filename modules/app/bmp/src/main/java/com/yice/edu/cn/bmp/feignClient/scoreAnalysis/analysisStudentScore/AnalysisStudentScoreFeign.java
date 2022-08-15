package com.yice.edu.cn.bmp.feignClient.scoreAnalysis.analysisStudentScore;

import com.yice.edu.cn.common.pojo.xq.analysisStudentScore.AnalysisStudentScore;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="jy",contextId = "analysisStudentScoreFeign",path = "/analysisStudentScore")
public interface AnalysisStudentScoreFeign {
    @GetMapping("/findAnalysisStudentScoreById/{id}")
    AnalysisStudentScore findAnalysisStudentScoreById(@PathVariable("id") String id);
    @PostMapping("/saveAnalysisStudentScore")
    AnalysisStudentScore saveAnalysisStudentScore(AnalysisStudentScore analysisStudentScore);
    @PostMapping("/findAnalysisStudentScoreListByCondition")
    List<AnalysisStudentScore> findAnalysisStudentScoreListByCondition(AnalysisStudentScore analysisStudentScore);
    @PostMapping("/findOneAnalysisStudentScoreByCondition")
    AnalysisStudentScore findOneAnalysisStudentScoreByCondition(AnalysisStudentScore analysisStudentScore);
    @PostMapping("/findAnalysisStudentScoreCountByCondition")
    long findAnalysisStudentScoreCountByCondition(AnalysisStudentScore analysisStudentScore);
    @PostMapping("/updateAnalysisStudentScore")
    void updateAnalysisStudentScore(AnalysisStudentScore analysisStudentScore);
    @GetMapping("/deleteAnalysisStudentScore/{id}")
    void deleteAnalysisStudentScore(@PathVariable("id") String id);
    @PostMapping("/deleteAnalysisStudentScoreByCondition")
    void deleteAnalysisStudentScoreByCondition(AnalysisStudentScore analysisStudentScore);
    @GetMapping("/analysisStudentScore/{examinationId}")
    void analysisStudentScore(@PathVariable("examinationId") String examinationId);
}
