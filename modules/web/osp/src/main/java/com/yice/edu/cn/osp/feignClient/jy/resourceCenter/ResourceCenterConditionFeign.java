package com.yice.edu.cn.osp.feignClient.jy.resourceCenter;

import com.yice.edu.cn.common.pojo.jy.resourceCenter.ResourceCenterCondition;
import com.yice.edu.cn.common.pojo.jy.resourceCenter.ResourceCenterType;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="jy",contextId = "resourceCenterConditionFeign",path = "/resourceCenterCondition")
public interface ResourceCenterConditionFeign {
    @GetMapping("/findResourceCenterConditionById/{id}")
    ResourceCenterCondition findResourceCenterConditionById(@PathVariable("id") String id);
    @PostMapping("/saveResourceCenterCondition")
    ResourceCenterCondition saveResourceCenterCondition(ResourceCenterCondition resourceCenterCondition);
    @PostMapping("/findResourceCenterConditionListByCondition")
    List<ResourceCenterCondition> findResourceCenterConditionListByCondition(ResourceCenterCondition resourceCenterCondition);
    @PostMapping("/findOneResourceCenterConditionByCondition")
    ResourceCenterCondition findOneResourceCenterConditionByCondition(ResourceCenterCondition resourceCenterCondition);
    @PostMapping("/findResourceCenterConditionCountByCondition")
    long findResourceCenterConditionCountByCondition(ResourceCenterCondition resourceCenterCondition);
    @PostMapping("/updateResourceCenterCondition")
    void updateResourceCenterCondition(ResourceCenterCondition resourceCenterCondition);
    @GetMapping("/deleteResourceCenterCondition/{id}")
    void deleteResourceCenterCondition(@PathVariable("id") String id);
    @PostMapping("/deleteResourceCenterConditionByCondition")
    void deleteResourceCenterConditionByCondition(ResourceCenterCondition resourceCenterCondition);
    @PostMapping("/deleteResourceCenterCondition4Like")
    ResourceCenterType deleteResourceCenterCondition4Like(ResourceCenterType resourceCenterType);
}
