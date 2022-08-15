package com.yice.edu.cn.bmp.feignClient.homework;

import com.yice.edu.cn.common.pojo.jy.homework.Homework;
import com.yice.edu.cn.common.pojo.jy.homework.HomeworkNew;
import com.yice.edu.cn.common.pojo.jy.topics.WrongTopics;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

@FeignClient(value="jy",contextId = "homeworkFeign",path = "/homework")
public interface HomeworkFeign {
    @GetMapping("/findHomeworkById/{id}")
    Homework findHomeworkById(@PathVariable("id") String id);
    @PostMapping("/saveHomework")
    Homework saveHomework(Homework homework);
    @PostMapping("/findHomeworkListByCondition")
    List<Homework> findHomeworkListByCondition(Homework homework);
    @PostMapping("/findHomeworkListByConditionNew")
    List<Homework> findHomeworkListByConditionNew(Homework homework);
    @PostMapping("/findOneHomeworkByCondition")
    Homework findOneHomeworkByCondition(Homework homework);
    @PostMapping("/findHomeworkCountByConditionNew")
    long findHomeworkCountByConditionNew(Homework homework);
    @PostMapping("/findHomeworkCountByCondition")
    long findHomeworkCountByCondition(Homework homework);
    @PostMapping("/updateHomework")
    void updateHomework(Homework homework);
    @PostMapping("/updateHomeworkNew")
    void updateHomeworkNew(Homework homework);
    @GetMapping("/deleteHomework/{id}")
    void deleteHomework(@PathVariable("id") String id);
    @GetMapping("/deleteHomeworkNew/{id}")
    void deleteHomeworkNew(@PathVariable("id") String id);
    @PostMapping("/deleteHomeworkByCondition")
    void deleteHomeworkByCondition(Homework homework);
    @PostMapping("/publishHomework")
    Homework publishHomework(Homework homework);
    @PostMapping("/findHomeworkListByConditionXq")
    List<Map<String, Object>> findHomeworkListByConditionXq(Homework homework);
    @PostMapping("/findHomeworksByConditionDetail")
    List<Map<String, Object>> findHomeworksByConditionDetail(Homework homework);
    @PostMapping("/findKnowlegAllMoreDetailByCondition")
    List<HomeworkNew> findKnowlegAllMoreDetailByCondition(Homework homework);
    @PostMapping("/findCurrentTopicDetail")
    HomeworkNew findCurrentTopicDetail(Homework homework);
    @PostMapping("/findOneError")
    List<Map<String,Object>> findOneError(Homework homework);
    @PostMapping("/findOneStudentDetail")
    List<Map<String,Object>> findOneStudentDetail(Homework homework);
    @PostMapping("/findOneStudentKnoledgeContext")
    List<HomeworkNew> findOneStudentKnoledgeContext(Homework homework);
    @PostMapping("/mistakesCollection")
    List<WrongTopics> mistakesCollection(Homework homework);
    @PostMapping("/mistakesCollectionDetail")
    HomeworkNew mistakesCollectionDetail(Homework homework);
    @PostMapping("/findWrongtopicInfo")
    List<Map<String,Object>> findWrongtopicInfo(Homework homework);
    @PostMapping("/mistakesCollectionCount")
    Integer mistakesCollectionCount(Homework homework);
}
