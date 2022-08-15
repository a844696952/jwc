package com.yice.edu.cn.osp.feignClient.jw.qusBankResource;

import com.yice.edu.cn.common.pojo.jw.qusBankResource.PersonalTopics;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="jw",contextId = "personalTopicsFeign",path = "/personalTopics")
public interface PersonalTopicsFeign {
    @GetMapping("/find/findPersonalTopicsById/{id}")
    PersonalTopics findPersonalTopicsById(@PathVariable("id") String id);
    @PostMapping("/savePersonalTopics")
    PersonalTopics savePersonalTopics(PersonalTopics personalTopics);
    @PostMapping("/find/findPersonalTopicsListByCondition")
    List<PersonalTopics> findPersonalTopicsListByCondition(PersonalTopics personalTopics);
    @PostMapping("/find/findOnePersonalTopicsByCondition")
    PersonalTopics findOnePersonalTopicsByCondition(PersonalTopics personalTopics);
    @PostMapping("/find/findPersonalTopicsCountByCondition")
    long findPersonalTopicsCountByCondition(PersonalTopics personalTopics);
    @PostMapping("/updatePersonalTopics")
    void updatePersonalTopics(PersonalTopics personalTopics);
    @GetMapping("/deletePersonalTopics/{id}")
    void deletePersonalTopics(@PathVariable("id") String id);
    @PostMapping("/deletePersonalTopicsByCondition")
    void deletePersonalTopicsByCondition(PersonalTopics personalTopics);
    @PostMapping("/copyTopicToPersonalTopics")
    void copyTopicToPersonalTopics(PersonalTopics personalTopics);
}
