package com.yice.edu.cn.jw.service.teacher;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.jw.teacher.RewardsaPunishment;
import com.yice.edu.cn.jw.dao.teacher.IRewardsaPunishmentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RewardsaPunishmentService {
    @Autowired
    private IRewardsaPunishmentDao rewardsaPunishmentDao;
    @Autowired
    private SequenceId sequenceId;

    @Transactional(readOnly = true)
    public RewardsaPunishment findRewardsaPunishmentById(String id) {
        return rewardsaPunishmentDao.findRewardsaPunishmentById(id);
    }
    @Transactional
    public void saveRewardsaPunishment(RewardsaPunishment rewardsaPunishment) {
        rewardsaPunishment.setId(sequenceId.nextId());
        rewardsaPunishmentDao.saveRewardsaPunishment(rewardsaPunishment);
    }
    @Transactional(readOnly = true)
    public List<RewardsaPunishment> findRewardsaPunishmentListByCondition(RewardsaPunishment rewardsaPunishment) {
        return rewardsaPunishmentDao.findRewardsaPunishmentListByCondition(rewardsaPunishment);
    }
    @Transactional(readOnly = true)
    public List<RewardsaPunishment> findRewardsaPunishmentListByCondition4Like(RewardsaPunishment rewardsaPunishment) {
        return rewardsaPunishmentDao.findRewardsaPunishmentListByCondition4Like(rewardsaPunishment);
    }
    @Transactional(readOnly = true)
    public RewardsaPunishment findOneRewardsaPunishmentByCondition(RewardsaPunishment rewardsaPunishment) {
        return rewardsaPunishmentDao.findOneRewardsaPunishmentByCondition(rewardsaPunishment);
    }
    @Transactional(readOnly = true)
    public long findRewardsaPunishmentCountByCondition(RewardsaPunishment rewardsaPunishment) {
        return rewardsaPunishmentDao.findRewardsaPunishmentCountByCondition(rewardsaPunishment);
    }
    @Transactional(readOnly = true)
    public long findRewardsaPunishmentCountByCondition4Like(RewardsaPunishment rewardsaPunishment) {
        return rewardsaPunishmentDao.findRewardsaPunishmentCountByCondition4Like(rewardsaPunishment);
    }
    @Transactional
    public void updateRewardsaPunishment(RewardsaPunishment rewardsaPunishment) {
        rewardsaPunishmentDao.updateRewardsaPunishment(rewardsaPunishment);
    }
    @Transactional
    public void deleteRewardsaPunishment(String id) {
        rewardsaPunishmentDao.deleteRewardsaPunishment(id);
    }
    @Transactional
    public void deleteRewardsaPunishmentByCondition(RewardsaPunishment rewardsaPunishment) {
        rewardsaPunishmentDao.deleteRewardsaPunishmentByCondition(rewardsaPunishment);
    }
    @Transactional
    public void batchSaveRewardsaPunishment(List<RewardsaPunishment> rewardsaPunishments){
        rewardsaPunishments.forEach(rewardsaPunishment -> rewardsaPunishment.setId(sequenceId.nextId()));
        rewardsaPunishmentDao.batchSaveRewardsaPunishment(rewardsaPunishments);
    }

}
