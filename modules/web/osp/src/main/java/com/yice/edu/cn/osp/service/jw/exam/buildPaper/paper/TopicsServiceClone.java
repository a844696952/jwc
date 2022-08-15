package com.yice.edu.cn.osp.service.jw.exam.buildPaper.paper;

import com.yice.edu.cn.common.pojo.jy.topics.Topics;
import com.yice.edu.cn.osp.feignClient.jw.exam.buildPaper.paper.TopicsFeignClone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopicsServiceClone {
    @Autowired
    private TopicsFeignClone topicsFeignClone;

    public List<Topics> findTopicsListByCondition4Muti(Topics topics) {
        return topicsFeignClone.findTopicsListByCondition4Muti(topics);
    }
    public long findTopicsCountByCondition4Muti(Topics topics) {
        return topicsFeignClone.findTopicsCountByCondition4Muti(topics);
    }
}
