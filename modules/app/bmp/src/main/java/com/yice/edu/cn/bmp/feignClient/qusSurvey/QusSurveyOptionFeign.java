package com.yice.edu.cn.bmp.feignClient.qusSurvey;

import com.yice.edu.cn.common.pojo.jw.qusSurvey.QusSurveyOption;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="jw",contextId = "qusSurveyOptionFeign",path = "/qusSurveyOption")
public interface QusSurveyOptionFeign {
    @GetMapping("/findQusSurveyOptionById/{id}")
    QusSurveyOption findQusSurveyOptionById(@PathVariable("id") String id);
    @PostMapping("/saveQusSurveyOption")
    QusSurveyOption saveQusSurveyOption(QusSurveyOption qusSurveyOption);
    @PostMapping("/findQusSurveyOptionListByCondition")
    List<QusSurveyOption> findQusSurveyOptionListByCondition(QusSurveyOption qusSurveyOption);
    @PostMapping("/findOneQusSurveyOptionByCondition")
    QusSurveyOption findOneQusSurveyOptionByCondition(QusSurveyOption qusSurveyOption);
    @PostMapping("/findQusSurveyOptionCountByCondition")
    long findQusSurveyOptionCountByCondition(QusSurveyOption qusSurveyOption);
    @PostMapping("/updateQusSurveyOption")
    void updateQusSurveyOption(QusSurveyOption qusSurveyOption);
    @GetMapping("/deleteQusSurveyOption/{id}")
    void deleteQusSurveyOption(@PathVariable("id") String id);
    @PostMapping("/deleteQusSurveyOptionByCondition")
    void deleteQusSurveyOptionByCondition(QusSurveyOption qusSurveyOption);
}
