package com.yice.edu.cn.ewb.feignClient.pentrace;

import com.yice.edu.cn.common.pojo.wb.pentrace.DmPentrace;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="dm",path = "/dmPentrace")
public interface DmPentraceFeign {
    @GetMapping("/findDmPentraceById/{id}")
    DmPentrace findDmPentraceById(@PathVariable("id") String id);
    @PostMapping("/saveDmPentrace")
    DmPentrace saveDmPentrace(DmPentrace dmPentrace);
    @PostMapping("/findDmPentraceListByCondition")
    List<DmPentrace> findDmPentraceListByCondition(DmPentrace dmPentrace);
    @PostMapping("/findOneDmPentraceByCondition")
    DmPentrace findOneDmPentraceByCondition(DmPentrace dmPentrace);
    @PostMapping("/findDmPentraceCountByCondition")
    long findDmPentraceCountByCondition(DmPentrace dmPentrace);
    @PostMapping("/updateDmPentrace")
    void updateDmPentrace(DmPentrace dmPentrace);
    @GetMapping("/deleteDmPentrace/{id}")
    void deleteDmPentrace(@PathVariable("id") String id);
    @PostMapping("/deleteDmPentraceByCondition")
    void deleteDmPentraceByCondition(DmPentrace dmPentrace);
}
