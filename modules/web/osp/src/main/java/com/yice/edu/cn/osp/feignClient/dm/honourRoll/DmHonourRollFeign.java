package com.yice.edu.cn.osp.feignClient.dm.honourRoll;

import com.yice.edu.cn.common.pojo.dm.classCard.DmDeleteData;
import com.yice.edu.cn.common.pojo.dm.honourRoll.DmHonourRoll;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="dm",contextId = "dmHonourRollFeign",path = "/dmHonourRoll")
public interface DmHonourRollFeign {
    @GetMapping("/findDmHonourRollById/{id}")
    DmHonourRoll findDmHonourRollById(@PathVariable("id") String id);
    @PostMapping("/saveDmHonourRoll")
    DmHonourRoll saveDmHonourRoll(DmHonourRoll dmHonourRoll);
    @PostMapping("/findDmHonourRollListByCondition")
    List<DmHonourRoll> findDmHonourRollListByCondition(DmHonourRoll dmHonourRoll);
    @PostMapping("/findOneDmHonourRollByCondition")
    DmHonourRoll findOneDmHonourRollByCondition(DmHonourRoll dmHonourRoll);
    @PostMapping("/findDmHonourRollCountByCondition")
    long findDmHonourRollCountByCondition(DmHonourRoll dmHonourRoll);
    @PostMapping("/updateDmHonourRoll")
    void updateDmHonourRoll(DmHonourRoll dmHonourRoll);
    @GetMapping("/deleteDmHonourRoll/{id}")
    void deleteDmHonourRoll(@PathVariable("id") String id);
    @PostMapping("/deleteDmHonourRollByCondition")
    void deleteDmHonourRollByCondition(DmHonourRoll dmHonourRoll);
    @PostMapping("/findDmHonourRoll")
    long findDmHonourRoll(DmHonourRoll dmHonourRoll);
    @PostMapping("/deleteDmHonourRollByClassId")
    void deleteDmHonourRollByClassId(DmDeleteData dmDeleteData);
}
