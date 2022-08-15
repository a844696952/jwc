package com.yice.edu.cn.osp.feignClient.jw.timetable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.timetable.TimetableJob;




@FeignClient(value="jw",contextId = "timetableJobFeign",path = "/timetableJob")
public interface TimetableJobFeign {
	
	@GetMapping("/findTimetableJobById/{id}")
    public TimetableJob findTimetableJobById(@PathVariable("id") String id);

    @PostMapping("/saveTimetableJob")
    public TimetableJob saveTimetableJob(@RequestBody TimetableJob timetableJob);

    @PostMapping("/findTimetableJobListByCondition")
    public ResponseJson findTimetableJobListByCondition(@RequestBody TimetableJob timetableJob);
    
    @PostMapping("/findTimetableJobCountByCondition")
    public long findTimetableJobCountByCondition(@RequestBody TimetableJob timetableJob);

    @PostMapping("/updateTimetableJob")
    public void updateTimetableJob(@RequestBody TimetableJob timetableJob);

    @GetMapping("/deleteTimetableJob/{id}")
    public void deleteTimetableJob(@PathVariable("id") String id);
    
    @GetMapping("/findTeacherInfoByGradeId")
    public ResponseJson findTeacherInfoByGradeId(@RequestParam("gradeId") String gradeId,@RequestParam("schoolId") String schoolId,@RequestParam("jobId") String jobId);
    
    @GetMapping("/startScheduling/{jobId}")
    public ResponseJson startScheduling(@PathVariable("jobId") String jobId);
    
    @PostMapping("/findTeacherAndCourse")
    public ResponseJson findTeacherAndCourse(@RequestBody TimetableJob timetableJob) ;
    
    @GetMapping("/copyJob")
    public ResponseJson copyJob(@RequestParam("jobId") String jobId,@RequestParam("rename") String rename);
}
