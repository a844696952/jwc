package com.yice.edu.cn.yed.feignClient.baseData.educationBureau;

import com.yice.edu.cn.common.pojo.jw.educationBureau.EducationBureau;
import com.yice.edu.cn.common.pojo.jw.school.School;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="jw",contextId = "educationBureauFeign",path = "/educationBureau")
public interface EducationBureauFeign {
    @GetMapping("/findEducationBureauById/{id}")
    EducationBureau findEducationBureauById(@PathVariable("id") String id);
    @PostMapping("/saveEducationBureau")
    EducationBureau saveEducationBureau(EducationBureau educationBureau);
    @PostMapping("/findEducationBureauListByCondition")
    List<EducationBureau> findEducationBureauListByCondition(EducationBureau educationBureau);
    @PostMapping("/findOneEducationBureauByCondition")
    EducationBureau findOneEducationBureauByCondition(EducationBureau educationBureau);
    @PostMapping("/findEducationBureauCountByCondition")
    long findEducationBureauCountByCondition(EducationBureau educationBureau);
    @PostMapping("/updateEducationBureau")
    void updateEducationBureau(EducationBureau educationBureau);
    @GetMapping("/deleteEducationBureau/{id}")
    void deleteEducationBureau(@PathVariable("id") String id);
    @PostMapping("/deleteEducationBureauByCondition")
    void deleteEducationBureauByCondition(EducationBureau educationBureau);
    @GetMapping("/findUnSelectedSchoolsById/{educationBureauId}")
    List<School> findUnSelectedSchoolsById(@PathVariable("educationBureauId") String educationBureauId);
    @GetMapping("/findSelectedSchoolsById/{educationBureauId}")
    List<String> findSelectedSchoolsById(@PathVariable("educationBureauId")String educationBureauId);
}
