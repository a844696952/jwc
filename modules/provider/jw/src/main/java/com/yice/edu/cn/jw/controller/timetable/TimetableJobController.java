package com.yice.edu.cn.jw.controller.timetable;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.timetable.TimetableJob;
import com.yice.edu.cn.common.pojo.validateClass.GroupOne;
import com.yice.edu.cn.jw.service.timetable.GARunService;
import com.yice.edu.cn.jw.service.timetable.TimetableJobService;


@RestController
@RequestMapping("/timetableJob")
@Api(value = "/timetableJob",description = "课程表任务模块")
public class TimetableJobController {
    @Autowired
    private TimetableJobService timetableJobService;
    
    @Autowired
    private GARunService GARun;

    @GetMapping("/findTimetableJobById/{id}")
    @ApiOperation(value = "通过id查找课程表任务", notes = "返回课程表任务对象")
    public TimetableJob findTimetableJobById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return timetableJobService.findTimetableJobById(id);
    }

    @PostMapping("/saveTimetableJob")
    @ApiOperation(value = "保存课程表任务", notes = "返回课程表任务对象")
    public TimetableJob saveTimetableJob(
            @ApiParam(value = "课程表任务对象", required = true)
            @RequestBody @Validated(value=GroupOne.class) TimetableJob timetableJob){
        return timetableJobService.saveTimetableJob(timetableJob);
    }

    @PostMapping("/findTimetableJobListByCondition")
    @ApiOperation(value = "根据条件查找课程表任务列表", notes = "返回课程表任务列表")
    public ResponseJson findTimetableJobListByCondition(
            @ApiParam(value = "课程表任务对象")
            @RequestBody  TimetableJob timetableJob){
        return timetableJobService.findTimetableJobListByCondition(timetableJob);
    }
    @PostMapping("/findTimetableJobCountByCondition")
    @ApiOperation(value = "根据条件查找课程表任务列表个数", notes = "返回课程表任务总个数")
    public long findTimetableJobCountByCondition(
            @ApiParam(value = "课程表任务对象")
            @RequestBody TimetableJob timetableJob){
        return timetableJobService.findTimetableJobCountByCondition(timetableJob);
    }

    @PostMapping("/updateTimetableJob")
    @ApiOperation(value = "修改课程表任务", notes = "课程表任务对象必传")
    public void updateTimetableJob(
            @ApiParam(value = "课程表任务对象,对象属性不为空则修改", required = true)
            @RequestBody TimetableJob timetableJob){
        timetableJobService.updateTimetableJob(timetableJob);
    }

    @GetMapping("/deleteTimetableJob/{id}")
    @ApiOperation(value = "通过id删除课程表任务")
    public void deleteTimetableJob(
            @ApiParam(value = "课程表任务对象", required = true)
            @PathVariable String id){
        timetableJobService.deleteTimetableJob(id);
    }
    @PostMapping("/deleteTimetableJobByCondition")
    @ApiOperation(value = "根据条件删除课程表任务")
    public void deleteTimetableJobByCondition(
            @ApiParam(value = "课程表任务对象")
            @RequestBody TimetableJob timetableJob){
        timetableJobService.deleteTimetableJobByCondition(timetableJob);
    }
    @PostMapping("/findOneTimetableJobByCondition")
    @ApiOperation(value = "根据条件查找单个课程表任务,结果必须为单条数据", notes = "返回单个课程表任务,没有时为空")
    public TimetableJob findOneTimetableJobByCondition(
            @ApiParam(value = "课程表任务对象")
            @RequestBody TimetableJob timetableJob){
        return timetableJobService.findOneTimetableJobByCondition(timetableJob);
    }
    
    @GetMapping("/findTeacherInfoByGradeId")
    public ResponseJson findTeacherInfoByGradeId(@RequestParam("gradeId") String gradeId,@RequestParam("schoolId") String schoolId,@RequestParam("jobId") String jobId){
    	
    	return timetableJobService.findTeacherInfoByGradeId(gradeId, schoolId,jobId);
    }
    
    
    @GetMapping("/startScheduling/{jobId}")
    public ResponseJson startScheduling(@PathVariable("jobId") String jobId) {
    	
    	
    	return GARun.execArrangeSchedule(jobId);
    	
    }
    
    @PostMapping("/findTeacherAndCourse")
    public ResponseJson findTeacherAndCourse(@RequestBody TimetableJob timetableJob) {
    	
    	return timetableJobService.findTeacherAndCourse(timetableJob);
    }
    
    @GetMapping("/copyJob")
    public ResponseJson copyJob(@RequestParam("jobId") String jobId,@RequestParam("rename") String rename) {
    	
    	return timetableJobService.copyJob(jobId,rename);
    }
    
}
