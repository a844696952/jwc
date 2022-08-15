package com.yice.edu.cn.osp.feignClient.jy.titleQuota;

import com.yice.edu.cn.common.pojo.jy.titleQuota.ResourcePlatform;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="jy",contextId = "resourcePlatformFeign",path = "/resourcePlatform")
public interface ResourcePlatformFeign {
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @GetMapping("/findResourcePlatformById/{id}")
    ResourcePlatform findResourcePlatformById(@PathVariable("id") String id);
    @PostMapping("/saveResourcePlatform")
    ResourcePlatform saveResourcePlatform(ResourcePlatform resourcePlatform);
    @PostMapping("/batchSaveResourcePlatform")
    void batchSaveResourcePlatform(List<ResourcePlatform> resourcePlatforms);
    @PostMapping("/findResourcePlatformListByCondition")
    List<ResourcePlatform> findResourcePlatformListByCondition(ResourcePlatform resourcePlatform);
    @PostMapping("/findOneResourcePlatformByCondition")
    ResourcePlatform findOneResourcePlatformByCondition(ResourcePlatform resourcePlatform);
    @PostMapping("/findResourcePlatformCountByCondition")
    long findResourcePlatformCountByCondition(ResourcePlatform resourcePlatform);
    @PostMapping("/updateResourcePlatform")
    void updateResourcePlatform(ResourcePlatform resourcePlatform);
    @PostMapping("/updateResourcePlatformForNotNull")
    void updateResourcePlatformForAll(ResourcePlatform resourcePlatform);
    @GetMapping("/deleteResourcePlatform/{id}")
    void deleteResourcePlatform(@PathVariable("id") String id);
    @PostMapping("/deleteResourcePlatformByCondition")
    void deleteResourcePlatformByCondition(ResourcePlatform resourcePlatform);
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
