package com.yice.edu.cn.osp.service.jy.collectivePlan;

import com.yice.edu.cn.common.pojo.jy.collectivePlan.JyPrepareLessonsDiscussReply;
import com.yice.edu.cn.osp.feignClient.jy.collectivePlan.JyPrepareLessonsDiscussReplyFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JyPrepareLessonsDiscussReplyService {
    @Autowired
    private JyPrepareLessonsDiscussReplyFeign jyPrepareLessonsDiscussReplyFeign;

    public JyPrepareLessonsDiscussReply findJyPrepareLessonsDiscussReplyById(String id) {
        return jyPrepareLessonsDiscussReplyFeign.findJyPrepareLessonsDiscussReplyById(id);
    }

    public JyPrepareLessonsDiscussReply saveJyPrepareLessonsDiscussReply(JyPrepareLessonsDiscussReply jyPrepareLessonsDiscussReply) {
        return jyPrepareLessonsDiscussReplyFeign.saveJyPrepareLessonsDiscussReply(jyPrepareLessonsDiscussReply);
    }

    public List<JyPrepareLessonsDiscussReply> findJyPrepareLessonsDiscussReplyListByCondition(JyPrepareLessonsDiscussReply jyPrepareLessonsDiscussReply) {
        return jyPrepareLessonsDiscussReplyFeign.findJyPrepareLessonsDiscussReplyListByCondition(jyPrepareLessonsDiscussReply);
    }

    public JyPrepareLessonsDiscussReply findOneJyPrepareLessonsDiscussReplyByCondition(JyPrepareLessonsDiscussReply jyPrepareLessonsDiscussReply) {
        return jyPrepareLessonsDiscussReplyFeign.findOneJyPrepareLessonsDiscussReplyByCondition(jyPrepareLessonsDiscussReply);
    }

    public long findJyPrepareLessonsDiscussReplyCountByCondition(JyPrepareLessonsDiscussReply jyPrepareLessonsDiscussReply) {
        return jyPrepareLessonsDiscussReplyFeign.findJyPrepareLessonsDiscussReplyCountByCondition(jyPrepareLessonsDiscussReply);
    }

    public void updateJyPrepareLessonsDiscussReply(JyPrepareLessonsDiscussReply jyPrepareLessonsDiscussReply) {
        jyPrepareLessonsDiscussReplyFeign.updateJyPrepareLessonsDiscussReply(jyPrepareLessonsDiscussReply);
    }

    public void deleteJyPrepareLessonsDiscussReply(String id) {
        jyPrepareLessonsDiscussReplyFeign.deleteJyPrepareLessonsDiscussReply(id);
    }

    public void deleteJyPrepareLessonsDiscussReplyByCondition(JyPrepareLessonsDiscussReply jyPrepareLessonsDiscussReply) {
        jyPrepareLessonsDiscussReplyFeign.deleteJyPrepareLessonsDiscussReplyByCondition(jyPrepareLessonsDiscussReply);
    }

}
