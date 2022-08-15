package com.yice.edu.cn.osp.feignClient.jw.timetable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.timetable.Timetable;
import com.yice.edu.cn.common.pojo.jw.timetable.TimetableAdjustCondition;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(value="jw",contextId = "timetableFeign",path = "/timetable")
public interface TimetableFeign {
    @GetMapping("/findTimetableById/{id}")
    Timetable findTimetableById(@PathVariable("id") String id);
    @PostMapping("/saveTimetable")
    Timetable saveTimetable(Timetable timetable);
    @PostMapping("/findTimetableListByCondition")
    List<Timetable> findTimetableListByCondition(Timetable timetable);
    @PostMapping("/findOneTimetableByCondition")
    Timetable findOneTimetableByCondition(Timetable timetable);
    @PostMapping("/findTimetableCountByCondition")
    long findTimetableCountByCondition(Timetable timetable);
    @PostMapping("/updateTimetable")
    void updateTimetable(Timetable timetable);
    @GetMapping("/deleteTimetable/{id}")
    void deleteTimetable(@PathVariable("id") String id);
    @PostMapping("/deleteTimetableByCondition")
    void deleteTimetableByCondition(Timetable timetable);
    @GetMapping("/findAllByJobId")
    public ResponseJson findAllTimetableByJobId(@RequestParam("jobId") String jobId,@RequestParam("type") Integer type);
    @GetMapping("/findTeacherTimetableByJobId")
	List<Timetable> findTeacherTimetableByJobId(@RequestParam("jobId") String jobId,@RequestParam("teacherId") String teacherId);
    @PostMapping("/adjustTimetable")
    ResponseJson adjustTimetable(@RequestBody TimetableAdjustCondition condition);
}
