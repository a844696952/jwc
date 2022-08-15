package com.yice.edu.cn.ts.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@FeignClient(value="xw",contextId = "StuInAndOutStartTimeFeign",path = "/stuInAndOutStartTime")
public interface StuInAndOutStartTimeFeign {

	@PostMapping("/sendToSchoolTeacherStuNowStatus")
    void sendToSchoolTeacherStuNowStatus(String taskId);

    @GetMapping("/stuNotArriveSchool")
    void stuNotArriveSchool();
}
