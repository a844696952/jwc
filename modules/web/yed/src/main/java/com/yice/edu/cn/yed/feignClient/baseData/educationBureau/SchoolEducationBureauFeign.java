package com.yice.edu.cn.yed.feignClient.baseData.educationBureau;

import com.yice.edu.cn.common.pojo.jw.educationBureau.SchoolEducationBureau;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="jw",contextId = "schoolEducationBureauFeign",path = "/schoolEducationBureau")
public interface SchoolEducationBureauFeign {
    @GetMapping("/findSchoolEducationBureauById/{id}")
    SchoolEducationBureau findSchoolEducationBureauById(@PathVariable("id") String id);
    @PostMapping("/saveSchoolEducationBureau")
    SchoolEducationBureau saveSchoolEducationBureau(SchoolEducationBureau schoolEducationBureau);
    @PostMapping("/findSchoolEducationBureauListByCondition")
    List<SchoolEducationBureau> findSchoolEducationBureauListByCondition(SchoolEducationBureau schoolEducationBureau);
    @PostMapping("/findOneSchoolEducationBureauByCondition")
    SchoolEducationBureau findOneSchoolEducationBureauByCondition(SchoolEducationBureau schoolEducationBureau);
    @PostMapping("/findSchoolEducationBureauCountByCondition")
    long findSchoolEducationBureauCountByCondition(SchoolEducationBureau schoolEducationBureau);
    @PostMapping("/updateSchoolEducationBureau")
    void updateSchoolEducationBureau(SchoolEducationBureau schoolEducationBureau);
    @GetMapping("/deleteSchoolEducationBureau/{id}")
    void deleteSchoolEducationBureau(@PathVariable("id") String id);
    @PostMapping("/deleteSchoolEducationBureauByCondition")
    void deleteSchoolEducationBureauByCondition(SchoolEducationBureau schoolEducationBureau);
    @PostMapping("/resetSchoolEducationBureaus")
    void resetSchoolEducationBureaus(SchoolEducationBureau schoolEducationBureau);
}
