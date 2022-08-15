package com.yice.edu.cn.tap.feignClient.titleQuota;

import com.yice.edu.cn.common.pojo.jy.titleQuota.ResourcePlatform;
import com.yice.edu.cn.common.pojo.jy.titleQuota.TopicQuotaResources;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="jy",contextId = "topicQuotaResourcesFeign",path = "/topicQuotaResources")
public interface TopicQuotaResourcesFeign {

    @GetMapping("/getBaiscInfo/{id}")
    TopicQuotaResources getBaiscInfo(@PathVariable("id") String schoolId);
    @PostMapping("/findPaltFormByCondition")
    List<ResourcePlatform> findPaltFormByCondition(TopicQuotaResources topicQuotaResources);
}
