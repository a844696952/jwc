package com.yice.edu.cn.osp.feignClient.jw.timetable;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.timetable.TimetableArrangeTime;

import java.util.List;

@FeignClient(value="jw",contextId = "timetableArrangeTimeFeign",path = "/timetableArrangeTime")
public interface TimetableArrangeTimeFeign {

    @PostMapping("/saveTimetableArrangeTime")
    public ResponseJson saveTimetableArrangeTime(@RequestBody List<TimetableArrangeTime> timetableArrangeTime);

    @PostMapping("/findTimetableArrangeTimeListByCondition")
    public List<TimetableArrangeTime> findTimetableArrangeTimeListByCondition(@RequestBody TimetableArrangeTime timetableArrangeTime);
    
    @PostMapping("/findTimetableArrangeTimeCountByCondition")
    public long findTimetableArrangeTimeCountByCondition(@RequestBody TimetableArrangeTime timetableArrangeTime);

    @PostMapping("/updateTimetableArrangeTime")
    public void updateTimetableArrangeTime(@RequestBody TimetableArrangeTime timetableArrangeTime);

    @GetMapping("/deleteTimetableArrangeTime/{id}")
    @ApiOperation(value = "通过id删除教师、课程不排课时间条件")
    public void deleteTimetableArrangeTime(@PathVariable("id") String id);
    
    @PostMapping("/deleteTimetableArrangeTimeByCondition")
    public void deleteTimetableArrangeTimeByCondition(@RequestBody TimetableArrangeTime timetableArrangeTime);
    
    @PostMapping("/findOneTimetableArrangeTimeByCondition")
    public TimetableArrangeTime findOneTimetableArrangeTimeByCondition(@RequestBody TimetableArrangeTime timetableArrangeTime);
}
