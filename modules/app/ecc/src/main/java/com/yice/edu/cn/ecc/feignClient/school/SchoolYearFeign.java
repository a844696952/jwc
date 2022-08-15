package com.yice.edu.cn.ecc.feignClient.school;

import com.yice.edu.cn.common.pojo.jw.schoolYear.CurSchoolYear;
import com.yice.edu.cn.common.pojo.jw.schoolYear.SchoolYear;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(value="jw",contextId = "SchoolYearFeign",path = "/schoolYear")
public interface SchoolYearFeign {
    @GetMapping("/findCurSchoolYear/{schoolId}")
    CurSchoolYear findCurSchoolYear(@PathVariable("schoolId") String schoolId);
    @GetMapping("/findSchoolYears/{schoolId}")
    List<SchoolYear> findSchoolYears(@PathVariable("schoolId") String schoolId);

    @GetMapping("/findMaxSchoolYear/{schoolId}")
    String[] findMaxSchoolYear(@PathVariable("schoolId")String schoolId);
}
