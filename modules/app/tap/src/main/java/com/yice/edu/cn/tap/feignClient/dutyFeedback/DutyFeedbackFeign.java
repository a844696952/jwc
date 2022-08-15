package com.yice.edu.cn.tap.feignClient.dutyFeedback;

import com.yice.edu.cn.common.pojo.xw.dutyFeedback.DutyFeedback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="xw",path = "/dutyFeedback")
public interface DutyFeedbackFeign {
    @GetMapping("/findDutyFeedbackById/{id}")
    DutyFeedback findDutyFeedbackById(@PathVariable("id") String id);
    @PostMapping("/saveDutyFeedback")
    DutyFeedback saveDutyFeedback(DutyFeedback dutyFeedback);
    @PostMapping("/findDutyFeedbackListByCondition")
    List<DutyFeedback> findDutyFeedbackListByCondition(DutyFeedback dutyFeedback);
    @PostMapping("/findOneDutyFeedbackByCondition")
    DutyFeedback findOneDutyFeedbackByCondition(DutyFeedback dutyFeedback);
    @PostMapping("/findDutyFeedbackCountByCondition")
    long findDutyFeedbackCountByCondition(DutyFeedback dutyFeedback);
    @PostMapping("/updateDutyFeedback")
    void updateDutyFeedback(DutyFeedback dutyFeedback);
    @GetMapping("/deleteDutyFeedback/{id}")
    void deleteDutyFeedback(@PathVariable("id") String id);
    @PostMapping("/deleteDutyFeedbackByCondition")
    void deleteDutyFeedbackByCondition(DutyFeedback dutyFeedback);
}
