package com.yice.edu.cn.osp.feignClient.xw.dutyLocation;

import com.yice.edu.cn.common.pojo.xw.dutyLocation.DutyLocation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="xw",contextId = "dutyLocationFeign",path = "/dutyLocation")
public interface DutyLocationFeign {
    @GetMapping("/findDutyLocationById/{id}")
    DutyLocation findDutyLocationById(@PathVariable("id") String id);
    @PostMapping("/saveDutyLocation")
    DutyLocation saveDutyLocation(DutyLocation dutyLocation);
    @PostMapping("/findDutyLocationListByCondition")
    List<DutyLocation> findDutyLocationListByCondition(DutyLocation dutyLocation);
    @PostMapping("/findOneDutyLocationByCondition")
    DutyLocation findOneDutyLocationByCondition(DutyLocation dutyLocation);
    @PostMapping("/findDutyLocationCountByCondition")
    long findDutyLocationCountByCondition(DutyLocation dutyLocation);
    @PostMapping("/updateDutyLocation")
    void updateDutyLocation(DutyLocation dutyLocation);
    @GetMapping("/deleteDutyLocation/{id}")
    void deleteDutyLocation(@PathVariable("id") String id);
    @PostMapping("/deleteDutyLocationByCondition")
    DutyLocation deleteDutyLocationByCondition(DutyLocation dutyLocation);
}
