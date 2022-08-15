package com.yice.edu.cn.tap.feignClient.dutyManagement;

import com.yice.edu.cn.common.pojo.xw.routineDutyFeedback.RoutineDutyFeedback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="xw",path = "/routineDutyFeedback")
public interface RoutineDutyFeedbackFeign {
    @GetMapping("/findRoutineDutyFeedbackById/{id}")
    RoutineDutyFeedback findRoutineDutyFeedbackById(@PathVariable("id") String id);
    @PostMapping("/saveRoutineDutyFeedback")
    RoutineDutyFeedback saveRoutineDutyFeedback(RoutineDutyFeedback routineDutyFeedback);
    @PostMapping("/findRoutineDutyFeedbackListByCondition")
    List<RoutineDutyFeedback> findRoutineDutyFeedbackListByCondition(RoutineDutyFeedback routineDutyFeedback);
    @PostMapping("/findOneRoutineDutyFeedbackByCondition")
    RoutineDutyFeedback findOneRoutineDutyFeedbackByCondition(RoutineDutyFeedback routineDutyFeedback);
    @PostMapping("/findRoutineDutyFeedbackCountByCondition")
    long findRoutineDutyFeedbackCountByCondition(RoutineDutyFeedback routineDutyFeedback);
    @PostMapping("/updateRoutineDutyFeedback")
    void updateRoutineDutyFeedback(RoutineDutyFeedback routineDutyFeedback);
    @GetMapping("/deleteRoutineDutyFeedback/{id}")
    void deleteRoutineDutyFeedback(@PathVariable("id") String id);
    @PostMapping("/deleteRoutineDutyFeedbackByCondition")
    void deleteRoutineDutyFeedbackByCondition(RoutineDutyFeedback routineDutyFeedback);
}
