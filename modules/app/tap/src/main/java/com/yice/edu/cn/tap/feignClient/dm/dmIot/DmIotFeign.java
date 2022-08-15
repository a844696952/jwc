package com.yice.edu.cn.tap.feignClient.dm.dmIot;

import com.yice.edu.cn.common.pojo.dm.dmIot.DmIot;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="dm",contextId = "dmIotFeign",path = "/dmIot")
public interface DmIotFeign {
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @GetMapping("/findDmIotById/{id}")
    DmIot findDmIotById(@PathVariable("id") String id);
    @PostMapping("/saveDmIot")
    DmIot saveDmIot(DmIot dmIot);
    @PostMapping("/batchSaveDmIot")
    void batchSaveDmIot(List<DmIot> dmIots);
    @PostMapping("/findDmIotListByCondition")
    List<DmIot> findDmIotListByCondition(DmIot dmIot);
    @PostMapping("/findOneDmIotByCondition")
    DmIot findOneDmIotByCondition(DmIot dmIot);
    @PostMapping("/findDmIotCountByCondition")
    long findDmIotCountByCondition(DmIot dmIot);
    @PostMapping("/updateDmIot")
    void updateDmIot(DmIot dmIot);
    @PostMapping("/updateDmIotForAll")
    void updateDmIotForAll(DmIot dmIot);
    @GetMapping("/deleteDmIot/{id}")
    void deleteDmIot(@PathVariable("id") String id);
    @PostMapping("/deleteDmIotByCondition")
    void deleteDmIotByCondition(DmIot dmIot);
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
