package com.yice.edu.cn.tap.feignClient.scoreAnalysis;

import com.yice.edu.cn.common.pojo.xq.AnalyseClassScore.AnalyseExamTopicType;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="jy",contextId = "AnalyseExamTopicTypeFeign",path = "/analyseExamTopicType")
public interface AnalyseExamTopicTypeFeign {
    @GetMapping("/findAnalyseExamTopicTypeById/{id}")
    AnalyseExamTopicType findAnalyseExamTopicTypeById(@PathVariable("id") String id);
    @PostMapping("/saveAnalyseExamTopicType")
    AnalyseExamTopicType saveAnalyseExamTopicType(AnalyseExamTopicType analyseExamTopicType);
    @PostMapping("/findAnalyseExamTopicTypeListByCondition")
    List<AnalyseExamTopicType> findAnalyseExamTopicTypeListByCondition(AnalyseExamTopicType analyseExamTopicType);
    @PostMapping("/findOneAnalyseExamTopicTypeByCondition")
    AnalyseExamTopicType findOneAnalyseExamTopicTypeByCondition(AnalyseExamTopicType analyseExamTopicType);
    @PostMapping("/findAnalyseExamTopicTypeCountByCondition")
    long findAnalyseExamTopicTypeCountByCondition(AnalyseExamTopicType analyseExamTopicType);
    @PostMapping("/updateAnalyseExamTopicType")
    void updateAnalyseExamTopicType(AnalyseExamTopicType analyseExamTopicType);
    @GetMapping("/deleteAnalyseExamTopicType/{id}")
    void deleteAnalyseExamTopicType(@PathVariable("id") String id);
    @PostMapping("/deleteAnalyseExamTopicTypeByCondition")
    void deleteAnalyseExamTopicTypeByCondition(AnalyseExamTopicType analyseExamTopicType);
}
