package com.yice.edu.cn.yed.feignClient.jw.thirdParty;

import com.yice.edu.cn.common.pojo.api.thirdParty.ApplySchool;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value = "jw", contextId = "applySchoolFeign", path = "/applySchool")
public interface ApplySchoolFeign {
    /*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @GetMapping("/findApplySchoolById/{id}")
    ApplySchool findApplySchoolById(@PathVariable("id") String id);

    @PostMapping("/saveApplySchool")
    List<ApplySchool> saveApplySchool(List<ApplySchool> applySchoolList);

    @PostMapping("/batchSaveApplySchool")
    void batchSaveApplySchool(List<ApplySchool> applySchools);

    @PostMapping("/findApplySchoolListByCondition")
    List<ApplySchool> findApplySchoolListByCondition(ApplySchool applySchool);

    @PostMapping("/findOneApplySchoolByCondition")
    ApplySchool findOneApplySchoolByCondition(ApplySchool applySchool);

    @PostMapping("/findApplySchoolCountByCondition")
    long findApplySchoolCountByCondition(ApplySchool applySchool);

    @PostMapping("/updateApplySchool")
    void updateApplySchool(ApplySchool applySchool);

    @PostMapping("/updateApplySchoolForAll")
    void updateApplySchoolForAll(ApplySchool applySchool);

    @GetMapping("/deleteApplySchool/{id}")
    void deleteApplySchool(@PathVariable("id") String id);

    @PostMapping("/deleteApplySchoolByCondition")
    void deleteApplySchoolByCondition(ApplySchool applySchool);


    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
