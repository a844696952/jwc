package com.yice.edu.cn.common.feign;

import com.yice.edu.cn.common.pojo.dm.classCard.DmLog;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="dm",contextId = "dmLogFeign",path = "/dmLog")
public interface DmLogFeign {
    @GetMapping("/findDmLogById/{id}")
    DmLog findDmLogById(@PathVariable("id") String id);
    @PostMapping("/saveDmLog")
    DmLog saveDmLog(DmLog dmLog);
    @PostMapping("/findDmLogListByCondition")
    List<DmLog> findDmLogListByCondition(DmLog dmLog);
    @PostMapping("/findOneDmLogByCondition")
    DmLog findOneDmLogByCondition(DmLog dmLog);
    @PostMapping("/findDmLogCountByCondition")
    long findDmLogCountByCondition(DmLog dmLog);
    @PostMapping("/updateDmLog")
    void updateDmLog(DmLog dmLog);
    @GetMapping("/deleteDmLog/{id}")
    void deleteDmLog(@PathVariable("id") String id);
    @PostMapping("/deleteDmLogByCondition")
    void deleteDmLogByCondition(DmLog dmLog);
    @PostMapping("/batchSaveDmLog")
    DmLog batchSaveDmLog(List<DmLog> dmLog);
}
