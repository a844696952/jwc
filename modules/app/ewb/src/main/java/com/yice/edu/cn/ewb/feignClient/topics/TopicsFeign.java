package com.yice.edu.cn.ewb.feignClient.topics;

import com.yice.edu.cn.common.pojo.jy.topics.TopicParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value="jy",contextId = "topicsFeign",path = "/topics")
public interface TopicsFeign {

    @PostMapping("/findTopicDetail")
    Object findTopicDetail(TopicParam topicParam);
}

