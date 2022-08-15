package com.yice.edu.cn.bmp.feignClient.dm.honourRoll;

import com.yice.edu.cn.common.pojo.dm.honourRoll.DmHonourRoll;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="dm",contextId = "dmHonourRollFeign",path = "/dmHonourRoll")
public interface DmHonourRollFeign {
    @GetMapping("/findDmHonourRollById/{id}")
    DmHonourRoll findDmHonourRollById(@PathVariable("id") String id);
    @PostMapping("/findDmHonourRollListByCondition")
    List<DmHonourRoll> findDmHonourRollListByCondition(DmHonourRoll dmHonourRoll);
    @PostMapping("/findOneDmHonourRollByCondition")
    DmHonourRoll findOneDmHonourRollByCondition(DmHonourRoll dmHonourRoll);
    @PostMapping("/findDmHonourRollCountByCondition")
    long findDmHonourRollCountByCondition(DmHonourRoll dmHonourRoll);
}
