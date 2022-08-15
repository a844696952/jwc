package com.yice.edu.cn.osp.feignClient.xw.dormManage.houseApplican;

import com.yice.edu.cn.common.pojo.xw.dormManage.dorm.HouseApplicanStudents;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="xw",contextId = "HouseApplicanStudentsFeign",path = "/houseApplicanStudents")
public interface HouseApplicanStudentsFeign {
    @GetMapping("/findHouseApplicanStudentsById/{id}")
    HouseApplicanStudents findHouseApplicanStudentsById(@PathVariable("id") String id);
    @PostMapping("/saveHouseApplicanStudents")
    HouseApplicanStudents saveHouseApplicanStudents(HouseApplicanStudents houseApplicanStudents);
    @PostMapping("/findHouseApplicanStudentsListByCondition")
    List<HouseApplicanStudents> findHouseApplicanStudentsListByCondition(HouseApplicanStudents houseApplicanStudents);
    @PostMapping("/findOneHouseApplicanStudentsByCondition")
    HouseApplicanStudents findOneHouseApplicanStudentsByCondition(HouseApplicanStudents houseApplicanStudents);
    @PostMapping("/findHouseApplicanStudentsCountByCondition")
    long findHouseApplicanStudentsCountByCondition(HouseApplicanStudents houseApplicanStudents);
    @PostMapping("/updateHouseApplicanStudents")
    void updateHouseApplicanStudents(HouseApplicanStudents houseApplicanStudents);
    @GetMapping("/deleteHouseApplicanStudents/{id}")
    void deleteHouseApplicanStudents(@PathVariable("id") String id);
    @PostMapping("/deleteHouseApplicanStudentsByCondition")
    void deleteHouseApplicanStudentsByCondition(HouseApplicanStudents houseApplicanStudents);


    @PostMapping("/findHouseApplicanStudents")
    List<HouseApplicanStudents> findHouseApplicanStudents(HouseApplicanStudents houseApplicanStudents);
    @PostMapping("/findHouseApplicanStudentsCount")
    long findHouseApplicanStudentsCount(HouseApplicanStudents houseApplicanStudents);

    @GetMapping("/lookHouseApplicanStudentsByhouseApplicanId/{houseApplicanId}")
    HouseApplicanStudents lookHouseApplicanStudentsByhouseApplicanId(@PathVariable("houseApplicanId")String houseApplicanId);

    @PostMapping("/saveHouseApplicanStudents1")
    HouseApplicanStudents saveHouseApplicanStudents1(HouseApplicanStudents houseApplicanStudents);
    @PostMapping("/lookHouseApplicanStudents")
    List<HouseApplicanStudents> lookHouseApplicanStudents(HouseApplicanStudents houseApplicanStudents);
    @GetMapping("/lookHouseApplicanStudentsCount/{houseApplicanId}")
    long lookHouseApplicanStudentsCount(@PathVariable("houseApplicanId")String houseApplicanId);
}
