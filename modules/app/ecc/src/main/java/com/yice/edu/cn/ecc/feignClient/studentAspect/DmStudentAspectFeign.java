package com.yice.edu.cn.ecc.feignClient.studentAspect;

import com.yice.edu.cn.common.pojo.dm.studentAspect.DmStudentAspect;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="dm", contextId = "dmStudentAspectFeign", path = "/dmStudentAspect")
public interface DmStudentAspectFeign {
    @GetMapping("/findDmStudentAspectById/{id}")
    DmStudentAspect findDmStudentAspectById(@PathVariable("id") String id);
    @PostMapping("/saveDmStudentAspect")
    DmStudentAspect saveDmStudentAspect(DmStudentAspect dmStudentAspect);
    @PostMapping("/batchSaveDmStudentAspect")
    void batchSaveDmStudentAspect(List<DmStudentAspect> dmStudentAspects);
    @PostMapping("/findDmStudentAspectListByCondition")
    List<DmStudentAspect> findDmStudentAspectListByCondition(DmStudentAspect dmStudentAspect);
    @PostMapping("/findOneDmStudentAspectByCondition")
    DmStudentAspect findOneDmStudentAspectByCondition(DmStudentAspect dmStudentAspect);
    @PostMapping("/findDmStudentAspectCountByCondition")
    long findDmStudentAspectCountByCondition(DmStudentAspect dmStudentAspect);
    @PostMapping("/updateDmStudentAspect")
    void updateDmStudentAspect(DmStudentAspect dmStudentAspect);
    @GetMapping("/deleteDmStudentAspect/{id}")
    void deleteDmStudentAspect(@PathVariable("id") String id);
    @PostMapping("/deleteDmStudentAspectByCondition")
    void deleteDmStudentAspectByCondition(DmStudentAspect dmStudentAspect);
}
