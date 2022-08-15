package com.yice.edu.cn.osp.feignClient.jw.schoolYear;

import com.yice.edu.cn.common.pojo.jw.schoolYear.CurSchoolYear;
import com.yice.edu.cn.common.pojo.jw.schoolYear.SchoolYear;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="jw",contextId = "schoolYearFeign",path = "/schoolYear")
public interface SchoolYearFeign {
    @GetMapping("/findSchoolYears/{schoolId}")
    List<SchoolYear> findSchoolYears(@PathVariable("schoolId") String schoolId);
    @GetMapping("/findCurSchoolYear/{schoolId}")
    CurSchoolYear findCurSchoolYear(@PathVariable("schoolId") String schoolId);

    @GetMapping("/findSchoolYearById/{id}")
    SchoolYear findSchoolYearById(@PathVariable("id") String id);

    @GetMapping("/findCurrentSchoolRange/{schoolId}")
    String [] findCurrentSchoolRange(@PathVariable("schoolId")String schoolId);


}
