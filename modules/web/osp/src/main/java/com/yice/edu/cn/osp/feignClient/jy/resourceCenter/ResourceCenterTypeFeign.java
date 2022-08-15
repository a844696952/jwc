package com.yice.edu.cn.osp.feignClient.jy.resourceCenter;

import com.yice.edu.cn.common.pojo.jy.resourceCenter.ResourceCenterType;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="jy",contextId = "resourceCenterTypeFeign",path = "/resourceCenterType")
public interface ResourceCenterTypeFeign {
    @GetMapping("/findResourceCenterTypeById/{id}")
    ResourceCenterType findResourceCenterTypeById(@PathVariable("id") String id);
    @PostMapping("/saveResourceCenterType")
    ResourceCenterType saveResourceCenterType(ResourceCenterType resourceCenterType);
    @PostMapping("/findResourceCenterTypeListByCondition")
    List<ResourceCenterType> findResourceCenterTypeListByCondition(ResourceCenterType resourceCenterType);
    @PostMapping("/findOneResourceCenterTypeByCondition")
    ResourceCenterType findOneResourceCenterTypeByCondition(ResourceCenterType resourceCenterType);
    @PostMapping("/findResourceCenterTypeCountByCondition")
    long findResourceCenterTypeCountByCondition(ResourceCenterType resourceCenterType);
    @PostMapping("/updateResourceCenterType")
    void updateResourceCenterType(ResourceCenterType resourceCenterType);
    @GetMapping("/deleteResourceCenterType/{id}")
    ResourceCenterType deleteResourceCenterType(@PathVariable("id") String id);
    @PostMapping("/deleteResourceCenterTypeByCondition")
    void deleteResourceCenterTypeByCondition(ResourceCenterType resourceCenterType);
    @PostMapping("/saveResourceCenterType4Like")
    ResourceCenterType saveResourceCenterType4Like(ResourceCenterType resourceCenterType);
    @PostMapping("/findResourceCenterTypeListByCondition4Like")
    List<ResourceCenterType> findResourceCenterTypeListByCondition4Like(ResourceCenterType resourceCenterType);
    @PostMapping("/updateResourceCenterType4Like")
    void updateResourceCenterType4Like(ResourceCenterType resourceCenterType);
    @GetMapping("/deleteResourceCenterType4Like/{id}")
    ResourceCenterType deleteResourceCenterType4Like(@PathVariable("id") String id);
}
