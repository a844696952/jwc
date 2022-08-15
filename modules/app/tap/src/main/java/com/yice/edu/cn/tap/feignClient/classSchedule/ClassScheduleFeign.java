package com.yice.edu.cn.tap.feignClient.classSchedule;

import com.yice.edu.cn.common.pojo.general.dd.Dd;
import com.yice.edu.cn.common.pojo.jw.classSchedule.ClassSchedule;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "jw", path = "/classSchedule")
public interface ClassScheduleFeign {
    @GetMapping("/findClassScheduleById/{id}")
    ClassSchedule findClassScheduleById(@PathVariable("id") String id);

    @PostMapping("/saveClassSchedule")
    ClassSchedule saveClassSchedule(ClassSchedule classSchedule);

    @PostMapping("/findClassScheduleListByCondition")
    List<ClassSchedule> findClassScheduleListByCondition(ClassSchedule classSchedule);

    @PostMapping("/findOneClassScheduleByCondition")
    ClassSchedule findOneClassScheduleByCondition(ClassSchedule classSchedule);

    @PostMapping("/findClassScheduleCountByCondition")
    long findClassScheduleCountByCondition(ClassSchedule classSchedule);

    @PostMapping("/updateClassSchedule")
    void updateClassSchedule(ClassSchedule classSchedule);

    @GetMapping("/deleteClassSchedule/{id}")
    void deleteClassSchedule(@PathVariable("id") String id);

    @PostMapping("/deleteClassScheduleByCondition")
    void deleteClassScheduleByCondition(ClassSchedule classSchedule);

    @GetMapping("/findGradesBySchoolId/{id}")
    List<Dd> findGradesBySchoolId(@PathVariable("id") String id);

    @PostMapping("/getTeacherNameAndCourseAndCount")
    List<ClassSchedule> getTeacherNameAndCourseAndCount(ClassSchedule classSchedule);

    @PostMapping("/updateorfind")
    void updateorfind(@RequestBody List<ClassSchedule> classSchedule);

    @PostMapping("/deletebatchdelete")
    void deletebatchdelete(ClassSchedule classSchedule);

    @PostMapping("/findList")
    List<List<ClassSchedule>> findList(@RequestBody ClassSchedule classSchedule);

    @PostMapping("/findClassScheduleListByConditions")
    List<ClassSchedule> findClassScheduleListByConditions(ClassSchedule classSchedule);

    @PostMapping("/todayClassScheduleList")
    List<ClassSchedule> todayClassScheduleList(ClassSchedule classSchedule);

}
