package com.yice.edu.cn.ecc.feignClient.schoolActive;

import com.yice.edu.cn.common.pojo.dm.schoolActive.DmActivityInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="dm",path = "/dmActivityInfo")
public interface DmActivityInfoFeign {
    @GetMapping("/findDmActivityInfoById/{id}")
    DmActivityInfo findDmActivityInfoById(@PathVariable("id") String id);
    @PostMapping("/saveDmActivityInfo")
    DmActivityInfo saveDmActivityInfo(DmActivityInfo dmActivityInfo);
    @PostMapping("/findDmActivityInfoListByCondition")
    List<DmActivityInfo> findDmActivityInfoListByCondition(DmActivityInfo dmActivityInfo);
    @PostMapping("/findOneDmActivityInfoByCondition")
    DmActivityInfo findOneDmActivityInfoByCondition(DmActivityInfo dmActivityInfo);
    @PostMapping("/findDmActivityInfoCountByCondition")
    long findDmActivityInfoCountByCondition(DmActivityInfo dmActivityInfo);
    @PostMapping("/updateDmActivityInfo")
    void updateDmActivityInfo(DmActivityInfo dmActivityInfo);
    @GetMapping("/deleteDmActivityInfo/{id}")
    void deleteDmActivityInfo(@PathVariable("id") String id);
    @PostMapping("/deleteDmActivityInfoByCondition")
    void deleteDmActivityInfoByCondition(DmActivityInfo dmActivityInfo);
    @PostMapping("/findDmActivityInfosByCondition")
    List<DmActivityInfo> findDmActivityInfosByCondition(DmActivityInfo dmActivityInfo);

}
