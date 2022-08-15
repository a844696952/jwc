package com.yice.edu.cn.osp.feignClient.xq.analysisClassScore;


import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.yice.edu.cn.common.pojo.xq.AnalyseClassScore.AnalyseExamTopicStu;

@FeignClient(value="jy",contextId = "analyseExamTopicStuFeign",path = "/analyseExamTopicStu")
public interface AnalyseExamTopicStuFeign {
    @GetMapping("/findAnalyseExamTopicStuById/{id}")
    AnalyseExamTopicStu findAnalyseExamTopicStuById(@PathVariable("id") String id);
    @PostMapping("/saveAnalyseExamTopicStu")
    AnalyseExamTopicStu saveAnalyseExamTopicStu(AnalyseExamTopicStu analyseExamTopicStu);
    @PostMapping("/findAnalyseExamTopicStuListByCondition")
    List<AnalyseExamTopicStu> findAnalyseExamTopicStuListByCondition(AnalyseExamTopicStu analyseExamTopicStu);
    @PostMapping("/findOneAnalyseExamTopicStuByCondition")
    AnalyseExamTopicStu findOneAnalyseExamTopicStuByCondition(AnalyseExamTopicStu analyseExamTopicStu);
    @PostMapping("/findAnalyseExamTopicStuCountByCondition")
    long findAnalyseExamTopicStuCountByCondition(AnalyseExamTopicStu analyseExamTopicStu);
    @PostMapping("/updateAnalyseExamTopicStu")
    void updateAnalyseExamTopicStu(AnalyseExamTopicStu analyseExamTopicStu);
    @GetMapping("/deleteAnalyseExamTopicStu/{id}")
    void deleteAnalyseExamTopicStu(@PathVariable("id") String id);
    @PostMapping("/deleteAnalyseExamTopicStuByCondition")
    void deleteAnalyseExamTopicStuByCondition(AnalyseExamTopicStu analyseExamTopicStu);
}
