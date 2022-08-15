package com.yice.edu.cn.jy.service.collectivePlan;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.dto.jy.DiscussAndReply;
import com.yice.edu.cn.common.pojo.jy.collectivePlan.JyPrepareLessonsDiscuss;
import com.yice.edu.cn.common.pojo.jy.collectivePlan.TeacherCollection;
import com.yice.edu.cn.jy.dao.collectivePlan.IJyPrepareLessonsDiscussDao;
import com.yice.edu.cn.jy.dao.collectivePlan.IJyPrepareLessonsDiscussReplyDao;
import com.yice.edu.cn.jy.dao.collectivePlan.ITeacherCollectionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class JyPrepareLessonsDiscussService {
    @Autowired
    private IJyPrepareLessonsDiscussDao jyPrepareLessonsDiscussDao;
    @Autowired
    private ITeacherCollectionDao iTeacherCollectionDao;
    @Autowired
    private SequenceId sequenceId;
    @Autowired
    private IJyPrepareLessonsDiscussReplyDao iJyPrepareLessonsDiscussReplyDao;

    @Transactional(readOnly = true)
    public JyPrepareLessonsDiscuss findJyPrepareLessonsDiscussById(String id) {
        return jyPrepareLessonsDiscussDao.findJyPrepareLessonsDiscussById(id);
    }

    @Transactional
    public void saveJyPrepareLessonsDiscuss(JyPrepareLessonsDiscuss jyPrepareLessonsDiscuss) {
        jyPrepareLessonsDiscuss.setId(sequenceId.nextId());
        jyPrepareLessonsDiscussDao.saveJyPrepareLessonsDiscuss(jyPrepareLessonsDiscuss);
        //评论完成后 在 教案表中将评论次数+1
        TeacherCollection teacherCollection = new TeacherCollection();
        teacherCollection.setCollectionPlanId(jyPrepareLessonsDiscuss.getUsenetId());
        teacherCollection.setTeacherPlanId(jyPrepareLessonsDiscuss.getTeachingPlanId());
        iTeacherCollectionDao.updateCommentCount(teacherCollection);
    }

    @Transactional(readOnly = true)
    public List<DiscussAndReply> findJyPrepareLessonsDiscussListByConditionDto(JyPrepareLessonsDiscuss jyPrepareLessonsDiscuss) {
        List<JyPrepareLessonsDiscuss> discussList = jyPrepareLessonsDiscussDao.findJyPrepareLessonsDiscussListByCondition(jyPrepareLessonsDiscuss);
        //放入回复信息
        List<DiscussAndReply> collect = discussList.stream().map(discuss -> {
            DiscussAndReply discussAndReply = new DiscussAndReply();
            discussAndReply.setSign(DiscussAndReply.SIGN_FALSE);
            discussAndReply.setJyPrepareLessonsDiscuss(discuss);
            discussAndReply.setList(iJyPrepareLessonsDiscussReplyDao.findJyPrepareLessonsDiscussReplyByDiscussId(discuss.getId()));
            return discussAndReply;
        }).collect(Collectors.toList());

        return collect;

    }

    @Transactional(readOnly = true)
    public List<JyPrepareLessonsDiscuss> findJyPrepareLessonsDiscussListByCondition(JyPrepareLessonsDiscuss jyPrepareLessonsDiscuss) {
        return jyPrepareLessonsDiscussDao.findJyPrepareLessonsDiscussListByCondition(jyPrepareLessonsDiscuss);
    }

    @Transactional(readOnly = true)
    public JyPrepareLessonsDiscuss findOneJyPrepareLessonsDiscussByCondition(JyPrepareLessonsDiscuss jyPrepareLessonsDiscuss) {
        return jyPrepareLessonsDiscussDao.findOneJyPrepareLessonsDiscussByCondition(jyPrepareLessonsDiscuss);
    }

    @Transactional(readOnly = true)
    public long findJyPrepareLessonsDiscussCountByCondition(JyPrepareLessonsDiscuss jyPrepareLessonsDiscuss) {
        return jyPrepareLessonsDiscussDao.findJyPrepareLessonsDiscussCountByCondition(jyPrepareLessonsDiscuss);
    }

    @Transactional
    public void updateJyPrepareLessonsDiscuss(JyPrepareLessonsDiscuss jyPrepareLessonsDiscuss) {
        jyPrepareLessonsDiscussDao.updateJyPrepareLessonsDiscuss(jyPrepareLessonsDiscuss);
    }

    @Transactional
    public void deleteJyPrepareLessonsDiscuss(String id) {
        //删除评论对应的教案评论次数-1
        JyPrepareLessonsDiscuss discuss = jyPrepareLessonsDiscussDao.findJyPrepareLessonsDiscussById(id);
        TeacherCollection teacherCollection = new TeacherCollection();
        teacherCollection.setCollectionPlanId(discuss.getUsenetId());
        teacherCollection.setTeacherPlanId(discuss.getTeachingPlanId());
        TeacherCollection collection = iTeacherCollectionDao.findOneTeacherCollectionByCondition(teacherCollection);
        if (collection.getCommentCount() > 0) {
            iTeacherCollectionDao.updateCommentCountReduce(teacherCollection);
        }
        //删除数据
        jyPrepareLessonsDiscussDao.deleteJyPrepareLessonsDiscuss(id);
        iJyPrepareLessonsDiscussReplyDao.deleteJyPrepareLessonsDiscussReplyByDiscussId(id);
    }

    @Transactional
    public void deleteJyPrepareLessonsDiscussByCondition(JyPrepareLessonsDiscuss jyPrepareLessonsDiscuss) {
        jyPrepareLessonsDiscussDao.deleteJyPrepareLessonsDiscussByCondition(jyPrepareLessonsDiscuss);
    }

    @Transactional
    public void batchSaveJyPrepareLessonsDiscuss(List<JyPrepareLessonsDiscuss> jyPrepareLessonsDiscusss) {
        jyPrepareLessonsDiscusss.forEach(jyPrepareLessonsDiscuss -> jyPrepareLessonsDiscuss.setId(sequenceId.nextId()));
        jyPrepareLessonsDiscussDao.batchSaveJyPrepareLessonsDiscuss(jyPrepareLessonsDiscusss);
    }

}
