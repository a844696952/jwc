package com.yice.edu.cn.bmp.feignClient.topics;

import com.yice.edu.cn.common.pojo.jy.topics.WrongTopics;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="jy",contextId = "wrongTopicsFeign",path = "/wrongTopics")
public interface WrongTopicsFeign {
    @GetMapping("/findWrongTopicsById/{id}")
    WrongTopics findWrongTopicsById(@PathVariable("id") String id);
    @PostMapping("/saveWrongTopics")
    WrongTopics saveWrongTopics(WrongTopics wrongTopics);
    @PostMapping("/findWrongTopicsListByCondition")
    List<WrongTopics> findWrongTopicsListByCondition(WrongTopics wrongTopics);
    @PostMapping("/findOneWrongTopicsByCondition")
    WrongTopics findOneWrongTopicsByCondition(WrongTopics wrongTopics);
    @PostMapping("/findWrongTopicsCountByCondition")
    long findWrongTopicsCountByCondition(WrongTopics wrongTopics);
    @PostMapping("/updateWrongTopics")
    void updateWrongTopics(WrongTopics wrongTopics);
    @GetMapping("/deleteWrongTopics/{id}")
    void deleteWrongTopics(@PathVariable("id") String id);
    @PostMapping("/deleteWrongTopicsByCondition")
    void deleteWrongTopicsByCondition(WrongTopics wrongTopics);
}
