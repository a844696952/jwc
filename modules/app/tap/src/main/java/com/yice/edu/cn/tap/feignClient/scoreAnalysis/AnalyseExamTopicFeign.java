package com.yice.edu.cn.tap.feignClient.scoreAnalysis;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.yice.edu.cn.common.pojo.xq.AnalyseClassScore.AnalyseExamTopic;

@FeignClient(value="jy",path = "/analyseExamTopic")
public interface AnalyseExamTopicFeign {
    @GetMapping("/findAnalyseExamTopicById/{id}")
    AnalyseExamTopic findAnalyseExamTopicById(@PathVariable("id") String id);
    @PostMapping("/saveAnalyseExamTopic")
    AnalyseExamTopic saveAnalyseExamTopic(AnalyseExamTopic analyseExamTopic);
    @PostMapping("/findAnalyseExamTopicListByCondition")
    List<AnalyseExamTopic> findAnalyseExamTopicListByCondition(AnalyseExamTopic analyseExamTopic);
    @PostMapping("/findOneAnalyseExamTopicByCondition")
    AnalyseExamTopic findOneAnalyseExamTopicByCondition(AnalyseExamTopic analyseExamTopic);
    @PostMapping("/findAnalyseExamTopicCountByCondition")
    long findAnalyseExamTopicCountByCondition(AnalyseExamTopic analyseExamTopic);
    @PostMapping("/updateAnalyseExamTopic")
    void updateAnalyseExamTopic(AnalyseExamTopic analyseExamTopic);
    @GetMapping("/deleteAnalyseExamTopic/{id}")
    void deleteAnalyseExamTopic(@PathVariable("id") String id);
    @PostMapping("/deleteAnalyseExamTopicByCondition")
    void deleteAnalyseExamTopicByCondition(AnalyseExamTopic analyseExamTopic);
}