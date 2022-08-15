package com.yice.edu.cn.osp.feignClient.dm.dmStudentAttendant;

import com.yice.edu.cn.common.pojo.dm.classCard.DmDeleteData;
import com.yice.edu.cn.common.pojo.dm.dmStudentAttendant.DmStudentAttendant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="dm",contextId = "dmStudentAttendantFeign",path = "/dmStudentAttendant")
public interface DmStudentAttendantFeign {
    @GetMapping("/findDmStudentAttendantById/{id}")
    DmStudentAttendant findDmStudentAttendantById(@PathVariable("id") String id);
    @PostMapping("/saveDmStudentAttendant")
    DmStudentAttendant saveDmStudentAttendant(DmStudentAttendant dmStudentAttendant);
    @PostMapping("/findDmStudentAttendantListByCondition")
    List<DmStudentAttendant> findDmStudentAttendantListByCondition(DmStudentAttendant dmStudentAttendant);
    @PostMapping("/findOneDmStudentAttendantByCondition")
    DmStudentAttendant findOneDmStudentAttendantByCondition(DmStudentAttendant dmStudentAttendant);
    @PostMapping("/findDmStudentAttendantCountByCondition")
    long findDmStudentAttendantCountByCondition(DmStudentAttendant dmStudentAttendant);
    @PostMapping("/updateDmStudentAttendant")
    void updateDmStudentAttendant(DmStudentAttendant dmStudentAttendant);
    @PostMapping("/updateDmStudentAttendantForNotNull")
    void updateDmStudentAttendantForNotNull(DmStudentAttendant dmStudentAttendant);
    @GetMapping("/deleteDmStudentAttendant/{id}")
    void deleteDmStudentAttendant(@PathVariable("id") String id);
    @PostMapping("/deleteDmStudentAttendantByCondition")
    void deleteDmStudentAttendantByCondition(DmStudentAttendant dmStudentAttendant);

    @PostMapping("/deleteDmStudentAttendantByClassIds")
    void deleteDmStudentAttendantByClassIds(DmDeleteData dmDeleteData);
}
