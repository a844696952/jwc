package com.yice.edu.cn.yed.service.jy.titleQuota;

import com.yice.edu.cn.common.pojo.jy.titleQuota.ResourcePlatform;
import com.yice.edu.cn.common.pojo.jy.titleQuota.TopicQuotaResources;
import com.yice.edu.cn.yed.feignClient.jy.titleQuota.TopicQuotaResourcesFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopicQuotaResourcesService {
    @Autowired
    private TopicQuotaResourcesFeign topicQuotaResourcesFeign;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    public TopicQuotaResources findTopicQuotaResourcesById(String id) {
        return topicQuotaResourcesFeign.findTopicQuotaResourcesById(id);
    }

    public TopicQuotaResources saveTopicQuotaResources(TopicQuotaResources topicQuotaResources) {
        return topicQuotaResourcesFeign.saveTopicQuotaResources(topicQuotaResources);
    }

    public void batchSaveTopicQuotaResources(List<TopicQuotaResources> topicQuotaResourcess){
        topicQuotaResourcesFeign.batchSaveTopicQuotaResources(topicQuotaResourcess);
    }

    public List<TopicQuotaResources> findTopicQuotaResourcesListByCondition(TopicQuotaResources topicQuotaResources) {
        return topicQuotaResourcesFeign.findTopicQuotaResourcesListByCondition(topicQuotaResources);
    }

    public TopicQuotaResources findOneTopicQuotaResourcesByCondition(TopicQuotaResources topicQuotaResources) {
        return topicQuotaResourcesFeign.findOneTopicQuotaResourcesByCondition(topicQuotaResources);
    }

    public long findTopicQuotaResourcesCountByCondition(TopicQuotaResources topicQuotaResources) {
        return topicQuotaResourcesFeign.findTopicQuotaResourcesCountByCondition(topicQuotaResources);
    }

    public void updateTopicQuotaResources(TopicQuotaResources topicQuotaResources) {
        topicQuotaResourcesFeign.updateTopicQuotaResources(topicQuotaResources);
    }

    public void updateTopicQuotaResourcesForAll(TopicQuotaResources topicQuotaResources) {
        topicQuotaResourcesFeign.updateTopicQuotaResourcesForAll(topicQuotaResources);
    }

    public void deleteTopicQuotaResources(String id) {
        topicQuotaResourcesFeign.deleteTopicQuotaResources(id);
    }

    public void deleteTopicQuotaResourcesByCondition(TopicQuotaResources topicQuotaResources) {
        topicQuotaResourcesFeign.deleteTopicQuotaResourcesByCondition(topicQuotaResources);
    }



    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
    public TopicQuotaResources updateTopicQuotaResources4Person(TopicQuotaResources topicQuotaResources) {
        return  topicQuotaResourcesFeign.updateTopicQuotaResources4Person(topicQuotaResources);
    }

    public TopicQuotaResources getBaiscInfo(String schoolId) {
        return  topicQuotaResourcesFeign.getBaiscInfo(schoolId);
    }

    public List<ResourcePlatform> findPaltFormByCondition(TopicQuotaResources topicQuotaResources) {
        return  topicQuotaResourcesFeign.findPaltFormByCondition(topicQuotaResources);
    }
}
