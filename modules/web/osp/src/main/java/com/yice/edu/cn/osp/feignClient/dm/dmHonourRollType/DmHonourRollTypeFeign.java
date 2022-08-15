package com.yice.edu.cn.osp.feignClient.dm.dmHonourRollType;

import com.yice.edu.cn.common.pojo.dm.dmHonourRollType.DmHonourRollType;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="dm",contextId = "dmHonourRollType",path = "/dmHonourRollType")
public interface DmHonourRollTypeFeign {
    @GetMapping("/findDmHonourRollTypeById/{id}")
    DmHonourRollType findDmHonourRollTypeById(@PathVariable("id") String id);
    @PostMapping("/saveDmHonourRollType")
    DmHonourRollType saveDmHonourRollType(DmHonourRollType dmHonourRollType);
    @PostMapping("/findDmHonourRollTypeListByCondition")
    List<DmHonourRollType> findDmHonourRollTypeListByCondition(DmHonourRollType dmHonourRollType);
    @PostMapping("/findOneDmHonourRollTypeByCondition")
    DmHonourRollType findOneDmHonourRollTypeByCondition(DmHonourRollType dmHonourRollType);
    @PostMapping("/findDmHonourRollTypeCountByCondition")
    long findDmHonourRollTypeCountByCondition(DmHonourRollType dmHonourRollType);
    @PostMapping("/updateDmHonourRollType")
    void updateDmHonourRollType(DmHonourRollType dmHonourRollType);
    @GetMapping("/deleteDmHonourRollType/{id}")
    void deleteDmHonourRollType(@PathVariable("id") String id);
    @PostMapping("/deleteDmHonourRollTypeByCondition")
    void deleteDmHonourRollTypeByCondition(DmHonourRollType dmHonourRollType);
}
