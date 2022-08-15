package com.yice.edu.cn.osp.feignClient.jw.qusSurvey;

import com.yice.edu.cn.common.pojo.jw.qusSurvey.QusSurveySubmit;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="jw",contextId = "qusSurveySubmitFeign",path = "/qusSurveySubmit")
public interface QusSurveySubmitFeign {
    @GetMapping("/findQusSurveySubmitById/{id}")
    QusSurveySubmit findQusSurveySubmitById(@PathVariable("id") String id);
    @PostMapping("/saveQusSurveySubmit")
    QusSurveySubmit saveQusSurveySubmit(QusSurveySubmit qusSurveySubmit);
    @PostMapping("/findQusSurveySubmitListByCondition")
    List<QusSurveySubmit> findQusSurveySubmitListByCondition(QusSurveySubmit qusSurveySubmit);
    @PostMapping("/findOneQusSurveySubmitByCondition")
    QusSurveySubmit findOneQusSurveySubmitByCondition(QusSurveySubmit qusSurveySubmit);
    @PostMapping("/findQusSurveySubmitCountByCondition")
    long findQusSurveySubmitCountByCondition(QusSurveySubmit qusSurveySubmit);
    @PostMapping("/updateQusSurveySubmit")
    void updateQusSurveySubmit(QusSurveySubmit qusSurveySubmit);
    @GetMapping("/deleteQusSurveySubmit/{id}")
    void deleteQusSurveySubmit(@PathVariable("id") String id);
    @PostMapping("/deleteQusSurveySubmitByCondition")
    void deleteQusSurveySubmitByCondition(QusSurveySubmit qusSurveySubmit);
    @PostMapping("/updateSubmit")
    void updateSubmit(QusSurveySubmit qusSurveySubmit);
}
