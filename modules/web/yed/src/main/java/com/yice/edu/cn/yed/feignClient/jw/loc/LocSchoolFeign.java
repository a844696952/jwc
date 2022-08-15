package com.yice.edu.cn.yed.feignClient.jw.loc;

import com.yice.edu.cn.common.pojo.loc.LocSchool;
import com.yice.edu.cn.common.pojo.loc.LocSchoolExt;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="jw",contextId = "locSchoolFeign",path = "/locSchool")
public interface LocSchoolFeign {
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @GetMapping("/findLocSchoolById/{id}")
    LocSchool findLocSchoolById(@PathVariable("id") String id);
    @PostMapping("/saveLocSchool")
    LocSchool saveLocSchool(LocSchool locSchool);
    @PostMapping("/batchSaveLocSchool")
    void batchSaveLocSchool(List<LocSchool> locSchools);
    @PostMapping("/findLocSchoolListByCondition")
    List<LocSchool> findLocSchoolListByCondition(LocSchool locSchool);
    @PostMapping("/findOneLocSchoolByCondition")
    LocSchool findOneLocSchoolByCondition(LocSchool locSchool);
    @PostMapping("/findLocSchoolCountByCondition")
    long findLocSchoolCountByCondition(LocSchool locSchool);
    @PostMapping("/updateLocSchool")
    void updateLocSchool(LocSchool locSchool);
    @PostMapping("/updateLocSchoolForAll")
    void updateLocSchoolForAll(LocSchool locSchool);
    @GetMapping("/deleteLocSchool/{id}")
    void deleteLocSchool(@PathVariable("id") String id);
    @PostMapping("/deleteLocSchoolByCondition")
    void deleteLocSchoolByCondition(LocSchool locSchool);
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/

    @PostMapping("/saveLocSchoolAndSaveLocSchoolYear")
    void saveLocSchoolAndSaveLocSchoolYear(LocSchoolExt locSchoolExt);
    @GetMapping("/deleteLocSchoolAndLocSchoolYear/{id}")
    void deleteLocSchoolAndLocSchoolYear(@PathVariable("id")String id);
    @PostMapping("/findIpRepetitionCount")
    long findIpRepetitionCount(LocSchool locSchool);
}
