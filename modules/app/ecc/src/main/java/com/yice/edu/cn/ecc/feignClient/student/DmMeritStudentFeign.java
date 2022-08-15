package com.yice.edu.cn.ecc.feignClient.student;

import com.yice.edu.cn.common.pojo.dm.student.AllStudent;
import com.yice.edu.cn.common.pojo.dm.student.DmMeritStudent;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="dm",contextId = "dmMeritStudentFeign",path = "/dmMeritStudent")
public interface DmMeritStudentFeign {
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @GetMapping("/findDmMeritStudentById/{id}")
    DmMeritStudent findDmMeritStudentById(@PathVariable("id") String id);
    @PostMapping("/saveDmMeritStudentOrUpdate")
    void saveDmMeritStudentOrUpdate(List<DmMeritStudent> dmMeritStudent);
    @PostMapping("/batchSaveDmMeritStudent")
    void batchSaveDmMeritStudent(List<DmMeritStudent> dmMeritStudents);
    @PostMapping("/findDmMeritStudentListByCondition")
    List<DmMeritStudent> findDmMeritStudentListByCondition(DmMeritStudent dmMeritStudent);
    @PostMapping("/findOneDmMeritStudentByCondition")
    DmMeritStudent findOneDmMeritStudentByCondition(DmMeritStudent dmMeritStudent);
    @PostMapping("/findDmMeritStudentCountByCondition")
    long findDmMeritStudentCountByCondition(DmMeritStudent dmMeritStudent);
    @PostMapping("/updateDmMeritStudent")
    void updateDmMeritStudent(DmMeritStudent dmMeritStudent);
    @PostMapping("/updateDmMeritStudentForAll")
    void updateDmMeritStudentForAll(DmMeritStudent dmMeritStudent);
    @GetMapping("/deleteDmMeritStudent/{id}")
    void deleteDmMeritStudent(@PathVariable("id") String id);
    @PostMapping("/deleteDmMeritStudentByCondition")
    void deleteDmMeritStudentByCondition(DmMeritStudent dmMeritStudent);
    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/

    @GetMapping("/findAllJwClassesAndStudents/{schoolId}")
    List<AllStudent> findAllJwClassesAndStudents(@PathVariable("schoolId") String schoolId);

    @GetMapping("/findAll/{schoolId}")
    List<DmMeritStudent> findAll(@PathVariable("schoolId") String schoolId);
}
