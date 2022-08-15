package com.yice.edu.cn.osp.feignClient.xq.analysisClassScore;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.yice.edu.cn.common.pojo.xq.AnalyseClassScore.AnalyseAllSubjectGradeScore;

@FeignClient(value="jy",contextId = "analyseAllSubjectGradeScoreFeign",path = "/analyseAllSubjectGradeScore")
public interface AnalyseAllSubjectGradeScoreFeign {
    @GetMapping("/findAnalyseAllSubjectGradeScoreById/{id}")
    AnalyseAllSubjectGradeScore findAnalyseAllSubjectGradeScoreById(@PathVariable("id") String id);
    @PostMapping("/saveAnalyseAllSubjectGradeScore")
    AnalyseAllSubjectGradeScore saveAnalyseAllSubjectGradeScore(AnalyseAllSubjectGradeScore analyseAllSubjectGradeScore);
    @PostMapping("/findAnalyseAllSubjectGradeScoreListByCondition")
    List<AnalyseAllSubjectGradeScore> findAnalyseAllSubjectGradeScoreListByCondition(AnalyseAllSubjectGradeScore analyseAllSubjectGradeScore);
    @PostMapping("/findOneAnalyseAllSubjectGradeScoreByCondition")
    AnalyseAllSubjectGradeScore findOneAnalyseAllSubjectGradeScoreByCondition(AnalyseAllSubjectGradeScore analyseAllSubjectGradeScore);
    @PostMapping("/findAnalyseAllSubjectGradeScoreCountByCondition")
    long findAnalyseAllSubjectGradeScoreCountByCondition(AnalyseAllSubjectGradeScore analyseAllSubjectGradeScore);
    @PostMapping("/updateAnalyseAllSubjectGradeScore")
    void updateAnalyseAllSubjectGradeScore(AnalyseAllSubjectGradeScore analyseAllSubjectGradeScore);
    @GetMapping("/deleteAnalyseAllSubjectGradeScore/{id}")
    void deleteAnalyseAllSubjectGradeScore(@PathVariable("id") String id);
    @PostMapping("/deleteAnalyseAllSubjectGradeScoreByCondition")
    void deleteAnalyseAllSubjectGradeScoreByCondition(AnalyseAllSubjectGradeScore analyseAllSubjectGradeScore);
}
