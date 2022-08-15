package com.yice.edu.cn.ewb.feignClient.classSchedule;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.classSchedule.ClassSchedule;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value = "jw", path = "/classSchedule")
public interface ClassScheduleFeign {


    @PostMapping("/findList")
    List<List<ClassSchedule>> findList(ClassSchedule classSchedule);

    @PostMapping("/todayAndTomorrowClassSchede")
    ResponseJson todayAndTomorrowClassSchede(ClassSchedule classSchedule);

}
