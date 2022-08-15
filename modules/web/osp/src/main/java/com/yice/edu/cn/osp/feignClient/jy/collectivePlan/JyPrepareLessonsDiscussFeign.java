package com.yice.edu.cn.osp.feignClient.jy.collectivePlan;

import com.yice.edu.cn.common.dto.jy.DiscussAndReply;
import com.yice.edu.cn.common.pojo.jy.collectivePlan.JyPrepareLessonsDiscuss;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value = "jy", contextId = "jyPrepareLessonsDiscussFeign",path = "/jyPrepareLessonsDiscuss")
public interface JyPrepareLessonsDiscussFeign {
    @GetMapping("/findJyPrepareLessonsDiscussById/{id}")
    JyPrepareLessonsDiscuss findJyPrepareLessonsDiscussById(@PathVariable("id") String id);

    @PostMapping("/saveJyPrepareLessonsDiscuss")
    JyPrepareLessonsDiscuss saveJyPrepareLessonsDiscuss(JyPrepareLessonsDiscuss jyPrepareLessonsDiscuss);

    @PostMapping("/findJyPrepareLessonsDiscussListByConditionDto")
    List<DiscussAndReply> findJyPrepareLessonsDiscussListByConditionDto(JyPrepareLessonsDiscuss jyPrepareLessonsDiscuss);

    @PostMapping("/findJyPrepareLessonsDiscussListByCondition")
    List<JyPrepareLessonsDiscuss> findJyPrepareLessonsDiscussListByCondition(JyPrepareLessonsDiscuss jyPrepareLessonsDiscuss);

    @PostMapping("/findOneJyPrepareLessonsDiscussByCondition")
    JyPrepareLessonsDiscuss findOneJyPrepareLessonsDiscussByCondition(JyPrepareLessonsDiscuss jyPrepareLessonsDiscuss);

    @PostMapping("/findJyPrepareLessonsDiscussCountByCondition")
    long findJyPrepareLessonsDiscussCountByCondition(JyPrepareLessonsDiscuss jyPrepareLessonsDiscuss);

    @PostMapping("/updateJyPrepareLessonsDiscuss")
    void updateJyPrepareLessonsDiscuss(JyPrepareLessonsDiscuss jyPrepareLessonsDiscuss);

    @GetMapping("/deleteJyPrepareLessonsDiscuss/{id}")
    void deleteJyPrepareLessonsDiscuss(@PathVariable("id") String id);

    @PostMapping("/deleteJyPrepareLessonsDiscussByCondition")
    void deleteJyPrepareLessonsDiscussByCondition(JyPrepareLessonsDiscuss jyPrepareLessonsDiscuss);
}
