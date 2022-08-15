package com.yice.edu.cn.jy.service.collectivePlan;

import cn.hutool.core.date.DateUtil;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.jy.collectivePlan.CollectivePlan;
import com.yice.edu.cn.common.pojo.jy.collectivePlan.JyPrepareLessonsDiscuss;
import com.yice.edu.cn.common.pojo.jy.collectivePlan.JyPrepareLessonsDiscussReply;
import com.yice.edu.cn.jy.dao.collectivePlan.ICollectivePlanDao;
import com.yice.edu.cn.jy.dao.collectivePlan.IJyPrepareLessonsDiscussDao;
import com.yice.edu.cn.jy.dao.collectivePlan.IJyPrepareLessonsDiscussReplyDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class CollectivePlanService {
    @Autowired
    private ICollectivePlanDao collectivePlanDao;
    @Autowired
    private SequenceId sequenceId;
    @Autowired
    private IJyPrepareLessonsDiscussDao jyPrepareLessonsDiscussDao;
    @Autowired
    private IJyPrepareLessonsDiscussReplyDao iJyPrepareLessonsDiscussReplyDao;

    @Transactional(readOnly = true)
    public CollectivePlan findCollectivePlanById(String id) {
        return collectivePlanDao.findCollectivePlanById(id);
    }

    @Transactional
    public void  saveCollectivePlan(CollectivePlan collectivePlan) {
        collectivePlan.setId(sequenceId.nextId());
        collectivePlanDao.saveCollectivePlan(collectivePlan);
    }

    @Transactional(readOnly = true)
    public List<CollectivePlan> findCollectivePlanListByCondition(CollectivePlan collectivePlan) {
        return collectivePlanDao.findCollectivePlanListByCondition(collectivePlan);
    }

    @Transactional(readOnly = true)
    public CollectivePlan findOneCollectivePlanByCondition(CollectivePlan collectivePlan) {
        return collectivePlanDao.findOneCollectivePlanByCondition(collectivePlan);
    }

    @Transactional(readOnly = true)
    public long findCollectivePlanCountByCondition(CollectivePlan collectivePlan) {
        return collectivePlanDao.findCollectivePlanCountByCondition(collectivePlan);
    }

    @Transactional
    public void updateCollectivePlan(CollectivePlan collectivePlan) {
        collectivePlan.setUpdateTime(DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        collectivePlanDao.updateCollectivePlan(collectivePlan);
    }

    @Transactional
    public void deleteCollectivePlan(String id) {
        collectivePlanDao.deleteCollectivePlan(id);
        //删除讨论组下的评论
        JyPrepareLessonsDiscuss jyPrepareLessonsDiscuss = new JyPrepareLessonsDiscuss();
        jyPrepareLessonsDiscuss.setUsenetId(id);
        jyPrepareLessonsDiscussDao.deleteJyPrepareLessonsDiscussByCondition(jyPrepareLessonsDiscuss);
        //删除讨论组下的回复
        JyPrepareLessonsDiscussReply jyPrepareLessonsDiscussReply = new JyPrepareLessonsDiscussReply();
        jyPrepareLessonsDiscussReply.setUsenetId(id);
        iJyPrepareLessonsDiscussReplyDao.deleteJyPrepareLessonsDiscussReplyByCondition(jyPrepareLessonsDiscussReply);
    }

    @Transactional
    public void deleteLessonsFileById(String id) {
        collectivePlanDao.deleteLessonsFileById(id);
    }

    @Transactional
    public void deleteTeamTeachingPlanById(String id) {
        collectivePlanDao.deleteTeamTeachingPlanById(id);
    }

    @Transactional
    public void deleteCollectivePlanByCondition(CollectivePlan collectivePlan) {
        collectivePlanDao.deleteCollectivePlanByCondition(collectivePlan);
    }

    @Transactional
    public void batchSaveCollectivePlan(List<CollectivePlan> collectivePlans) {
        collectivePlans.forEach(collectivePlan -> collectivePlan.setId(sequenceId.nextId()));
        collectivePlanDao.batchSaveCollectivePlan(collectivePlans);
    }

    //判断同一学年下的讨论组名称是否重名
    @Transactional(readOnly = true)
    public List<CollectivePlan> findCollectivePlanByPlanName(CollectivePlan collectivePlan) {
        return collectivePlanDao.findCollectivePlanByPlanName(collectivePlan);
    }

    //查询我创建的备课组
    @Transactional(readOnly = true)
    public List<CollectivePlan> findCollectivePlanList(CollectivePlan collectivePlan) {
        return collectivePlanDao.findCollectivePlanList(collectivePlan);
    }

    //查询集体备课首用讨论组
    @Transactional(readOnly = true)
    public List<CollectivePlan> findCollectivePlanListByTeacherId(CollectivePlan collectivePlan) {
        return collectivePlanDao.findCollectivePlanListByTeacherId(collectivePlan);
    }

    //查询集体备课首用讨论组
    @Transactional(readOnly = true)
    public List<CollectivePlan> findSchoolYear(CollectivePlan collectivePlan) {
        return collectivePlanDao.findSchoolYear(collectivePlan);
    }
}
