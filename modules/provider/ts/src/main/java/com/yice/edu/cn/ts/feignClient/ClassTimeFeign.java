package com.yice.edu.cn.ts.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;


@FeignClient(value="xw",contextId = "classTimeFeign2",path = "/classTime")
public interface ClassTimeFeign {

	@PostMapping("/sendToSchoolTeacherStuNowStatus")
    void sendToSchoolTeacherStuNowStatus(String taskId);
    @PostMapping("/statisStudentNowStatusAfterSchool")
    void statisStudentNowStatusAfterSchool(String taskId);

}
