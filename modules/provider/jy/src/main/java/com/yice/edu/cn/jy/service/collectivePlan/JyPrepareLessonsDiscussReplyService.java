package com.yice.edu.cn.jy.service.collectivePlan;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.jy.collectivePlan.JyPrepareLessonsDiscussReply;
import com.yice.edu.cn.jy.dao.collectivePlan.IJyPrepareLessonsDiscussReplyDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class JyPrepareLessonsDiscussReplyService {
    @Autowired
    private IJyPrepareLessonsDiscussReplyDao jyPrepareLessonsDiscussReplyDao;
    @Autowired
    private SequenceId sequenceId;

    @Transactional(readOnly = true)
    public JyPrepareLessonsDiscussReply findJyPrepareLessonsDiscussReplyById(String id) {
        return jyPrepareLessonsDiscussReplyDao.findJyPrepareLessonsDiscussReplyById(id);
    }

    @Transactional
    public void saveJyPrepareLessonsDiscussReply(JyPrepareLessonsDiscussReply jyPrepareLessonsDiscussReply) {
        jyPrepareLessonsDiscussReply.setId(sequenceId.nextId());
        jyPrepareLessonsDiscussReplyDao.saveJyPrepareLessonsDiscussReply(jyPrepareLessonsDiscussReply);
    }

    @Transactional(readOnly = true)
    public List<JyPrepareLessonsDiscussReply> findJyPrepareLessonsDiscussReplyListByCondition(JyPrepareLessonsDiscussReply jyPrepareLessonsDiscussReply) {
        return jyPrepareLessonsDiscussReplyDao.findJyPrepareLessonsDiscussReplyListByCondition(jyPrepareLessonsDiscussReply);
    }

    @Transactional(readOnly = true)
    public JyPrepareLessonsDiscussReply findOneJyPrepareLessonsDiscussReplyByCondition(JyPrepareLessonsDiscussReply jyPrepareLessonsDiscussReply) {
        return jyPrepareLessonsDiscussReplyDao.findOneJyPrepareLessonsDiscussReplyByCondition(jyPrepareLessonsDiscussReply);
    }

    @Transactional(readOnly = true)
    public long findJyPrepareLessonsDiscussReplyCountByCondition(JyPrepareLessonsDiscussReply jyPrepareLessonsDiscussReply) {
        return jyPrepareLessonsDiscussReplyDao.findJyPrepareLessonsDiscussReplyCountByCondition(jyPrepareLessonsDiscussReply);
    }

    @Transactional
    public void updateJyPrepareLessonsDiscussReply(JyPrepareLessonsDiscussReply jyPrepareLessonsDiscussReply) {
        jyPrepareLessonsDiscussReplyDao.updateJyPrepareLessonsDiscussReply(jyPrepareLessonsDiscussReply);
    }

    @Transactional
    public void deleteJyPrepareLessonsDiscussReply(String id) {
        jyPrepareLessonsDiscussReplyDao.deleteJyPrepareLessonsDiscussReply(id);
    }

    @Transactional
    public void deleteJyPrepareLessonsDiscussReplyByCondition(JyPrepareLessonsDiscussReply jyPrepareLessonsDiscussReply) {
        jyPrepareLessonsDiscussReplyDao.deleteJyPrepareLessonsDiscussReplyByCondition(jyPrepareLessonsDiscussReply);
    }

    @Transactional
    public void batchSaveJyPrepareLessonsDiscussReply(List<JyPrepareLessonsDiscussReply> jyPrepareLessonsDiscussReplys) {
        jyPrepareLessonsDiscussReplys.forEach(jyPrepareLessonsDiscussReply -> jyPrepareLessonsDiscussReply.setId(sequenceId.nextId()));
        jyPrepareLessonsDiscussReplyDao.batchSaveJyPrepareLessonsDiscussReply(jyPrepareLessonsDiscussReplys);
    }

}
