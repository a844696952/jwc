package com.yice.edu.cn.osp.feignClient.jw.qusSurvey;

import com.yice.edu.cn.common.pojo.jw.qusSurvey.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="jw",contextId = "qusSurveyFeign",path = "/qusSurvey")
public interface QusSurveyFeign {
    @GetMapping("/findQusSurveyById/{id}")
    QusSurvey findQusSurveyById(@PathVariable("id") String id);
    @PostMapping("/saveQusSurvey")
    QusSurvey saveQusSurvey(QusSurvey qusSurvey);
    @PostMapping("/findQusSurveyListByCondition")
    List<QusSurvey> findQusSurveyListByCondition(QusSurvey qusSurvey);
    @PostMapping("/findOneQusSurveyByCondition")
    QusSurvey findOneQusSurveyByCondition(QusSurvey qusSurvey);
    @PostMapping("/findQusSurveyCountByCondition")
    long findQusSurveyCountByCondition(QusSurvey qusSurvey);
    @PostMapping("/updateQusSurvey")
    QusSurvey updateQusSurvey(QusSurvey qusSurvey);
    @GetMapping("/deleteQusSurvey/{id}")
    void deleteQusSurvey(@PathVariable("id") String id);
    @PostMapping("/deleteQusSurveyByCondition")
    void deleteQusSurveyByCondition(QusSurvey qusSurvey);
    @PostMapping("/ConfirmSend")
    void ConfirmSend(QusSurveySendObject1 qusSurveySendObject1);
    @PostMapping("/findSendObjectQusSurveyListByCondition")
    List<SendObjectQusSurvey> findSendObjectQusSurveyListByCondition(SendObjectQusSurvey sendObjectQusSurvey);
    @PostMapping("/findQusSurveyTeacherSendClassesCountByCondition")
    long findQusSurveyTeacherSendClassesCountByCondition(QusSurveyTeacherSendClass qusSurveyTeacherSendClass);
    @PostMapping("/findQusSurveyTeacherSendClassesListByCondition")
    List<QusSurveyTeacherSendClass> findQusSurveyTeacherSendClassesListByCondition(QusSurveyTeacherSendClass qusSurveyTeacherSendClass);
    @PostMapping("/getQuestionTypeCountList")
    List<QusSurveyQuestion> getQuestionTypeCountList(QusSurveySubmit qusSurveySubmit);
    @PostMapping("/getTeacherSendClassTopSum")
    QusSurveyTeacherSendClass getTeacherSendClassTopSum(QusSurveyTeacherSendClass qusSurveyTeacherSendClass);
    @PostMapping("/findSendObjectQusSurveyCountByCondition")
    long findSendObjectQusSurveyCountByCondition(SendObjectQusSurvey sendObjectQusSurvey1);
    @PostMapping("/getTeacherQuestionTypeCountList")
    List<QusSurveyQuestion> getTeacherQuestionTypeCountList(QusSurveySubmit qusSurveySubmit);
    @PostMapping("/getTeacherEvaluateTopSum")
    QusSurveySubmit getTeacherEvaluateTopSum(QusSurveySubmit qusSurveySubmit);
    @PostMapping("/findOneSendObjectQusSurveyByCondition")
    SendObjectQusSurvey findOneSendObjectQusSurveyByCondition(SendObjectQusSurvey sendObjectQusSurvey);
    @PostMapping("/lookAlreadyQusSurvey")
    QusSurvey lookAlreadyQusSurvey(QusSurveySubmit qusSurveySubmit);
}
