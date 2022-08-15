package com.yice.edu.cn.yed.service.jy.topics;

import com.yice.edu.cn.common.pojo.jy.topics.Topics;
import com.yice.edu.cn.yed.feignClient.jy.topics.TopicsFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopicsService {
    @Autowired
    private TopicsFeign topicsFeign;

    public Topics findTopicsById(String id) {
        return topicsFeign.findTopicsById(id);
    }

    public Topics saveTopics(Topics topics) {
        return topicsFeign.saveTopics(topics);
    }

    public List<Topics> findTopicsListByCondition(Topics topics) {
        return topicsFeign.findTopicsListByCondition(topics);
    }
    public List<Topics> findTopicsListByCondition4Muti(Topics topics) {
        return topicsFeign.findTopicsListByCondition4Muti(topics);
    }
    public Long findTopicsCountByCondition4Muti(Topics topics) {
        return topicsFeign.findTopicsCountByCondition4Muti(topics);
    }
    public Topics findOneTopicsByCondition(Topics topics) {
        return topicsFeign.findOneTopicsByCondition(topics);
    }

    public long findTopicsCountByCondition(Topics topics) {
        return topicsFeign.findTopicsCountByCondition(topics);
    }

    public void updateTopics(Topics topics) {
        topicsFeign.updateTopics(topics);
    }

    public void deleteTopics(String id) {
        topicsFeign.deleteTopics(id);
    }

    public void deleteTopicsByCondition(Topics topics) {
        topicsFeign.deleteTopicsByCondition(topics);
    }
}
