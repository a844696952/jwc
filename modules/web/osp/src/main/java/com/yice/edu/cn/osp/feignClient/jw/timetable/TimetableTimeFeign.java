package com.yice.edu.cn.osp.feignClient.jw.timetable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import com.yice.edu.cn.common.pojo.jw.timetable.TimetableTime;

import java.util.List;

@FeignClient(value="jw",contextId = "timetableTimeFeign",path = "/timetableTime")
public interface TimetableTimeFeign {

    @GetMapping("/findTimetableTimeById/{id}")
    public TimetableTime findTimetableTimeById(@PathVariable("id") String id);

    @PostMapping("/saveTimetableTime")
    public TimetableTime saveTimetableTime(@RequestBody TimetableTime timetableTime);
    
    @PostMapping("/findTimetableTimeListByCondition")
    public List<TimetableTime> findTimetableTimeListByCondition(@RequestBody TimetableTime timetableTime);
    
    
    @PostMapping("/findTimetableTimeCountByCondition")
    public long findTimetableTimeCountByCondition(@RequestBody TimetableTime timetableTime);

    @PostMapping("/updateTimetableTime")
    public void updateTimetableTime(@RequestBody TimetableTime timetableTime);

    @GetMapping("/deleteTimetableTime/{id}")
    public void deleteTimetableTime(@PathVariable("id") String id);
    
    
    @PostMapping("/deleteTimetableTimeByCondition")
    public void deleteTimetableTimeByCondition(@RequestBody TimetableTime timetableTime);
    
    
    @PostMapping("/findOneTimetableTimeByCondition")
    public TimetableTime findOneTimetableTimeByCondition(@RequestBody TimetableTime timetableTime);
}
