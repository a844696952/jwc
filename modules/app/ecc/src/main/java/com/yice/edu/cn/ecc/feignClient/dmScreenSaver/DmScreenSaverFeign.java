package com.yice.edu.cn.ecc.feignClient.dmScreenSaver;

import com.yice.edu.cn.common.pojo.dm.dmScreenSaver.DmScreenSaver;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="dm",path = "/dmScreenSaver")
public interface DmScreenSaverFeign {
    @GetMapping("/findDmScreenSaverById/{id}")
    DmScreenSaver findDmScreenSaverById(@PathVariable("id") String id);
    @PostMapping("/saveDmScreenSaver")
    DmScreenSaver saveDmScreenSaver(DmScreenSaver dmScreenSaver);
    @PostMapping("/findDmScreenSaverListByCondition")
    List<DmScreenSaver> findDmScreenSaverListByCondition(DmScreenSaver dmScreenSaver);
    @PostMapping("/findOneDmScreenSaverByCondition")
    DmScreenSaver findOneDmScreenSaverByCondition(DmScreenSaver dmScreenSaver);
    @PostMapping("/findDmScreenSaverCountByCondition")
    long findDmScreenSaverCountByCondition(DmScreenSaver dmScreenSaver);
    @PostMapping("/updateDmScreenSaver")
    void updateDmScreenSaver(DmScreenSaver dmScreenSaver);
    @GetMapping("/deleteDmScreenSaver/{id}")
    void deleteDmScreenSaver(@PathVariable("id") String id);
    @PostMapping("/deleteDmScreenSaverByCondition")
    void deleteDmScreenSaverByCondition(DmScreenSaver dmScreenSaver);
    @PostMapping("/getRunNingDmScreenSaver")
    DmScreenSaver getRunNingDmScreenSaver(DmScreenSaver dmScreenSaver);
    @PostMapping("/batchUpdateDmScreenSaverStatus")
    void batchUpdateDmScreenSaverStatus(DmScreenSaver dmScreenSaver);
}
