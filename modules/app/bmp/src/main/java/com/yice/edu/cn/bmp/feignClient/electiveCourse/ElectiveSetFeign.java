package com.yice.edu.cn.bmp.feignClient.electiveCourse;

import com.yice.edu.cn.common.pojo.jw.electiveCourse.ElectiveSet;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="jw",contextId = "electiveSetFeign",path = "/electiveSet")
public interface ElectiveSetFeign {
    @GetMapping("/findElectiveSetById/{id}")
    ElectiveSet findElectiveSetById(@PathVariable("id") String id);
    @PostMapping("/saveElectiveSet")
    ElectiveSet saveElectiveSet(ElectiveSet electiveSet);
    @PostMapping("/findElectiveSetListByCondition")
    List<ElectiveSet> findElectiveSetListByCondition(ElectiveSet electiveSet);
    @PostMapping("/findOneElectiveSetByCondition")
    ElectiveSet findOneElectiveSetByCondition(ElectiveSet electiveSet);
    @PostMapping("/findElectiveSetCountByCondition")
    long findElectiveSetCountByCondition(ElectiveSet electiveSet);
    @PostMapping("/updateElectiveSet")
    void updateElectiveSet(ElectiveSet electiveSet);
    @GetMapping("/deleteElectiveSet/{id}")
    void deleteElectiveSet(@PathVariable("id") String id);
    @PostMapping("/deleteElectiveSetByCondition")
    void deleteElectiveSetByCondition(ElectiveSet electiveSet);
}
