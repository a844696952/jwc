package com.yice.edu.cn.osp.feignClient.xq.analysisClassScore;


import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.yice.edu.cn.common.pojo.xq.AnalyseClassScore.AnalyseSubjectGradeScore;

@FeignClient(value="jy",contextId = "analyseSubjectGradeScoreFeign",path = "/analyseSubjectGradeScore")
public interface AnalyseSubjectGradeScoreFeign {
    @GetMapping("/findAnalyseSubjectGradeScoreById/{id}")
    AnalyseSubjectGradeScore findAnalyseSubjectGradeScoreById(@PathVariable("id") String id);
    @PostMapping("/saveAnalyseSubjectGradeScore")
    AnalyseSubjectGradeScore saveAnalyseSubjectGradeScore(AnalyseSubjectGradeScore analyseSubjectGradeScore);
    @PostMapping("/findAnalyseSubjectGradeScoreListByCondition")
    List<AnalyseSubjectGradeScore> findAnalyseSubjectGradeScoreListByCondition(AnalyseSubjectGradeScore analyseSubjectGradeScore);
    @PostMapping("/findOneAnalyseSubjectGradeScoreByCondition")
    AnalyseSubjectGradeScore findOneAnalyseSubjectGradeScoreByCondition(AnalyseSubjectGradeScore analyseSubjectGradeScore);
    @PostMapping("/findAnalyseSubjectGradeScoreCountByCondition")
    long findAnalyseSubjectGradeScoreCountByCondition(AnalyseSubjectGradeScore analyseSubjectGradeScore);
    @PostMapping("/updateAnalyseSubjectGradeScore")
    void updateAnalyseSubjectGradeScore(AnalyseSubjectGradeScore analyseSubjectGradeScore);
    @GetMapping("/deleteAnalyseSubjectGradeScore/{id}")
    void deleteAnalyseSubjectGradeScore(@PathVariable("id") String id);
    @PostMapping("/deleteAnalyseSubjectGradeScoreByCondition")
    void deleteAnalyseSubjectGradeScoreByCondition(AnalyseSubjectGradeScore analyseSubjectGradeScore);
}
