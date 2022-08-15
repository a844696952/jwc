package com.yice.edu.cn.bmp.feignClient.feedback;

import com.yice.edu.cn.common.pojo.jw.feedback.Feedback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="jw",contextId = "teacherFeign",path = "/feedback")
public interface FeedbackFeign {
    @GetMapping("/findFeedbackById/{id}")
    Feedback findFeedbackById(@PathVariable("id") String id);
    @PostMapping("/saveFeedback")
    Feedback saveFeedback(Feedback feedback);
    @PostMapping("/findFeedbackListByCondition")
    List<Feedback> findFeedbackListByCondition(Feedback feedback);
    @PostMapping("/findOneFeedbackByCondition")
    Feedback findOneFeedbackByCondition(Feedback feedback);
    @PostMapping("/findFeedbackCountByCondition")
    long findFeedbackCountByCondition(Feedback feedback);
    @PostMapping("/updateFeedback")
    void updateFeedback(Feedback feedback);
    @GetMapping("/deleteFeedback/{id}")
    void deleteFeedback(@PathVariable("id") String id);
    @PostMapping("/deleteFeedbackByCondition")
    void deleteFeedbackByCondition(Feedback feedback);
}
