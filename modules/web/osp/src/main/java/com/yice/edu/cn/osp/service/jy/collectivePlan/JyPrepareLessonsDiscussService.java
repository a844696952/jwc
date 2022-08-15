package com.yice.edu.cn.osp.service.jy.collectivePlan;

import com.yice.edu.cn.common.dto.jy.DiscussAndReply;
import com.yice.edu.cn.common.pojo.jy.collectivePlan.JyPrepareLessonsDiscuss;
import com.yice.edu.cn.osp.feignClient.jy.collectivePlan.JyPrepareLessonsDiscussFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JyPrepareLessonsDiscussService {
    @Autowired
    private JyPrepareLessonsDiscussFeign jyPrepareLessonsDiscussFeign;

    public JyPrepareLessonsDiscuss findJyPrepareLessonsDiscussById(String id) {
        return jyPrepareLessonsDiscussFeign.findJyPrepareLessonsDiscussById(id);
    }

    public JyPrepareLessonsDiscuss saveJyPrepareLessonsDiscuss(JyPrepareLessonsDiscuss jyPrepareLessonsDiscuss) {
        return jyPrepareLessonsDiscussFeign.saveJyPrepareLessonsDiscuss(jyPrepareLessonsDiscuss);
    }

    public List<DiscussAndReply> findJyPrepareLessonsDiscussListByConditionDto(JyPrepareLessonsDiscuss jyPrepareLessonsDiscuss) {
        return jyPrepareLessonsDiscussFeign.findJyPrepareLessonsDiscussListByConditionDto(jyPrepareLessonsDiscuss);
    }

    public List<JyPrepareLessonsDiscuss> findJyPrepareLessonsDiscussListByCondition(JyPrepareLessonsDiscuss jyPrepareLessonsDiscuss) {
        return jyPrepareLessonsDiscussFeign.findJyPrepareLessonsDiscussListByCondition(jyPrepareLessonsDiscuss);
    }

    public JyPrepareLessonsDiscuss findOneJyPrepareLessonsDiscussByCondition(JyPrepareLessonsDiscuss jyPrepareLessonsDiscuss) {
        return jyPrepareLessonsDiscussFeign.findOneJyPrepareLessonsDiscussByCondition(jyPrepareLessonsDiscuss);
    }

    public long findJyPrepareLessonsDiscussCountByCondition(JyPrepareLessonsDiscuss jyPrepareLessonsDiscuss) {
        return jyPrepareLessonsDiscussFeign.findJyPrepareLessonsDiscussCountByCondition(jyPrepareLessonsDiscuss);
    }

    public void updateJyPrepareLessonsDiscuss(JyPrepareLessonsDiscuss jyPrepareLessonsDiscuss) {
        jyPrepareLessonsDiscussFeign.updateJyPrepareLessonsDiscuss(jyPrepareLessonsDiscuss);
    }

    public void deleteJyPrepareLessonsDiscuss(String id) {
        jyPrepareLessonsDiscussFeign.deleteJyPrepareLessonsDiscuss(id);
    }

    public void deleteJyPrepareLessonsDiscussByCondition(JyPrepareLessonsDiscuss jyPrepareLessonsDiscuss) {
        jyPrepareLessonsDiscussFeign.deleteJyPrepareLessonsDiscussByCondition(jyPrepareLessonsDiscuss);
    }
}
