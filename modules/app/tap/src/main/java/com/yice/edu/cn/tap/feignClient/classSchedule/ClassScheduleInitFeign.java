package com.yice.edu.cn.tap.feignClient.classSchedule;

import com.yice.edu.cn.common.pojo.jw.classSchedule.ClassScheduleInit;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="jw",contextId = "classScheduleInitFeign",path = "/classScheduleInit")
public interface ClassScheduleInitFeign {
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @GetMapping("/findClassScheduleInitById/{id}")
    ClassScheduleInit findClassScheduleInitById(@PathVariable("id") String id);
    @PostMapping("/saveClassScheduleInit")
    ClassScheduleInit saveClassScheduleInit(ClassScheduleInit classScheduleInit);
    @PostMapping("/findClassScheduleInitListByCondition")
    List<ClassScheduleInit> findClassScheduleInitListByCondition(ClassScheduleInit classScheduleInit);
    @PostMapping("/findOneClassScheduleInitByCondition")
    ClassScheduleInit findOneClassScheduleInitByCondition(ClassScheduleInit classScheduleInit);
    @PostMapping("/findClassScheduleInitCountByCondition")
    long findClassScheduleInitCountByCondition(ClassScheduleInit classScheduleInit);
    @PostMapping("/updateClassScheduleInit")
    void updateClassScheduleInit(ClassScheduleInit classScheduleInit);
    @PostMapping("/updateClassScheduleInitForNotNull")
    void updateClassScheduleInitForAll(ClassScheduleInit classScheduleInit);
    @GetMapping("/deleteClassScheduleInit/{id}")
    void deleteClassScheduleInit(@PathVariable("id") String id);
    @PostMapping("/deleteClassScheduleInitByCondition")
    void deleteClassScheduleInitByCondition(ClassScheduleInit classScheduleInit);
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
