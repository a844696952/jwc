package com.yice.edu.cn.xw.feignClient.resetDynamicTask;

import com.yice.edu.cn.common.pojo.ts.dynamicCron.DynamicCron;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="jw",path = "/dynamicCron")
public interface DynamicCronFeign {
    @GetMapping("/findDynamicCronById/{id}")
    DynamicCron findDynamicCronById(@PathVariable("id") String id);
    @PostMapping("/saveDynamicCron")
    DynamicCron saveDynamicCron(DynamicCron dynamicCron);
    @PostMapping("/findDynamicCronListByCondition")
    List<DynamicCron> findDynamicCronListByCondition(DynamicCron dynamicCron);
    @PostMapping("/findOneDynamicCronByCondition")
    DynamicCron findOneDynamicCronByCondition(DynamicCron dynamicCron);
    @PostMapping("/findDynamicCronCountByCondition")
    long findDynamicCronCountByCondition(DynamicCron dynamicCron);
    @PostMapping("/updateDynamicCron")
    void updateDynamicCron(DynamicCron dynamicCron);
    @GetMapping("/deleteDynamicCron/{id}")
    void deleteDynamicCron(@PathVariable("id") String id);
    @PostMapping("/deleteDynamicCronByCondition")
    void deleteDynamicCronByCondition(DynamicCron dynamicCron);
}
