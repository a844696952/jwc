package com.yice.edu.cn.jw.controller.timetable;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.yice.edu.cn.common.pojo.jw.timetable.TimetableTime;
import com.yice.edu.cn.common.pojo.validateClass.GroupOne;
import com.yice.edu.cn.jw.service.timetable.TimetableTimeService;

import java.util.List;

@RestController
@RequestMapping("/timetableTime")
@Api(value = "/timetableTime",description = "课程表时间片次数模块")
public class TimetableTimeController {
    @Autowired
    private TimetableTimeService timetableTimeService;

    @GetMapping("/findTimetableTimeById/{id}")
    @ApiOperation(value = "通过id查找课程表时间片次数", notes = "返回课程表时间片次数对象")
    public TimetableTime findTimetableTimeById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return timetableTimeService.findTimetableTimeById(id);
    }

    @PostMapping("/saveTimetableTime")
    @ApiOperation(value = "保存课程表时间片次数", notes = "返回课程表时间片次数对象")
    public TimetableTime saveTimetableTime(
            @ApiParam(value = "课程表时间片次数对象", required = true)
            @RequestBody @Validated(value=GroupOne.class) TimetableTime timetableTime){
        return  timetableTimeService.saveTimetableTime(timetableTime);
    }

    @PostMapping("/findTimetableTimeListByCondition")
    @ApiOperation(value = "根据条件查找课程表时间片次数列表", notes = "返回课程表时间片次数列表")
    public List<TimetableTime> findTimetableTimeListByCondition(
            @ApiParam(value = "课程表时间片次数对象")
            @RequestBody TimetableTime timetableTime){
        return timetableTimeService.findTimetableTimeListByCondition(timetableTime);
    }
    @PostMapping("/findTimetableTimeCountByCondition")
    @ApiOperation(value = "根据条件查找课程表时间片次数列表个数", notes = "返回课程表时间片次数总个数")
    public long findTimetableTimeCountByCondition(
            @ApiParam(value = "课程表时间片次数对象")
            @RequestBody TimetableTime timetableTime){
        return timetableTimeService.findTimetableTimeCountByCondition(timetableTime);
    }

    @PostMapping("/updateTimetableTime")
    @ApiOperation(value = "修改课程表时间片次数", notes = "课程表时间片次数对象必传")
    public void updateTimetableTime(
            @ApiParam(value = "课程表时间片次数对象,对象属性不为空则修改", required = true)
            @RequestBody TimetableTime timetableTime){
        timetableTimeService.updateTimetableTime(timetableTime);
    }

    @GetMapping("/deleteTimetableTime/{id}")
    @ApiOperation(value = "通过id删除课程表时间片次数")
    public void deleteTimetableTime(
            @ApiParam(value = "课程表时间片次数对象", required = true)
            @PathVariable String id){
        timetableTimeService.deleteTimetableTime(id);
    }
    @PostMapping("/deleteTimetableTimeByCondition")
    @ApiOperation(value = "根据条件删除课程表时间片次数")
    public void deleteTimetableTimeByCondition(
            @ApiParam(value = "课程表时间片次数对象")
            @RequestBody TimetableTime timetableTime){
        timetableTimeService.deleteTimetableTimeByCondition(timetableTime);
    }
    @PostMapping("/findOneTimetableTimeByCondition")
    @ApiOperation(value = "根据条件查找单个课程表时间片次数,结果必须为单条数据", notes = "返回单个课程表时间片次数,没有时为空")
    public TimetableTime findOneTimetableTimeByCondition(
            @ApiParam(value = "课程表时间片次数对象")
            @RequestBody TimetableTime timetableTime){
        return timetableTimeService.findOneTimetableTimeByCondition(timetableTime);
    }
}
