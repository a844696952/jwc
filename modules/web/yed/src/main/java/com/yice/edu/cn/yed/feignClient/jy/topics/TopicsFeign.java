package com.yice.edu.cn.yed.feignClient.jy.topics;

import com.yice.edu.cn.common.pojo.jy.topics.Topics;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="jy",contextId = "topicsFeign",path = "/topics")
public interface TopicsFeign {
    @GetMapping("/findTopicsById/{id}")
    Topics findTopicsById(@PathVariable("id") String id);
    @PostMapping("/saveTopics")
    Topics saveTopics(Topics topics);
    @PostMapping("/findTopicsListByCondition")
    List<Topics> findTopicsListByCondition(Topics topics);
    @PostMapping("/findOneTopicsByCondition")
    Topics findOneTopicsByCondition(Topics topics);
    @PostMapping("/findTopicsCountByCondition")
    long findTopicsCountByCondition(Topics topics);
    @PostMapping("/updateTopics")
    void updateTopics(Topics topics);
    @GetMapping("/deleteTopics/{id}")
    void deleteTopics(@PathVariable("id") String id);
    @PostMapping("/deleteTopicsByCondition")
    void deleteTopicsByCondition(Topics topics);
    @PostMapping("/findTopicsListByCondition4Muti")
    List<Topics> findTopicsListByCondition4Muti(Topics topics);
    @PostMapping("/findTopicsCountByCondition4Muti")
    Long findTopicsCountByCondition4Muti(Topics topics);
}
