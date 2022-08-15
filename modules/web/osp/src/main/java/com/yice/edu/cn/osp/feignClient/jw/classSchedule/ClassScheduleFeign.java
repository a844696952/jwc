package com.yice.edu.cn.osp.feignClient.jw.classSchedule;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.classSchedule.ClassSchedule;
import com.yice.edu.cn.common.pojo.general.dd.Dd;
import com.yice.edu.cn.common.pojo.jw.classSchedule.ImportClassSchedule;
import com.yice.edu.cn.common.pojo.jw.teacher.TeacherClasses;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

@FeignClient(value = "jw",contextId = "classScheduleFeign",path = "/classSchedule")
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

    @GetMapping("/findGradesBySchoolId/{id}/{scheduleId}")
    List<Dd> findGradesBySchoolId(@PathVariable("id") String id,@PathVariable("scheduleId")String scheduleId);

    @PostMapping("/getTeacherNameAndCourseAndCount")
    List<ClassSchedule> getTeacherNameAndCourseAndCount(ClassSchedule classSchedule);

    @PostMapping("/updateorfind")
    void updateorfind(@RequestBody ClassSchedule classSchedule);

    @PostMapping("/deletebatchdelete")
    void deletebatchdelete(ClassSchedule classSchedule);

    @PostMapping("/findList")
    List<List<ClassSchedule>> findList(ClassSchedule classSchedule);

    @PostMapping("/findClassScheduleListByConditions")
    List<ClassSchedule> findClassScheduleListByConditions(ClassSchedule classSchedule);

    @PostMapping("/todayClassScheduleList")
    List<ClassSchedule> todayClassScheduleList(ClassSchedule classSchedule);


    @PostMapping("/batchSaveClassSchedule")
    void batchSaveClassSchedule(List<ClassSchedule> classSchedules);

    @GetMapping("/verifyImport/{classesId}")
    List<ClassSchedule> verifyImport(@PathVariable("classesId") String classesId);

    @PostMapping("/conversionSchedule")
    Map<String,Object> conversionSchedule(ImportClassSchedule importClassSchedule);

    @PostMapping("/getTeacherThisWeekClassSchedule")
    List<List<ClassSchedule>> getTeacherThisWeekClassSchedule(ClassSchedule classSchedule);

    @PostMapping("/getTeacherList")
    ResponseJson getTeacherList(ClassSchedule classSchedule);

    @PostMapping("/findCourseTeacherByClass")
    List<Map<String,String>> findCourseTeacherByClass(TeacherClasses teacherClasses);

    @PostMapping("/findClassScheduleGroupClassId")
    List<ClassSchedule> findClassScheduleGroupClassId(ClassSchedule classSchedule);

    @PostMapping("/conversionScheduleNew")
    Map<String,Object> conversionScheduleNew(ImportClassSchedule importClassSchedule);
}
