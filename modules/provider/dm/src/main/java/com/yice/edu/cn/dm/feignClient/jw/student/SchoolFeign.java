package com.yice.edu.cn.dm.feignClient.jw.student;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.yice.edu.cn.common.pojo.jw.school.School;


@FeignClient(value="jw",contextId = "schoolFeign",path = "/school")
public interface SchoolFeign {

	@PostMapping("/findSchoolListByCondition")
    public List<School> findSchoolListByCondition(
            @RequestBody School school);
}
