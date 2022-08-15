package com.yice.edu.cn.dm.feignClient.jw.classSchedule;

import com.yice.edu.cn.common.pojo.jw.classSchedule.ClassScheduleInit;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="jw",contextId = "classScheduleInitFeign",path = "/classScheduleInit")
public interface ClassScheduleInitFeign {
    @PostMapping("/findListClassScheduleInitBySchool")
    List<ClassScheduleInit> findListClassScheduleInitBySchool(ClassScheduleInit classScheduleInit);
}
