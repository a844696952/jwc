package com.yice.edu.cn.osp.feignClient.xw.DutyteachersDutyarrment;

import com.yice.edu.cn.common.pojo.xw.dutyteachersDutyarrment.DutyteachersDutyarrment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="xw",contextId = "dutyteachersDutyarrmentFeign",path = "/dutyteachersDutyarrment")
public interface DutyteachersDutyarrmentFeign {
    @GetMapping("/findDutyteachersDutyarrmentById/{id}")
    DutyteachersDutyarrment findDutyteachersDutyarrmentById(@PathVariable("id") String id);
    @PostMapping("/saveDutyteachersDutyarrment")
    DutyteachersDutyarrment saveDutyteachersDutyarrment(DutyteachersDutyarrment dutyteachersDutyarrment);
    @PostMapping("/findDutyteachersDutyarrmentListByCondition")
    List<DutyteachersDutyarrment> findDutyteachersDutyarrmentListByCondition(DutyteachersDutyarrment dutyteachersDutyarrment);
    @PostMapping("/findOneDutyteachersDutyarrmentByCondition")
    DutyteachersDutyarrment findOneDutyteachersDutyarrmentByCondition(DutyteachersDutyarrment dutyteachersDutyarrment);
    @PostMapping("/findDutyteachersDutyarrmentCountByCondition")
    long findDutyteachersDutyarrmentCountByCondition(DutyteachersDutyarrment dutyteachersDutyarrment);
    @PostMapping("/updateDutyteachersDutyarrment")
    void updateDutyteachersDutyarrment(DutyteachersDutyarrment dutyteachersDutyarrment);
    @GetMapping("/deleteDutyteachersDutyarrment/{id}")
    void deleteDutyteachersDutyarrment(@PathVariable("id") String id);
    @PostMapping("/deleteDutyteachersDutyarrmentByCondition")
    void deleteDutyteachersDutyarrmentByCondition(DutyteachersDutyarrment dutyteachersDutyarrment);
}
