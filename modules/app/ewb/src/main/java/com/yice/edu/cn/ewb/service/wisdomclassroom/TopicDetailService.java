package com.yice.edu.cn.ewb.service.wisdomclassroom;

import com.yice.edu.cn.common.pojo.jy.wisdomclassroom.StudentInfo;
import com.yice.edu.cn.common.pojo.jy.wisdomclassroom.TopicDetail;
import com.yice.edu.cn.ewb.feignClient.wisdomclassroom.TopicDetailFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopicDetailService {

    @Autowired
    private TopicDetailFeign topicDetailFeign;




    public List<TopicDetail> findTopicDetailListByCondition(TopicDetail topicDetail) {

        return topicDetailFeign.findTopicDetailListByCondition(topicDetail);
    }

    public List<StudentInfo> findStudentAnswerListBySidAndCid(String studentId, String classTestId){

        return topicDetailFeign.findStudentAnswerListBySidAndCid(studentId,classTestId);
    }
}
