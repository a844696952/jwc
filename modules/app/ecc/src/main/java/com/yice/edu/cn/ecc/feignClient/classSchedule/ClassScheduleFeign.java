package com.yice.edu.cn.ecc.feignClient.classSchedule;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.classSchedule.ClassSchedule;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value = "jw", contextId = "classScheduleFeign",path = "/classSchedule")
public interface ClassScheduleFeign {


    @PostMapping("/findList")
    List<List<ClassSchedule>> findList(ClassSchedule classSchedule);

    @PostMapping("/todayAndTomorrowClassSchede")
    ResponseJson todayAndTomorrowClassSchede(ClassSchedule classSchedule);

    @PostMapping("/todayClassScheduleList")
    List<ClassSchedule> todayClassScheduleList(ClassSchedule classSchedule);

    @GetMapping("/findTodayClassScheduleListByUserName/{userName}")
    List<ClassSchedule> findTodayClassScheduleListByUserName(@PathVariable("userName") String userName);
}
