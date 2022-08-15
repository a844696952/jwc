package com.yice.edu.cn.osp.controller.jw.timetable;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.timetable.TimetableJob;
import com.yice.edu.cn.common.pojo.validateClass.GroupOne;
import com.yice.edu.cn.osp.service.jw.timetable.TimetableJobService;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.*;


@RestController
@RequestMapping("/timetableJob")
@Api(value = "/timetableJob",description = "课程表任务模块")
public class TimetableJobController {
    @Autowired
    private TimetableJobService timetableJobService;
    
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
    	timetableJob.setSchoolId(mySchoolId());
    	timetableJob.setCreateUserId(myId());
    	timetableJob.setType(0);
    	timetableJob.setStatus(1);
        return timetableJobService.saveTimetableJob(timetableJob);
    }

    @PostMapping("/findTimetableJobListByCondition")
    @ApiOperation(value = "根据条件查找课程表任务列表", notes = "返回课程表任务列表")
    public ResponseJson findTimetableJobListByCondition(
            @ApiParam(value = "课程表任务对象")
            @RequestBody  TimetableJob timetableJob){
    	timetableJob.setSchoolId(mySchoolId());
        return timetableJobService.findTimetableJobListByCondition(timetableJob);
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
    
    @GetMapping("/findTeacherInfoByGradeId")
    public ResponseJson findTeacherInfoByGradeId(@RequestParam("gradeId") String gradeId,@RequestParam("jobId") String jobId){
    	
    	ResponseJson teacherInfos = timetableJobService.findTeacherInfoByGradeId(gradeId, mySchoolId(),jobId);
    	return teacherInfos;
    }
    
    @GetMapping("/startScheduling/{jobId}")
    public ResponseJson startScheduling(@PathVariable("jobId") String jobId) {
    	
    	return timetableJobService.startScheduling(jobId);
    }
    
    @GetMapping("/copyJob")
    public ResponseJson copyJob(@RequestParam("jobId") String jobId,@RequestParam("rename") String rename) {
    	
    	return timetableJobService.copyJob(jobId,rename);
    }
    
    @PostMapping("/findTeacherAndCourse")
    public ResponseJson findTeacherAndCourse(@RequestBody TimetableJob timetableJob) {
    	timetableJob.setSchoolId(mySchoolId());
    	return timetableJobService.findTeacherAndCourse(timetableJob);
    }
    
    
    
    
}
