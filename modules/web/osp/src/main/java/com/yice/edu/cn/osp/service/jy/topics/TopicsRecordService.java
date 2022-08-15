package com.yice.edu.cn.osp.service.jy.topics;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yice.edu.cn.common.pojo.jy.topics.TopicsRecord;
import com.yice.edu.cn.osp.feignClient.jy.topics.TopicsRecordFeign;

@Service
public class TopicsRecordService {
    @Autowired
    private TopicsRecordFeign topicsRecordFeign;

    public TopicsRecord findTopicsRecordById(String id) {
        return topicsRecordFeign.findTopicsRecordById(id);
    }

    public TopicsRecord saveTopicsRecord(TopicsRecord topicsRecord) {
        return topicsRecordFeign.saveTopicsRecord(topicsRecord);
    }

    public List<TopicsRecord> findTopicsRecordListByCondition(TopicsRecord topicsRecord) {
        return topicsRecordFeign.findTopicsRecordListByCondition(topicsRecord);
    }

    public TopicsRecord findOneTopicsRecordByCondition(TopicsRecord topicsRecord) {
        return topicsRecordFeign.findOneTopicsRecordByCondition(topicsRecord);
    }

    public long findTopicsRecordCountByCondition(TopicsRecord topicsRecord) {
        return topicsRecordFeign.findTopicsRecordCountByCondition(topicsRecord);
    }

    public void updateTopicsRecord(TopicsRecord topicsRecord) {
        topicsRecordFeign.updateTopicsRecord(topicsRecord);
    }

    public void deleteTopicsRecord(String id) {
        topicsRecordFeign.deleteTopicsRecord(id);
    }

    public void deleteTopicsRecordByCondition(TopicsRecord topicsRecord) {
        topicsRecordFeign.deleteTopicsRecordByCondition(topicsRecord);
    }
}
