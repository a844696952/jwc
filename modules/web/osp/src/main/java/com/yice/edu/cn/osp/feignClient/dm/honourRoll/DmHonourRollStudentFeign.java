package com.yice.edu.cn.osp.feignClient.dm.honourRoll;

import com.yice.edu.cn.common.pojo.dm.classCard.DmDeleteData;
import com.yice.edu.cn.common.pojo.dm.honourRoll.DmHonourRollStudent;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="dm",contextId = "dmHonourRollStudentFeign",path = "/dmHonourRollStudent")
public interface DmHonourRollStudentFeign {
    @GetMapping("/findDmHonourRollStudentById/{id}")
    DmHonourRollStudent findDmHonourRollStudentById(@PathVariable("id") String id);
    @PostMapping("/saveDmHonourRollStudent")
    DmHonourRollStudent saveDmHonourRollStudent(DmHonourRollStudent dmHonourRollStudent);
    @PostMapping("/findDmHonourRollStudentListByCondition")
    List<DmHonourRollStudent> findDmHonourRollStudentListByCondition(DmHonourRollStudent dmHonourRollStudent);
    @PostMapping("/findOneDmHonourRollStudentByCondition")
    DmHonourRollStudent findOneDmHonourRollStudentByCondition(DmHonourRollStudent dmHonourRollStudent);
    @PostMapping("/findDmHonourRollStudentCountByCondition")
    long findDmHonourRollStudentCountByCondition(DmHonourRollStudent dmHonourRollStudent);
    @PostMapping("/updateDmHonourRollStudent")
    void updateDmHonourRollStudent(DmHonourRollStudent dmHonourRollStudent);
    @GetMapping("/deleteDmHonourRollStudent/{id}")
    void deleteDmHonourRollStudent(@PathVariable("id") String id);
    @PostMapping("/deleteDmHonourRollStudentByCondition")
    void deleteDmHonourRollStudentByCondition(DmHonourRollStudent dmHonourRollStudent);
    @PostMapping("/findDmHonourRollStudentListByConditions")
    List<DmHonourRollStudent> findDmHonourRollStudentListByConditions(DmHonourRollStudent dmHonourRollStudent);
    @PostMapping("/findDmHonourRollStudentCountByConditions")
    long findDmHonourRollStudentCountByConditions(DmHonourRollStudent dmHonourRollStudent);
    @PostMapping("/deleteDmHonourRollStudentByClassId")
    void deleteDmHonourRollStudentByClassId(DmDeleteData dmDeleteData);
}
