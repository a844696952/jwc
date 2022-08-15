package com.yice.edu.cn.osp.feignClient.xq.analysisStudentScoreHistory;

import com.yice.edu.cn.common.pojo.xq.analyseDataHistory.AnalysisStudentScoreHistory;
import com.yice.edu.cn.common.pojo.xq.analysisStudentScore.AnalysisStudentScore;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="jy",path = "/analysisStudentScoreHistory")
public interface AnalysisStudentScoreHistoryFeign {
    @GetMapping("/findAnalysisStudentScoreHistoryById/{id}")
    AnalysisStudentScoreHistory findAnalysisStudentScoreHistoryById(@PathVariable("id") String id);
    @PostMapping("/saveAnalysisStudentScoreHistory")
    AnalysisStudentScoreHistory saveAnalysisStudentScoreHistory(AnalysisStudentScoreHistory analysisStudentScoreHistory);
    @PostMapping("/batchSaveAnalysisStudentScoreHistory")
    void batchSaveAnalysisStudentScoreHistory(List<AnalysisStudentScoreHistory> analysisStudentScoreHistorys);
    @PostMapping("/findAnalysisStudentScoreHistoryListByCondition")
    List<AnalysisStudentScoreHistory> findAnalysisStudentScoreHistoryListByCondition(AnalysisStudentScoreHistory analysisStudentScoreHistory);
    @PostMapping("/findOneAnalysisStudentScoreHistoryByCondition")
    AnalysisStudentScoreHistory findOneAnalysisStudentScoreHistoryByCondition(AnalysisStudentScoreHistory analysisStudentScoreHistory);
    @PostMapping("/findAnalysisStudentScoreHistoryCountByCondition")
    long findAnalysisStudentScoreHistoryCountByCondition(AnalysisStudentScoreHistory analysisStudentScoreHistory);
    @PostMapping("/updateAnalysisStudentScoreHistory")
    void updateAnalysisStudentScoreHistory(AnalysisStudentScoreHistory analysisStudentScoreHistory);
    @GetMapping("/deleteAnalysisStudentScoreHistory/{id}")
    void deleteAnalysisStudentScoreHistory(@PathVariable("id") String id);
    @PostMapping("/deleteAnalysisStudentScoreHistoryByCondition")
    void deleteAnalysisStudentScoreHistoryByCondition(AnalysisStudentScoreHistory analysisStudentScoreHistory);
    @PostMapping("/findStudentScoresCountByCondition")
    long findStudentScoresCountByCondition(AnalysisStudentScoreHistory analysisStudentScoreHistory);
}
