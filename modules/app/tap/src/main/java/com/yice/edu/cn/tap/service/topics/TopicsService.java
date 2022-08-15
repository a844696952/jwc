package com.yice.edu.cn.tap.service.topics;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.qusBankResource.ResourceVo;
import com.yice.edu.cn.common.pojo.jy.source21.model.question.SearchParam;
import com.yice.edu.cn.common.pojo.jy.topics.TopicParam;
import com.yice.edu.cn.common.pojo.jy.topics.Topics;
import com.yice.edu.cn.tap.feignClient.topics.TopicsFeign;
import com.yice.edu.cn.tap.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

import static com.yice.edu.cn.tap.interceptor.LoginInterceptor.myId;
import static com.yice.edu.cn.tap.interceptor.LoginInterceptor.mySchoolId;

@Service
public class TopicsService {
    @Autowired
    private TopicsFeign topicsFeign;
    public List<Topics> findTopicsListByCondition4Muti(Topics topics) {
        return topicsFeign.findTopicsListByCondition4Muti(topics);
    }
    public Long findTopicsCountByCondition4Muti(Topics topics) {
        return topicsFeign.findTopicsCountByCondition4Muti(topics);
    }

    public Topics findTopicsById(String id) {
        return topicsFeign.findTopicsById(id);
    }
    public Topics findTopicDetail(ResourceVo resourceVo) {
        TopicParam topicParam = new TopicParam().setId(resourceVo.getTempId())
                .setSchoolId(mySchoolId())
                .setSource(resourceVo.getPlatform())
                .setTeacherId(myId());
        return topicsFeign.findTopicDetail(topicParam);
    }
    public Long findTopicUseCount(String topicId) {
        TopicParam topicParam = new TopicParam()
                .setSchoolId(mySchoolId())
                .setId(topicId)
                .setTeacherId(myId());
        return topicsFeign.findTopicUseCount(topicParam);
    }
    /**
     * 公告查询接口
     * @param searchParam
     * @return
     */
    public ResponseJson findTopicList(SearchParam searchParam) {
        searchParam.setStage(Integer.parseInt(LoginInterceptor.currentTeacher().getSchool().getTypeId()))
                .setTeacherId(myId()).setSchoolId(mySchoolId());
        return topicsFeign.findTopicList(searchParam);
    }
}
