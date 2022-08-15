package com.yice.edu.cn.mes.feign;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.school.School;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value = "jw", contextId = "schoolFeign", path = "/school")
public interface SchoolFeign {
    @PostMapping("/findSchoolListByCondition")
    List<School> findSchoolListByCondition(School school);

    @GetMapping("/findSchoolExpireOrSchoolYear/{schoolId}")
    ResponseJson findSchoolExpireOrSchoolYear(@PathVariable("schoolId") String schoolId);
}
