package com.yice.edu.cn.osp.feignClient.jw.electiveCourse;

import com.yice.edu.cn.common.pojo.jw.electiveCourse.ElectiveClasses;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="jw",contextId = "electiveClassesFeign",path = "/electiveClasses")
public interface ElectiveClassesFeign {
    @GetMapping("/findElectiveClassesById/{id}")
    ElectiveClasses findElectiveClassesById(@PathVariable("id") String id);
    @PostMapping("/saveElectiveClasses")
    ElectiveClasses saveElectiveClasses(ElectiveClasses electiveClasses);
    @PostMapping("/findElectiveClassesListByCondition")
    List<ElectiveClasses> findElectiveClassesListByCondition(ElectiveClasses electiveClasses);
    @PostMapping("/findOneElectiveClassesByCondition")
    ElectiveClasses findOneElectiveClassesByCondition(ElectiveClasses electiveClasses);
    @PostMapping("/findElectiveClassesCountByCondition")
    long findElectiveClassesCountByCondition(ElectiveClasses electiveClasses);
    @PostMapping("/updateElectiveClasses")
    void updateElectiveClasses(ElectiveClasses electiveClasses);
    @GetMapping("/deleteElectiveClasses/{id}")
    void deleteElectiveClasses(@PathVariable("id") String id);
    @PostMapping("/deleteElectiveClassesByCondition")
    void deleteElectiveClassesByCondition(ElectiveClasses electiveClasses);
}
