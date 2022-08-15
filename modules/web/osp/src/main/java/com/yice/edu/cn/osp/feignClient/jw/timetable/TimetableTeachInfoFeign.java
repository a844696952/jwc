package com.yice.edu.cn.osp.feignClient.jw.timetable;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.timetable.ArrangeClass;
import com.yice.edu.cn.common.pojo.jw.timetable.ArrangeCourse;
import com.yice.edu.cn.common.pojo.jw.timetable.ArrangeTeacher;
import com.yice.edu.cn.common.pojo.jw.timetable.TeachInfoBo;
import com.yice.edu.cn.common.pojo.jw.timetable.TimetableTeachInfo;

import java.util.List;

@FeignClient(value = "jw",contextId = "timetableTeachInfoFeign",path = "/timetableTeachInfo")
public interface TimetableTeachInfoFeign {

	@GetMapping("/findTimetableTeachInfoById/{id}")
	public TimetableTeachInfo findTimetableTeachInfoById(@PathVariable("id") String id);

	@PostMapping("/saveTimetableTeachInfo")
	public ResponseJson saveTimetableTeachInfo(@RequestBody TeachInfoBo teachInfos);

	@PostMapping("/findTimetableTeachInfoListByCondition")
	public List<TimetableTeachInfo> findTimetableTeachInfoListByCondition(
			@RequestBody TimetableTeachInfo timetableTeachInfo);

	@PostMapping("/findTimetableTeachInfoCountByCondition")
	public long findTimetableTeachInfoCountByCondition(@RequestBody TimetableTeachInfo timetableTeachInfo);

	@PostMapping("/updateTimetableTeachInfo")
	public void updateTimetableTeachInfo(@RequestBody TimetableTeachInfo timetableTeachInfo);

	@GetMapping("/deleteTimetableTeachInfo/{id}")
	public void deleteTimetableTeachInfo(@PathVariable("id") String id);

	@PostMapping("/deleteTimetableTeachInfoByCondition")
	public void deleteTimetableTeachInfoByCondition(@RequestBody TimetableTeachInfo timetableTeachInfo);

	@PostMapping("/findOneTimetableTeachInfoByCondition")
	public TimetableTeachInfo findOneTimetableTeachInfoByCondition(@RequestBody TimetableTeachInfo timetableTeachInfo);
	
	@GetMapping("/findTeachInfoCourseByJobId/{jobId}")
    public List<ArrangeCourse> findTeachInfoCourseByJobId(@PathVariable("jobId") String jobId);
    
    @GetMapping("/findTeacherByJobIdAndCourseId")
    public List<ArrangeTeacher> findTeacherByJobIdAndCourseId(@RequestParam("jobId") String jobId ,@RequestParam("courseId") String courseId);
    
    @GetMapping("/findArrangeClassByJobId")
	public List<ArrangeClass> findArrangeClassByJobId(@RequestParam("jobId") String jobId);
}
