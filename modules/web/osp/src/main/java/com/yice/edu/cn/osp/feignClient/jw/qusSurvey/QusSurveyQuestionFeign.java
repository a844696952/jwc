package com.yice.edu.cn.osp.feignClient.jw.qusSurvey;

import com.yice.edu.cn.common.pojo.jw.qusSurvey.QusSurveyQuestion;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@FeignClient(value="jw",contextId = "qusSurveyQuestionFeign",path = "/qusSurveyQuestion")
public interface QusSurveyQuestionFeign {
    @GetMapping("/findQusSurveyQuestionById/{id}")
    QusSurveyQuestion findQusSurveyQuestionById(@PathVariable("id") String id);
    @PostMapping("/saveQusSurveyQuestion")
    QusSurveyQuestion saveQusSurveyQuestion(ArrayList<QusSurveyQuestion> qusSurveyQuestionList);
    @PostMapping("/findQusSurveyQuestionListByCondition")
    List<QusSurveyQuestion> findQusSurveyQuestionListByCondition(QusSurveyQuestion qusSurveyQuestion);
    @PostMapping("/findOneQusSurveyQuestionByCondition")
    QusSurveyQuestion findOneQusSurveyQuestionByCondition(QusSurveyQuestion qusSurveyQuestion);
    @PostMapping("/findQusSurveyQuestionCountByCondition")
    long findQusSurveyQuestionCountByCondition(QusSurveyQuestion qusSurveyQuestion);
    @PostMapping("/updateQusSurveyQuestion")
    void updateQusSurveyQuestion(QusSurveyQuestion qusSurveyQuestion);
    @GetMapping("/deleteQusSurveyQuestion/{id}")
    void deleteQusSurveyQuestion(@PathVariable("id") String id);
    @PostMapping("/deleteQusSurveyQuestionByCondition")
    void deleteQusSurveyQuestionByCondition(QusSurveyQuestion qusSurveyQuestion);
}
