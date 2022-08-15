package com.yice.edu.cn.osp.feignClient.jy.collectivePlan;

import com.yice.edu.cn.common.pojo.jy.collectivePlan.CollectivePlan;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="jy",contextId = "collectivePlanFeign",path = "/collectivePlan")
public interface CollectivePlanFeign {
    @GetMapping("/findCollectivePlanById/{id}")
    CollectivePlan findCollectivePlanById(@PathVariable("id") String id);
    @PostMapping("/saveCollectivePlan")
    CollectivePlan saveCollectivePlan(CollectivePlan collectivePlan);
    @PostMapping("/findCollectivePlanListByCondition")
    List<CollectivePlan> findCollectivePlanListByCondition(CollectivePlan collectivePlan);
    @PostMapping("/findOneCollectivePlanByCondition")
    CollectivePlan findOneCollectivePlanByCondition(CollectivePlan collectivePlan);
    @PostMapping("/findCollectivePlanCountByCondition")
    long findCollectivePlanCountByCondition(CollectivePlan collectivePlan);
    @PostMapping("/updateCollectivePlan")
    void updateCollectivePlan(CollectivePlan collectivePlan);
    @GetMapping("/deleteCollectivePlan/{id}")
    void deleteCollectivePlan(@PathVariable("id") String id);
    @PostMapping("/deleteCollectivePlanByCondition")
    void deleteCollectivePlanByCondition(CollectivePlan collectivePlan);
    //根据条件查找讨论组是否重名
    @PostMapping("/findCollectivePlanByPlanName")
    List<CollectivePlan> findCollectivePlanByPlanName(CollectivePlan collectivePlan);
    //查询我创建的备课组
    @PostMapping("/findCollectivePlanList")
    List<CollectivePlan> findCollectivePlanList(CollectivePlan collectivePlan);
    //查询集体备课首用讨论组
    @PostMapping("/findCollectivePlanListByTeacherId")
    List<CollectivePlan> findCollectivePlanListByTeacherId(CollectivePlan collectivePlan);
    //查询学年
    @PostMapping("/findSchoolYear")
    List<CollectivePlan> findSchoolYear(CollectivePlan collectivePlan);

}
