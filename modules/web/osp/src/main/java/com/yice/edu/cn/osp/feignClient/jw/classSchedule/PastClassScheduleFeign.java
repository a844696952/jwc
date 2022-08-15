package com.yice.edu.cn.osp.feignClient.jw.classSchedule;

import com.yice.edu.cn.common.pojo.jw.classSchedule.PastClassSchedule;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="jw",contextId = "pastClassScheduleFeign",path = "/pastClassSchedule")
public interface PastClassScheduleFeign {
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @GetMapping("/findPastClassScheduleById/{id}")
    PastClassSchedule findPastClassScheduleById(@PathVariable("id") String id);
    @PostMapping("/savePastClassSchedule")
    PastClassSchedule savePastClassSchedule(PastClassSchedule pastClassSchedule);
    @PostMapping("/batchSavePastClassSchedule")
    void batchSavePastClassSchedule(List<PastClassSchedule> pastClassSchedules);
    @PostMapping("/findPastClassScheduleListByCondition")
    List<PastClassSchedule> findPastClassScheduleListByCondition(PastClassSchedule pastClassSchedule);
    @PostMapping("/findOnePastClassScheduleByCondition")
    PastClassSchedule findOnePastClassScheduleByCondition(PastClassSchedule pastClassSchedule);
    @PostMapping("/findPastClassScheduleCountByCondition")
    long findPastClassScheduleCountByCondition(PastClassSchedule pastClassSchedule);
    @PostMapping("/updatePastClassSchedule")
    void updatePastClassSchedule(PastClassSchedule pastClassSchedule);
    @PostMapping("/updatePastClassScheduleForAll")
    void updatePastClassScheduleForAll(PastClassSchedule pastClassSchedule);
    @GetMapping("/deletePastClassSchedule/{id}")
    void deletePastClassSchedule(@PathVariable("id") String id);
    @PostMapping("/deletePastClassScheduleByCondition")
    void deletePastClassScheduleByCondition(PastClassSchedule pastClassSchedule);
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/

    @PostMapping("/findListPastClassScheduleByCountKong")
    List<PastClassSchedule> findListPastClassScheduleByCountKong(PastClassSchedule pastClassSchedule);
}
