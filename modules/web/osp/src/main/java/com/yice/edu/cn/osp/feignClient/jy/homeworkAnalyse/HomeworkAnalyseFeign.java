package com.yice.edu.cn.osp.feignClient.jy.homeworkAnalyse;

import com.yice.edu.cn.common.pojo.jy.homework.Homework;
import com.yice.edu.cn.common.pojo.jy.homework.HomeworkStudent;
import com.yice.edu.cn.common.pojo.jy.topics.TopicsRecord;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="jy",contextId = "homeworkAnalyseFeign",path = "/homeworkAnalyse")
public interface HomeworkAnalyseFeign {
    @GetMapping("/findHomeworkAnalyseById/{id}")
    Homework findHomeworkAnalyseById(@PathVariable("id") String id);

    @PostMapping("/findHomeworkAnalyseListByConditionNew")
    List<Homework> findHomeworkAnalyseListByConditionNew(Homework homework);

    @PostMapping("/findHomeworkAnalyseCountByConditionNew")
    long findHomeworkAnalyseCountByConditionNew(Homework homework);

    @PostMapping("/findHomeworkStudentListAnalyseByCondition")
    List<HomeworkStudent> findHomeworkStudentListAnalyseByCondition(HomeworkStudent homeworkStudent);

    @PostMapping("/findTopicsRecordListAnalyseByCondition")
    List<TopicsRecord> findTopicsRecordListAnalyseByCondition(TopicsRecord topicsRecord);

}
