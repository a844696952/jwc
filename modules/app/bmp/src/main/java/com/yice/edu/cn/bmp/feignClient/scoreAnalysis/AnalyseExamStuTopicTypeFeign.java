package com.yice.edu.cn.bmp.feignClient.scoreAnalysis;

import com.yice.edu.cn.common.pojo.xq.AnalyseClassScore.AnalyseExamStuTopicType;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="jy",contextId = "AnalyseExamStuTopicTypeFeign",path = "/analyseExamStuTopicType")
public interface AnalyseExamStuTopicTypeFeign {
    @GetMapping("/findAnalyseExamStuTopicTypeById/{id}")
    AnalyseExamStuTopicType findAnalyseExamStuTopicTypeById(@PathVariable("id") String id);
    @PostMapping("/saveAnalyseExamStuTopicType")
    AnalyseExamStuTopicType saveAnalyseExamStuTopicType(AnalyseExamStuTopicType analyseExamStuTopicType);
    @PostMapping("/findAnalyseExamStuTopicTypeListByCondition")
    List<AnalyseExamStuTopicType> findAnalyseExamStuTopicTypeListByCondition(AnalyseExamStuTopicType analyseExamStuTopicType);
    @PostMapping("/findOneAnalyseExamStuTopicTypeByCondition")
    AnalyseExamStuTopicType findOneAnalyseExamStuTopicTypeByCondition(AnalyseExamStuTopicType analyseExamStuTopicType);
    @PostMapping("/findAnalyseExamStuTopicTypeCountByCondition")
    long findAnalyseExamStuTopicTypeCountByCondition(AnalyseExamStuTopicType analyseExamStuTopicType);
    @PostMapping("/updateAnalyseExamStuTopicType")
    void updateAnalyseExamStuTopicType(AnalyseExamStuTopicType analyseExamStuTopicType);
    @GetMapping("/deleteAnalyseExamStuTopicType/{id}")
    void deleteAnalyseExamStuTopicType(@PathVariable("id") String id);
    @PostMapping("/deleteAnalyseExamStuTopicTypeByCondition")
    void deleteAnalyseExamStuTopicTypeByCondition(AnalyseExamStuTopicType analyseExamStuTopicType);
}
