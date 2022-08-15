package com.yice.edu.cn.osp.feignClient.jw.exam.buildPaper.paper;

import com.yice.edu.cn.common.pojo.jy.topics.Topics;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value = "jy",contextId = "topicsFeignClone",path = "/topics")
public interface TopicsFeignClone {
    @PostMapping("/findTopicsListByCondition4Muti")
    List<Topics> findTopicsListByCondition4Muti(Topics topics);
    @PostMapping("/findTopicsCountByCondition4Muti")
    long findTopicsCountByCondition4Muti(Topics topics);
}
