package com.yice.edu.cn.bmp.feignClient.xwDormManage.houseApplican;

import com.yice.edu.cn.common.pojo.xw.dormManage.dorm.HouseApplicanTeachers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="xw",contextId = "HouseApplicanTeachersFeign",path = "/houseApplicanTeachers")
public interface HouseApplicanTeachersFeign {
    @GetMapping("/findHouseApplicanTeachersById/{id}")
    HouseApplicanTeachers findHouseApplicanTeachersById(@PathVariable("id") String id);
    @PostMapping("/saveHouseApplicanTeachers")
    HouseApplicanTeachers saveHouseApplicanTeachers(HouseApplicanTeachers houseApplicanTeachers);
    @PostMapping("/findHouseApplicanTeachersListByCondition")
    List<HouseApplicanTeachers> findHouseApplicanTeachersListByCondition(HouseApplicanTeachers houseApplicanTeachers);
    @PostMapping("/findOneHouseApplicanTeachersByCondition")
    HouseApplicanTeachers findOneHouseApplicanTeachersByCondition(HouseApplicanTeachers houseApplicanTeachers);
    @PostMapping("/findHouseApplicanTeachersCountByCondition")
    long findHouseApplicanTeachersCountByCondition(HouseApplicanTeachers houseApplicanTeachers);
    @PostMapping("/updateHouseApplicanTeachers")
    void updateHouseApplicanTeachers(HouseApplicanTeachers houseApplicanTeachers);
    @GetMapping("/deleteHouseApplicanTeachers/{id}")
    void deleteHouseApplicanTeachers(@PathVariable("id") String id);
    @PostMapping("/deleteHouseApplicanTeachersByCondition")
    void deleteHouseApplicanTeachersByCondition(HouseApplicanTeachers houseApplicanTeachers);
}
