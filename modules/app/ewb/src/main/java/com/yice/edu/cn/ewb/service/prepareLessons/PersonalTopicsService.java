package com.yice.edu.cn.ewb.service.prepareLessons;

import com.yice.edu.cn.common.pojo.jw.qusBankResource.PersonalTopics;
import com.yice.edu.cn.common.pojo.jw.qusBankResource.ResourceVo;
import com.yice.edu.cn.common.pojo.jy.topics.TopicParam;
import com.yice.edu.cn.ewb.feignClient.prepareLessons.PersonalTopicsFeign;
import com.yice.edu.cn.ewb.feignClient.topics.TopicsFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.yice.edu.cn.ewb.interceptor.LoginInterceptor.myId;
import static com.yice.edu.cn.ewb.interceptor.LoginInterceptor.mySchoolId;

@Service
public class PersonalTopicsService {

    @Autowired
    private PersonalTopicsFeign personalTopicsFeign;

    @Autowired
    private TopicsFeign topicsFeign;

    public List<PersonalTopics> findPersonalTopicsListByCondition(PersonalTopics personalTopics) {
        return personalTopicsFeign.findPersonalTopicsListByCondition(personalTopics);
    }

    public long findPersonalTopicsCountByCondition(PersonalTopics personalTopics) {
        return personalTopicsFeign.findPersonalTopicsCountByCondition(personalTopics);
    }


    public Object findTopicDetail(ResourceVo resourceVo) {
        TopicParam topicParam = new TopicParam().setId(resourceVo.getTempId())
                .setSchoolId(mySchoolId())
                .setSource(resourceVo.getPlatform())
                .setTeacherId(myId());
        return topicsFeign.findTopicDetail(topicParam);
    }
}
