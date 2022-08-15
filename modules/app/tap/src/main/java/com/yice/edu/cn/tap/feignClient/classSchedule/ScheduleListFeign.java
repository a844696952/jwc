package com.yice.edu.cn.tap.feignClient.classSchedule;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.classSchedule.ScheduleList;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="jw",contextId = "scheduleListFeign",path = "/scheduleList")
public interface ScheduleListFeign {
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @GetMapping("/findScheduleListById/{id}")
    ScheduleList findScheduleListById(@PathVariable("id") String id);
    @PostMapping("/saveScheduleList")
    ScheduleList saveScheduleList(ScheduleList scheduleList);
    @PostMapping("/batchSaveScheduleList")
    void batchSaveScheduleList(List<ScheduleList> scheduleLists);
    @PostMapping("/findScheduleListListByCondition")
    List<ScheduleList> findScheduleListListByCondition(ScheduleList scheduleList);
    @PostMapping("/findOneScheduleListByCondition")
    ScheduleList findOneScheduleListByCondition(ScheduleList scheduleList);
    @PostMapping("/findScheduleListCountByCondition")
    long findScheduleListCountByCondition(ScheduleList scheduleList);
    @PostMapping("/updateScheduleList")
    void updateScheduleList(ScheduleList scheduleList);
    @PostMapping("/updateScheduleListForAll")
    void updateScheduleListForAll(ScheduleList scheduleList);
    @GetMapping("/deleteScheduleList/{id}")
    void deleteScheduleList(@PathVariable("id") String id);
    @PostMapping("/deleteScheduleListByCondition")
    void deleteScheduleListByCondition(ScheduleList scheduleList);
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
    @PostMapping("/updateScheduleAndSavePastSchedule")
    void updateScheduleAndSavePastSchedule(ScheduleList scheduleList);
    @PostMapping("/saveScheduleListKong")
    ResponseJson saveScheduleListKong(ScheduleList scheduleList);
    @GetMapping("/deleteScheduleAndClassSchedule/{id}")
    void  deleteScheduleAndClassSchedule(@PathVariable("id") String id);
    @PostMapping("/issueScheduleListKong")
    ResponseJson issueScheduleListKong(ScheduleList scheduleList);
}
