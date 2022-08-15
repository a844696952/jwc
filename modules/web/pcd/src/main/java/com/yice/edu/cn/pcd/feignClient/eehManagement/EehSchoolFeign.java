package com.yice.edu.cn.pcd.feignClient.eehManagement;

import com.yice.edu.cn.common.pojo.jw.eehManagement.EehSchool;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="jw",contextId = "eehSchool",path = "/eehSchool")
public interface EehSchoolFeign {
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @GetMapping("/findEehSchoolById/{id}")
    EehSchool findEehSchoolById(@PathVariable("id") String id);
    @PostMapping("/saveEehSchool")
    EehSchool saveEehSchool(EehSchool eehSchool);
    @PostMapping("/batchSaveEehSchool")
    void batchSaveEehSchool(List<EehSchool> eehSchools);
    @PostMapping("/findEehSchoolListByCondition")
    List<EehSchool> findEehSchoolListByCondition(EehSchool eehSchool);
    @PostMapping("/findOneEehSchoolByCondition")
    EehSchool findOneEehSchoolByCondition(EehSchool eehSchool);
    @PostMapping("/findEehSchoolCountByCondition")
    long findEehSchoolCountByCondition(EehSchool eehSchool);
    @PostMapping("/updateEehSchool")
    void updateEehSchool(EehSchool eehSchool);
    @PostMapping("/updateEehSchoolForAll")
    void updateEehSchoolForAll(EehSchool eehSchool);
    @GetMapping("/deleteEehSchool/{id}")
    void deleteEehSchool(@PathVariable("id") String id);
    @PostMapping("/deleteEehSchoolByCondition")
    void deleteEehSchoolByCondition(EehSchool eehSchool);
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
    @PostMapping("/findEehSchoolListByEehIds")
    List<EehSchool> findEehSchoolListByEehIds(@RequestBody List<String> eehids);
}
