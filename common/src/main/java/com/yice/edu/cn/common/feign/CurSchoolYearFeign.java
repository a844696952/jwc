package com.yice.edu.cn.common.feign;

import com.yice.edu.cn.common.pojo.jw.schoolYear.CurSchoolYear;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "jw",contextId = "curSchoolYearFeign",path = "/schoolYear")
public interface CurSchoolYearFeign {
    @GetMapping("/findCurSchoolYear/{schoolId}")
    CurSchoolYear findCurSchoolYear(@PathVariable("schoolId") String schoolId);
}
