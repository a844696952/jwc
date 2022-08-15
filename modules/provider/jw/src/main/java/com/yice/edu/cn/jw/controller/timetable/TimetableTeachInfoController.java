package com.yice.edu.cn.jw.controller.timetable;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.timetable.ArrangeClass;
import com.yice.edu.cn.common.pojo.jw.timetable.ArrangeCourse;
import com.yice.edu.cn.common.pojo.jw.timetable.ArrangeTeacher;
import com.yice.edu.cn.common.pojo.jw.timetable.TeachInfoBo;
import com.yice.edu.cn.common.pojo.jw.timetable.TimetableTeachInfo;
import com.yice.edu.cn.jw.service.timetable.TimetableTeachInfoService;

import java.util.List;

@RestController
@RequestMapping("/timetableTeachInfo")
@Api(value = "/timetableTeachInfo",description = "任课信息模块")
public class TimetableTeachInfoController {
    @Autowired
    private TimetableTeachInfoService timetableTeachInfoService;

    @GetMapping("/findTimetableTeachInfoById/{id}")
    @ApiOperation(value = "通过id查找任课信息", notes = "返回任课信息对象")
    public TimetableTeachInfo findTimetableTeachInfoById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return timetableTeachInfoService.findTimetableTeachInfoById(id);
    }

    @PostMapping("/saveTimetableTeachInfo")
    @ApiOperation(value = "保存任课信息", notes = "返回任课信息对象")
    public ResponseJson saveTimetableTeachInfo(
            @ApiParam(value = "任课信息对象", required = true)
            @RequestBody TeachInfoBo teachInfos){
        
        return timetableTeachInfoService.saveTimetableTeachInfo(teachInfos);
    }

    @PostMapping("/findTimetableTeachInfoListByCondition")
    @ApiOperation(value = "根据条件查找任课信息列表", notes = "返回任课信息列表")
    public List<TimetableTeachInfo> findTimetableTeachInfoListByCondition(
            @ApiParam(value = "任课信息对象")
            @RequestBody TimetableTeachInfo timetableTeachInfo){
        return timetableTeachInfoService.findTimetableTeachInfoListByCondition(timetableTeachInfo);
    }
    @PostMapping("/findTimetableTeachInfoCountByCondition")
    @ApiOperation(value = "根据条件查找任课信息列表个数", notes = "返回任课信息总个数")
    public long findTimetableTeachInfoCountByCondition(
            @ApiParam(value = "任课信息对象")
            @RequestBody TimetableTeachInfo timetableTeachInfo){
        return timetableTeachInfoService.findTimetableTeachInfoCountByCondition(timetableTeachInfo);
    }

    @PostMapping("/updateTimetableTeachInfo")
    @ApiOperation(value = "修改任课信息", notes = "任课信息对象必传")
    public void updateTimetableTeachInfo(
            @ApiParam(value = "任课信息对象,对象属性不为空则修改", required = true)
            @RequestBody TimetableTeachInfo timetableTeachInfo){
        timetableTeachInfoService.updateTimetableTeachInfo(timetableTeachInfo);
    }

    @GetMapping("/deleteTimetableTeachInfo/{id}")
    @ApiOperation(value = "通过id删除任课信息")
    public void deleteTimetableTeachInfo(
            @ApiParam(value = "任课信息对象", required = true)
            @PathVariable String id){
        timetableTeachInfoService.deleteTimetableTeachInfo(id);
    }
    @PostMapping("/deleteTimetableTeachInfoByCondition")
    @ApiOperation(value = "根据条件删除任课信息")
    public void deleteTimetableTeachInfoByCondition(
            @ApiParam(value = "任课信息对象")
            @RequestBody TimetableTeachInfo timetableTeachInfo){
        timetableTeachInfoService.deleteTimetableTeachInfoByCondition(timetableTeachInfo);
    }
    @PostMapping("/findOneTimetableTeachInfoByCondition")
    @ApiOperation(value = "根据条件查找单个任课信息,结果必须为单条数据", notes = "返回单个任课信息,没有时为空")
    public TimetableTeachInfo findOneTimetableTeachInfoByCondition(
            @ApiParam(value = "任课信息对象")
            @RequestBody TimetableTeachInfo timetableTeachInfo){
        return timetableTeachInfoService.findOneTimetableTeachInfoByCondition(timetableTeachInfo);
    }
    
    @GetMapping("/findTeachInfoCourseByJobId/{jobId}")
    public List<ArrangeCourse> findTeachInfoCourseByJobId(@PathVariable("jobId") String jobId){
    	return timetableTeachInfoService.findTeachInfoCourseByJobId(jobId);
    }
    
    @GetMapping("/findTeacherByJobIdAndCourseId")
    public List<ArrangeTeacher> findTeacherByJobIdAndCourseId(@RequestParam("jobId") String jobId ,@RequestParam("courseId") String courseId){
    	
    	return timetableTeachInfoService.findTeacherByJobIdAndCourseId(jobId, courseId);
    }
    
    @GetMapping("/findArrangeClassByJobId")
    public List<ArrangeClass> findArrangeClassByJobId(@RequestParam("jobId") String jobId){
    	
    	return timetableTeachInfoService.findArrangeClassByJobId(jobId);
    }
    
    
    
}
