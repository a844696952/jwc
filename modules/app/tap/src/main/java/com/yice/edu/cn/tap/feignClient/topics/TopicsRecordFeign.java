package com.yice.edu.cn.tap.feignClient.topics;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.yice.edu.cn.common.pojo.jy.homework.StuHomeRecordVo;
import com.yice.edu.cn.common.pojo.jy.topics.TopicsRecord;

import io.swagger.annotations.ApiParam;

@FeignClient(value="jy",path = "/topicsRecord")
public interface TopicsRecordFeign {
    @GetMapping("/findTopicsRecordById/{id}")
    TopicsRecord findTopicsRecordById(@PathVariable("id") String id);
    @PostMapping("/saveTopicsRecord")
    TopicsRecord saveTopicsRecord(TopicsRecord topicsRecord);
    @PostMapping("/findTopicsRecordListByCondition")
    List<TopicsRecord> findTopicsRecordListByCondition(TopicsRecord topicsRecord);
    @PostMapping("/findOneTopicsRecordByCondition")
    TopicsRecord findOneTopicsRecordByCondition(TopicsRecord topicsRecord);
    @PostMapping("/findTopicsRecordCountByCondition")
    long findTopicsRecordCountByCondition(TopicsRecord topicsRecord);
    @PostMapping("/updateTopicsRecord")
    void updateTopicsRecord(TopicsRecord topicsRecord);
    @GetMapping("/deleteTopicsRecord/{id}")
    void deleteTopicsRecord(@PathVariable("id") String id);
    @PostMapping("/deleteTopicsRecordByCondition")
    void deleteTopicsRecordByCondition(TopicsRecord topicsRecord);
    @GetMapping("/queryHomeworkCorrectRateByHomeworkId/{homeworkSqId}")
    List<StuHomeRecordVo> queryHomeworkCorrectRateByHomeworkId(@PathVariable("homeworkSqId") String homeworkSqId);
}
