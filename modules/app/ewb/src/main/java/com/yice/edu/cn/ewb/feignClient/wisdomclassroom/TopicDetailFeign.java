package com.yice.edu.cn.ewb.feignClient.wisdomclassroom;

import com.yice.edu.cn.common.pojo.jy.wisdomclassroom.StudentInfo;
import com.yice.edu.cn.common.pojo.jy.wisdomclassroom.TopicDetail;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value="jy",contextId = "topicDetailFeign",path = "/topicDetail")
public interface TopicDetailFeign {

    @PostMapping("/findHomeworkListByCondition")
    List<TopicDetail> findTopicDetailListByCondition(TopicDetail topicDetail);
    @GetMapping("/findStudentAnswerListBySidAndCid")
    List<StudentInfo> findStudentAnswerListBySidAndCid(@RequestParam(value = "studentId") String studentId, @RequestParam(value = "classTestId") String classTestId);
}
