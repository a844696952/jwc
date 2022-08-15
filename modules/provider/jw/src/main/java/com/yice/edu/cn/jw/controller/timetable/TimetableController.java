package com.yice.edu.cn.jw.controller.timetable;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.timetable.Timetable;
import com.yice.edu.cn.common.pojo.jw.timetable.TimetableAdjustCondition;
import com.yice.edu.cn.common.pojo.validateClass.GroupOne;
import com.yice.edu.cn.common.pojo.validateClass.GroupTwo;
import com.yice.edu.cn.jw.service.timetable.TimetableService;

import java.util.List;

@RestController
@RequestMapping("/timetable")
@Api(value = "/timetable",description = "时间表模块")
public class TimetableController {
    @Autowired
    private TimetableService timetableService;

    @GetMapping("/findTimetableById/{id}")
    @ApiOperation(value = "通过id查找", notes = "返回对象")
    public Timetable findTimetableById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable("id") String id){
        return timetableService.findTimetableById(id);
    }

    @PostMapping("/saveTimetable")
    @ApiOperation(value = "保存", notes = "返回对象")
    public Timetable saveTimetable(
            @ApiParam(value = "对象", required = true)
            @RequestBody Timetable timetable){
        timetableService.saveTimetable(timetable);
        return timetable;
    }

    @PostMapping("/findTimetableListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
    public List<Timetable> findTimetableListByCondition(
            @ApiParam(value = "对象")
            @RequestBody Timetable timetable){
        return timetableService.findTimetableListByCondition(timetable);
    }
    @PostMapping("/findTimetableCountByCondition")
    @ApiOperation(value = "根据条件查找列表个数", notes = "返回总个数")
    public long findTimetableCountByCondition(
            @ApiParam(value = "对象")
            @RequestBody Timetable timetable){
        return timetableService.findTimetableCountByCondition(timetable);
    }

    @PostMapping("/updateTimetable")
    @ApiOperation(value = "修改", notes = "对象必传")
    public void updateTimetable(
            @ApiParam(value = "对象,对象属性不为空则修改", required = true)
            @RequestBody Timetable timetable){
        timetableService.updateTimetable(timetable);
    }

    @GetMapping("/deleteTimetable/{id}")
    @ApiOperation(value = "通过id删除")
    public void deleteTimetable(
            @ApiParam(value = "对象", required = true)
            @PathVariable String id){
        timetableService.deleteTimetable(id);
    }
    @PostMapping("/deleteTimetableByCondition")
    @ApiOperation(value = "根据条件删除")
    public void deleteTimetableByCondition(
            @ApiParam(value = "对象")
            @RequestBody Timetable timetable){
        timetableService.deleteTimetableByCondition(timetable);
    }
    @PostMapping("/findOneTimetableByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "返回单个,没有时为空")
    public Timetable findOneTimetableByCondition(
            @ApiParam(value = "对象")
            @RequestBody Timetable timetable){
        return timetableService.findOneTimetableByCondition(timetable);
    }
    
    
    @GetMapping("/findAllByJobId")
    @ApiOperation(value = "通过jobId查找时间表")
    public ResponseJson findAllTimetableByJobId(@RequestParam("jobId") String jobId,@RequestParam("type") Integer type) {
    	
    	return timetableService.findAllTimetableByJobId(jobId,type);
    	
    }
    
    
    @GetMapping("/findTeacherTimetableByJobId")
    @ApiOperation(value = "通过jobId查找教师时间表")
    public List<Timetable> findTeacherTimetableByJobId(@RequestParam("jobId") String jobId,@RequestParam("teacherId") String teacherId) {
    	
    	return timetableService.findTeacherTimetableByJobId(jobId,teacherId);
    	
    }
    
    @PostMapping("/adjustTimetable")
    public ResponseJson adjustTimetable(@RequestBody @Validated(value=GroupTwo.class) TimetableAdjustCondition condition) {
    	
    	return timetableService.adjustTimetable(condition);
    }
    
}
