package com.yice.edu.cn.bmp.service.topics;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yice.edu.cn.bmp.feignClient.topics.TopicsRecordFeign;
import com.yice.edu.cn.common.pojo.jy.homework.StuHomeRecordVo;
import com.yice.edu.cn.common.pojo.jy.topics.TopicsRecord;

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
    
    public List<StuHomeRecordVo> queryHomeworkCorrectRateByHomeworkId(String homeworkSqId){
    	return topicsRecordFeign.queryHomeworkCorrectRateByHomeworkId(homeworkSqId);
    }

    public List<TopicsRecord> findTopicsRecordBy4Like(TopicsRecord topicsRecord) {
        return topicsRecordFeign.findTopicsRecordBy4Like(topicsRecord);
    }
}
