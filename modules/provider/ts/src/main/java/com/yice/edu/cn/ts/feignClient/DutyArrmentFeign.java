package com.yice.edu.cn.ts.feignClient;

import com.yice.edu.cn.common.pojo.xw.dutyArrment.DutyArrment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="xw",contextId = "dutyArrmentFeign",path = "/dutyArrment")
public interface DutyArrmentFeign {
    @GetMapping("/findDutyArrmentById/{id}")
    DutyArrment findDutyArrmentById(@PathVariable("id") String id);
    @PostMapping("/saveDutyArrment")
    DutyArrment saveDutyArrment(DutyArrment dutyArrment);
    @PostMapping("/findDutyArrmentListByCondition")
    List<DutyArrment> findDutyArrmentListByCondition(DutyArrment dutyArrment);
    @PostMapping("/findOneDutyArrmentByCondition")
    DutyArrment findOneDutyArrmentByCondition(DutyArrment dutyArrment);
    @PostMapping("/findDutyArrmentCountByCondition")
    long findDutyArrmentCountByCondition(DutyArrment dutyArrment);
    @PostMapping("/updateDutyArrment")
    void updateDutyArrment(DutyArrment dutyArrment);
    @GetMapping("/deleteDutyArrment/{id}")
    void deleteDutyArrment(@PathVariable("id") String id);
    @PostMapping("/deleteDutyArrmentByCondition")
    void deleteDutyArrmentByCondition(DutyArrment dutyArrment);
}
