package com.yice.edu.cn.yed.feignClient.jy.titleQuota;

import com.yice.edu.cn.common.pojo.jy.titleQuota.ResourcePlatform;
import com.yice.edu.cn.common.pojo.jy.titleQuota.TopicQuotaResources;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="jy",contextId = "topicQuotaResourcesFeign",path = "/topicQuotaResources")
public interface TopicQuotaResourcesFeign {
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @GetMapping("/findTopicQuotaResourcesById/{id}")
    TopicQuotaResources findTopicQuotaResourcesById(@PathVariable("id") String id);
    @PostMapping("/saveTopicQuotaResources")
    TopicQuotaResources saveTopicQuotaResources(TopicQuotaResources topicQuotaResources);
    @PostMapping("/batchSaveTopicQuotaResources")
    void batchSaveTopicQuotaResources(List<TopicQuotaResources> topicQuotaResourcess);
    @PostMapping("/findTopicQuotaResourcesListByCondition")
    List<TopicQuotaResources> findTopicQuotaResourcesListByCondition(TopicQuotaResources topicQuotaResources);
    @PostMapping("/findOneTopicQuotaResourcesByCondition")
    TopicQuotaResources findOneTopicQuotaResourcesByCondition(TopicQuotaResources topicQuotaResources);
    @PostMapping("/findTopicQuotaResourcesCountByCondition")
    long findTopicQuotaResourcesCountByCondition(TopicQuotaResources topicQuotaResources);
    @PostMapping("/updateTopicQuotaResources")
    void updateTopicQuotaResources(TopicQuotaResources topicQuotaResources);
    @PostMapping("/updateTopicQuotaResourcesForNotNull")
    void updateTopicQuotaResourcesForAll(TopicQuotaResources topicQuotaResources);
    @GetMapping("/deleteTopicQuotaResources/{id}")
    void deleteTopicQuotaResources(@PathVariable("id") String id);
    @PostMapping("/deleteTopicQuotaResourcesByCondition")
    void deleteTopicQuotaResourcesByCondition(TopicQuotaResources topicQuotaResources);



    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
    @PostMapping("/updateTopicQuotaResources4Person")
    TopicQuotaResources updateTopicQuotaResources4Person(TopicQuotaResources topicQuotaResources);
    @GetMapping("/getBaiscInfo/{id}")
    TopicQuotaResources getBaiscInfo(@PathVariable("id")String schoolId);
    @PostMapping("/findPaltFormByCondition")
    List<ResourcePlatform> findPaltFormByCondition(TopicQuotaResources topicQuotaResources);
}
