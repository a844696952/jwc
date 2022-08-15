package com.yice.edu.cn.osp.service.jw.qusBankResouece;

import com.yice.edu.cn.common.pojo.jw.qusBankResource.PersonalTopics;
import com.yice.edu.cn.osp.feignClient.jw.qusBankResource.PersonalTopicsFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonalTopicsService {
    @Autowired
    private PersonalTopicsFeign personalTopicsFeign;

    public PersonalTopics findPersonalTopicsById(String id) {
        return personalTopicsFeign.findPersonalTopicsById(id);
    }

    public PersonalTopics savePersonalTopics(PersonalTopics personalTopics) {
        return personalTopicsFeign.savePersonalTopics(personalTopics);
    }

    public List<PersonalTopics> findPersonalTopicsListByCondition(PersonalTopics personalTopics) {
        return personalTopicsFeign.findPersonalTopicsListByCondition(personalTopics);
    }

    public PersonalTopics findOnePersonalTopicsByCondition(PersonalTopics personalTopics) {
        return personalTopicsFeign.findOnePersonalTopicsByCondition(personalTopics);
    }

    public long findPersonalTopicsCountByCondition(PersonalTopics personalTopics) {
        return personalTopicsFeign.findPersonalTopicsCountByCondition(personalTopics);
    }

    public void updatePersonalTopics(PersonalTopics personalTopics) {
        personalTopicsFeign.updatePersonalTopics(personalTopics);
    }

    public void deletePersonalTopics(String id) {
        personalTopicsFeign.deletePersonalTopics(id);
    }

    public void deletePersonalTopicsByCondition(PersonalTopics personalTopics) {
        personalTopicsFeign.deletePersonalTopicsByCondition(personalTopics);
    }
}
