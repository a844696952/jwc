package com.yice.edu.cn.osp.feignClient.xw.stuInAndOut;

import com.yice.edu.cn.common.pojo.xw.stuInAndOut.StuNotComeToSchool;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="xw",contextId = "StuNotComeToSchoolFeign",path = "/stuNotComeToSchool")
public interface StuNotComeToSchoolFeign {
    @GetMapping("/findStuNotComeToSchoolById/{id}")
    StuNotComeToSchool findStuNotComeToSchoolById(@PathVariable("id") String id);
    @PostMapping("/saveStuNotComeToSchool")
    StuNotComeToSchool saveStuNotComeToSchool(StuNotComeToSchool stuNotComeToSchool);
    @PostMapping("/findStuNotComeToSchoolListByCondition")
    List<StuNotComeToSchool> findStuNotComeToSchoolListByCondition(StuNotComeToSchool stuNotComeToSchool);
    @PostMapping("/findOneStuNotComeToSchoolByCondition")
    StuNotComeToSchool findOneStuNotComeToSchoolByCondition(StuNotComeToSchool stuNotComeToSchool);
    @PostMapping("/findStuNotComeToSchoolCountByCondition")
    long findStuNotComeToSchoolCountByCondition(StuNotComeToSchool stuNotComeToSchool);
    @PostMapping("/updateStuNotComeToSchool")
    void updateStuNotComeToSchool(StuNotComeToSchool stuNotComeToSchool);
    @GetMapping("/deleteStuNotComeToSchool/{id}")
    void deleteStuNotComeToSchool(@PathVariable("id") String id);
    @PostMapping("/deleteStuNotComeToSchoolByCondition")
    void deleteStuNotComeToSchoolByCondition(StuNotComeToSchool stuNotComeToSchool);
    @GetMapping("/makeNotComeData/{schoolId}")
    void makeNotComeData(@PathVariable("schoolId")String schoolId);
}
