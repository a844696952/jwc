package com.yice.edu.cn.osp.feignClient.jw.classSchedule;

import com.yice.edu.cn.common.pojo.jw.classSchedule.ClassScheduleNoonBreak;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="jw",contextId = "classScheduleNoonBreakFeign",path = "/classScheduleNoonBreak")
public interface ClassScheduleNoonBreakFeign {
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @GetMapping("/findClassScheduleNoonBreakById/{id}")
    ClassScheduleNoonBreak findClassScheduleNoonBreakById(@PathVariable("id") String id);
    @PostMapping("/saveClassScheduleNoonBreak")
    ClassScheduleNoonBreak saveClassScheduleNoonBreak(ClassScheduleNoonBreak classScheduleNoonBreak);
    @PostMapping("/findClassScheduleNoonBreakListByCondition")
    List<ClassScheduleNoonBreak> findClassScheduleNoonBreakListByCondition(ClassScheduleNoonBreak classScheduleNoonBreak);
    @PostMapping("/findOneClassScheduleNoonBreakByCondition")
    ClassScheduleNoonBreak findOneClassScheduleNoonBreakByCondition(ClassScheduleNoonBreak classScheduleNoonBreak);
    @PostMapping("/findClassScheduleNoonBreakCountByCondition")
    long findClassScheduleNoonBreakCountByCondition(ClassScheduleNoonBreak classScheduleNoonBreak);
    @PostMapping("/updateClassScheduleNoonBreak")
    void updateClassScheduleNoonBreak(ClassScheduleNoonBreak classScheduleNoonBreak);
    @PostMapping("/updateClassScheduleNoonBreakForNotNull")
    void updateClassScheduleNoonBreakForAll(ClassScheduleNoonBreak classScheduleNoonBreak);
    @GetMapping("/deleteClassScheduleNoonBreak/{id}")
    void deleteClassScheduleNoonBreak(@PathVariable("id") String id);
    @PostMapping("/deleteClassScheduleNoonBreakByCondition")
    void deleteClassScheduleNoonBreakByCondition(ClassScheduleNoonBreak classScheduleNoonBreak);
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/

    @PostMapping("/updateClassScheduleNoobBreakNumber")
    void  updateClassScheduleNoobBreakNumber(ClassScheduleNoonBreak classScheduleNoonBreak);
}
