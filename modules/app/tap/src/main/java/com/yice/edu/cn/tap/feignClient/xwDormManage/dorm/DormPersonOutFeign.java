package com.yice.edu.cn.tap.feignClient.xwDormManage.dorm;

import com.yice.edu.cn.common.pojo.xw.dormManage.dorm.DormPersonOut;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="xw",contextId = "dormPersonOutFeign",path = "/dormPersonOut")
public interface DormPersonOutFeign {
    @GetMapping("/findDormPersonOutById/{id}")
    DormPersonOut findDormPersonOutById(@PathVariable("id") String id);
    @PostMapping("/saveDormPersonOut")
    DormPersonOut saveDormPersonOut(DormPersonOut dormPersonOut);
    @PostMapping("/findDormPersonOutListByCondition")
    List<DormPersonOut> findDormPersonOutListByCondition(DormPersonOut dormPersonOut);
    @PostMapping("/findOneDormPersonOutByCondition")
    DormPersonOut findOneDormPersonOutByCondition(DormPersonOut dormPersonOut);
    @PostMapping("/findDormPersonOutCountByCondition")
    long findDormPersonOutCountByCondition(DormPersonOut dormPersonOut);
    @PostMapping("/updateDormPersonOut")
    void updateDormPersonOut(DormPersonOut dormPersonOut);
    @GetMapping("/deleteDormPersonOut/{id}")
    void deleteDormPersonOut(@PathVariable("id") String id);
    @PostMapping("/deleteDormPersonOutByCondition")
    void deleteDormPersonOutByCondition(DormPersonOut dormPersonOut);

    /*---------------------------------------------------------------------------------------------*/
    @PostMapping("/findDormPersonOutListByConditionAndPersonType")
    List<DormPersonOut> findDormPersonOutListByConditionAndPersonType(DormPersonOut dormPersonOut);
    @PostMapping("/findDormPersonOutCountByConditionAndPersonType")
    long findDormPersonOutCountByConditionAndPersonType(DormPersonOut dormPersonOut);
}
