package com.yice.edu.cn.osp.feignClient.dm.active;

import com.yice.edu.cn.common.pojo.dm.active.DmActive;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value="dm",contextId = "dmActiveFeign",path = "/dmActive")
public interface DmActiveFeign {
    @GetMapping("/findDmActiveById/{id}")
    DmActive findDmActiveById(@PathVariable("id") String id);
    @PostMapping("/saveDmActive")
    DmActive saveDmActive(DmActive dmActive);
    @PostMapping("/findDmActiveListByCondition")
    List<DmActive> findDmActiveListByCondition(DmActive dmActive);
    @PostMapping("/findOneDmActiveByCondition")
    DmActive findOneDmActiveByCondition(DmActive dmActive);
    @PostMapping("/findDmActiveCountByCondition")
    long findDmActiveCountByCondition(DmActive dmActive);
    @PostMapping("/updateDmActive")
    void updateDmActive(DmActive dmActive);
    @GetMapping("/deleteDmActive/{id}")
    void deleteDmActive(@PathVariable("id") String id);
    @PostMapping("/deleteDmActiveByCondition")
    void deleteDmActiveByCondition(DmActive dmActive);
    @PostMapping("/deleteManyDmActive")
    void deleteManyDmActive(DmActive dmActive);

    @PostMapping("/findDmActiveListByConditionLike")
    List<DmActive> findDmActiveListByConditionLike(DmActive dmActive);
    @PostMapping("/findDmActiveCountByConditionLike")
    long findDmActiveCountByConditionLike(DmActive dmActive);

}
