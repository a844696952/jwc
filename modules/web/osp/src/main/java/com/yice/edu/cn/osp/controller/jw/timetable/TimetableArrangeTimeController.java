package com.yice.edu.cn.osp.controller.jw.timetable;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.timetable.TimetableArrangeTime;
import com.yice.edu.cn.common.pojo.validateClass.GroupOne;
import com.yice.edu.cn.osp.service.jw.timetable.TimetableArrangeTimeService;

import java.util.List;

import javax.validation.Valid;

@RestController
@RequestMapping("/timetableArrangeTime")
@Api(value = "/timetableArrangeTime",description = "教师、课程不排课时间条件模块")
public class TimetableArrangeTimeController {
    @Autowired
    private TimetableArrangeTimeService timetableArrangeTimeService;


    @PostMapping("/saveTimetableArrangeTime")
    @ApiOperation(value = "保存教师、课程不排课时间条件", notes = "返回教师、课程不排课时间条件对象")
    public ResponseJson saveTimetableArrangeTime(
            @ApiParam(value = "教师、课程不排课时间条件对象", required = true)
            @RequestBody @Valid List<TimetableArrangeTime> timetableArrangeTime){
        
        return timetableArrangeTimeService.saveTimetableArrangeTime(timetableArrangeTime);
    }

    @PostMapping("/findTimetableArrangeTimeListByCondition")
    @ApiOperation(value = "根据条件查找教师、课程不排课时间条件列表", notes = "返回教师、课程不排课时间条件列表")
    public List<TimetableArrangeTime> findTimetableArrangeTimeListByCondition(
            @ApiParam(value = "教师、课程不排课时间条件对象")
            @RequestBody TimetableArrangeTime timetableArrangeTime){
        return timetableArrangeTimeService.findTimetableArrangeTimeListByCondition(timetableArrangeTime);
    }
    @PostMapping("/findTimetableArrangeTimeCountByCondition")
    @ApiOperation(value = "根据条件查找教师、课程不排课时间条件列表个数", notes = "返回教师、课程不排课时间条件总个数")
    public long findTimetableArrangeTimeCountByCondition(
            @ApiParam(value = "教师、课程不排课时间条件对象")
            @RequestBody TimetableArrangeTime timetableArrangeTime){
        return timetableArrangeTimeService.findTimetableArrangeTimeCountByCondition(timetableArrangeTime);
    }

    @PostMapping("/updateTimetableArrangeTime")
    @ApiOperation(value = "修改教师、课程不排课时间条件", notes = "教师、课程不排课时间条件对象必传")
    public void updateTimetableArrangeTime(
            @ApiParam(value = "教师、课程不排课时间条件对象,对象属性不为空则修改", required = true)
            @RequestBody TimetableArrangeTime timetableArrangeTime){
        timetableArrangeTimeService.updateTimetableArrangeTime(timetableArrangeTime);
    }

    @GetMapping("/deleteTimetableArrangeTime/{id}")
    @ApiOperation(value = "通过id删除教师、课程不排课时间条件")
    public void deleteTimetableArrangeTime(
            @ApiParam(value = "教师、课程不排课时间条件对象", required = true)
            @PathVariable String id){
        timetableArrangeTimeService.deleteTimetableArrangeTime(id);
    }
    @PostMapping("/deleteTimetableArrangeTimeByCondition")
    @ApiOperation(value = "根据条件删除教师、课程不排课时间条件")
    public void deleteTimetableArrangeTimeByCondition(
            @ApiParam(value = "教师、课程不排课时间条件对象")
            @RequestBody TimetableArrangeTime timetableArrangeTime){
        timetableArrangeTimeService.deleteTimetableArrangeTimeByCondition(timetableArrangeTime);
    }
    @PostMapping("/findOneTimetableArrangeTimeByCondition")
    @ApiOperation(value = "根据条件查找单个教师、课程不排课时间条件,结果必须为单条数据", notes = "返回单个教师、课程不排课时间条件,没有时为空")
    public TimetableArrangeTime findOneTimetableArrangeTimeByCondition(
            @ApiParam(value = "教师、课程不排课时间条件对象")
            @RequestBody TimetableArrangeTime timetableArrangeTime){
        return timetableArrangeTimeService.findOneTimetableArrangeTimeByCondition(timetableArrangeTime);
    }
}
