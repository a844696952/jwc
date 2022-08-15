package com.yice.edu.cn.osp.feignClient.jy.resourceCenter;

import com.yice.edu.cn.common.pojo.jy.resourceCenter.ResourceCenterTypeCondition;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="jy",contextId = "resourceCenterTypeConditionFeign",path = "/resourceCenterTypeCondition")
public interface ResourceCenterTypeConditionFeign {
    @GetMapping("/findResourceCenterTypeConditionById/{id}")
    ResourceCenterTypeCondition findResourceCenterTypeConditionById(@PathVariable("id") String id);
    @PostMapping("/saveResourceCenterTypeCondition")
    ResourceCenterTypeCondition saveResourceCenterTypeCondition(ResourceCenterTypeCondition resourceCenterTypeCondition);
    @PostMapping("/findResourceCenterTypeConditionListByCondition")
    List<ResourceCenterTypeCondition> findResourceCenterTypeConditionListByCondition(ResourceCenterTypeCondition resourceCenterTypeCondition);
    @PostMapping("/findOneResourceCenterTypeConditionByCondition")
    ResourceCenterTypeCondition findOneResourceCenterTypeConditionByCondition(ResourceCenterTypeCondition resourceCenterTypeCondition);
    @PostMapping("/findResourceCenterTypeConditionCountByCondition")
    long findResourceCenterTypeConditionCountByCondition(ResourceCenterTypeCondition resourceCenterTypeCondition);
    @PostMapping("/updateResourceCenterTypeCondition")
    void updateResourceCenterTypeCondition(ResourceCenterTypeCondition resourceCenterTypeCondition);
    @GetMapping("/deleteResourceCenterTypeCondition/{id}")
    void deleteResourceCenterTypeCondition(@PathVariable("id") String id);
    @PostMapping("/deleteResourceCenterTypeConditionByCondition")
    void deleteResourceCenterTypeConditionByCondition(ResourceCenterTypeCondition resourceCenterTypeCondition);
}
