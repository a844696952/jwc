package com.yice.edu.cn.osp.feignClient.xw.dormManage.dorm;

import com.yice.edu.cn.common.pojo.xw.dormManage.dorm.DormPersonLog;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="xw",contextId = "dormPersonLogFeign",path = "/dormPersonLog")
public interface DormPersonLogFeign {
    @GetMapping("/findDormPersonLogById/{id}")
    DormPersonLog findDormPersonLogById(@PathVariable("id") String id);
    @PostMapping("/saveDormPersonLog")
    DormPersonLog saveDormPersonLog(DormPersonLog dormPersonLog);
    @PostMapping("/findDormPersonLogListByCondition")
    List<DormPersonLog> findDormPersonLogListByCondition(DormPersonLog dormPersonLog);
    @PostMapping("/findOneDormPersonLogByCondition")
    DormPersonLog findOneDormPersonLogByCondition(DormPersonLog dormPersonLog);
    @PostMapping("/findDormPersonLogCountByCondition")
    long findDormPersonLogCountByCondition(DormPersonLog dormPersonLog);
    @PostMapping("/updateDormPersonLog")
    void updateDormPersonLog(DormPersonLog dormPersonLog);
    @GetMapping("/deleteDormPersonLog/{id}")
    void deleteDormPersonLog(@PathVariable("id") String id);
    @PostMapping("/deleteDormPersonLogByCondition")
    void deleteDormPersonLogByCondition(DormPersonLog dormPersonLog);
}
