package com.yice.edu.cn.osp.feignClient.jy.collectivePlan;

import com.yice.edu.cn.common.pojo.jy.collectivePlan.JyPrepareLessonsDiscussReply;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value = "jy",contextId = "jyPrepareLessonsDiscussReplyFeign",path = "/jyPrepareLessonsDiscussReply")
public interface JyPrepareLessonsDiscussReplyFeign {
    @GetMapping("/findJyPrepareLessonsDiscussReplyById/{id}")
    JyPrepareLessonsDiscussReply findJyPrepareLessonsDiscussReplyById(@PathVariable("id") String id);

    @PostMapping("/saveJyPrepareLessonsDiscussReply")
    JyPrepareLessonsDiscussReply saveJyPrepareLessonsDiscussReply(JyPrepareLessonsDiscussReply jyPrepareLessonsDiscussReply);

    @PostMapping("/findJyPrepareLessonsDiscussReplyListByCondition")
    List<JyPrepareLessonsDiscussReply> findJyPrepareLessonsDiscussReplyListByCondition(JyPrepareLessonsDiscussReply jyPrepareLessonsDiscussReply);

    @PostMapping("/findOneJyPrepareLessonsDiscussReplyByCondition")
    JyPrepareLessonsDiscussReply findOneJyPrepareLessonsDiscussReplyByCondition(JyPrepareLessonsDiscussReply jyPrepareLessonsDiscussReply);

    @PostMapping("/findJyPrepareLessonsDiscussReplyCountByCondition")
    long findJyPrepareLessonsDiscussReplyCountByCondition(JyPrepareLessonsDiscussReply jyPrepareLessonsDiscussReply);

    @PostMapping("/updateJyPrepareLessonsDiscussReply")
    void updateJyPrepareLessonsDiscussReply(JyPrepareLessonsDiscussReply jyPrepareLessonsDiscussReply);

    @GetMapping("/deleteJyPrepareLessonsDiscussReply/{id}")
    void deleteJyPrepareLessonsDiscussReply(@PathVariable("id") String id);

    @PostMapping("/deleteJyPrepareLessonsDiscussReplyByCondition")
    void deleteJyPrepareLessonsDiscussReplyByCondition(JyPrepareLessonsDiscussReply jyPrepareLessonsDiscussReply);

}
