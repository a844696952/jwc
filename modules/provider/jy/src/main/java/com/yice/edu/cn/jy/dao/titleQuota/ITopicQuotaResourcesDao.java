package com.yice.edu.cn.jy.dao.titleQuota;

import com.yice.edu.cn.common.pojo.jy.titleQuota.ResourcePlatform;
import com.yice.edu.cn.common.pojo.jy.titleQuota.TopicQuotaResources;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ITopicQuotaResourcesDao {
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    List<TopicQuotaResources> findTopicQuotaResourcesListByCondition(TopicQuotaResources topicQuotaResources);

    long findTopicQuotaResourcesCountByCondition(TopicQuotaResources topicQuotaResources);

    TopicQuotaResources findOneTopicQuotaResourcesByCondition(TopicQuotaResources topicQuotaResources);

    TopicQuotaResources findTopicQuotaResourcesById(@Param("id") String id);

    void saveTopicQuotaResources(TopicQuotaResources topicQuotaResources);

    void updateTopicQuotaResources(TopicQuotaResources topicQuotaResources);

    void updateTopicQuotaResourcesForAll(TopicQuotaResources topicQuotaResources);

    void deleteTopicQuotaResources(@Param("id") String id);

    void deleteTopicQuotaResourcesByCondition(TopicQuotaResources topicQuotaResources);

    void batchSaveTopicQuotaResources(List<TopicQuotaResources> topicQuotaResourcess);


    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
    TopicQuotaResources getBaiscInfo(TopicQuotaResources topicQuotaResources);


    List<ResourcePlatform> findPaltFormByCondition(TopicQuotaResources topicQuotaResources);

    void updateTopicQuotaResources4Like(TopicQuotaResources topicQuotaResources);

    void updateTopicQuotaResourcesByOne(TopicQuotaResources temp1);
}
