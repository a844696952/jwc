package com.yice.edu.cn.yed.feignClient.baseData.eehManagement;

import com.yice.edu.cn.common.pojo.jw.eehManagement.EehSchool;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="jw",contextId = "eehSchoolFeign",path = "/eehSchool")
public interface EehSchoolFeign {
    @GetMapping("/findEehSchoolById/{id}")
    EehSchool findEehSchoolById(@PathVariable("id") String id);
    @PostMapping("/saveEehSchool")
    EehSchool saveEehSchool(EehSchool eehSchool);
    @PostMapping("/findEehSchoolListByCondition")
    List<EehSchool> findEehSchoolListByCondition(EehSchool eehSchool);
    @PostMapping("/findOneEehSchoolByCondition")
    EehSchool findOneEehSchoolByCondition(EehSchool eehSchool);
    @PostMapping("/findEehSchoolCountByCondition")
    long findEehSchoolCountByCondition(EehSchool eehSchool);
    @PostMapping("/updateEehSchool")
    void updateEehSchool(EehSchool eehSchool);
    @GetMapping("/deleteEehSchool/{id}")
    void deleteEehSchool(@PathVariable("id") String id);
    @PostMapping("/deleteEehSchoolByCondition")
    void deleteEehSchoolByCondition(EehSchool eehSchool);

    @PostMapping("/saveEehSchoolNew")
    EehSchool saveEehSchoolNew(EehSchool eehSchool);
    @GetMapping("/findCheckEehSchoolListById/{id}")
    List<EehSchool> findCheckEehSchoolListById(@PathVariable("id") String id);
}
