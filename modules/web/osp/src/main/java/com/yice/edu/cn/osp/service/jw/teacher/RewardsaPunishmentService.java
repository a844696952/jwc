package com.yice.edu.cn.osp.service.jw.teacher;

import com.yice.edu.cn.common.pojo.jw.teacher.RewardsaPunishment;
import com.yice.edu.cn.osp.feignClient.jw.teacher.RewardsaPunishmentFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RewardsaPunishmentService {
    @Autowired
    private RewardsaPunishmentFeign rewardsaPunishmentFeign;

    public RewardsaPunishment findRewardsaPunishmentById(String id) {
        return rewardsaPunishmentFeign.findRewardsaPunishmentById(id);
    }

    public RewardsaPunishment saveRewardsaPunishment(RewardsaPunishment rewardsaPunishment) {
        return rewardsaPunishmentFeign.saveRewardsaPunishment(rewardsaPunishment);
    }

    public List<RewardsaPunishment> findRewardsaPunishmentListByCondition(RewardsaPunishment rewardsaPunishment) {
        return rewardsaPunishmentFeign.findRewardsaPunishmentListByCondition(rewardsaPunishment);
    }
    public List<RewardsaPunishment> findRewardsaPunishmentListByCondition4Like(RewardsaPunishment rewardsaPunishment) {
        return rewardsaPunishmentFeign.findRewardsaPunishmentListByCondition4Like(rewardsaPunishment);
    }

    public RewardsaPunishment findOneRewardsaPunishmentByCondition(RewardsaPunishment rewardsaPunishment) {
        return rewardsaPunishmentFeign.findOneRewardsaPunishmentByCondition(rewardsaPunishment);
    }

    public long findRewardsaPunishmentCountByCondition(RewardsaPunishment rewardsaPunishment) {
        return rewardsaPunishmentFeign.findRewardsaPunishmentCountByCondition(rewardsaPunishment);
    }
    public long findRewardsaPunishmentCountByCondition4Like(RewardsaPunishment rewardsaPunishment) {
        return rewardsaPunishmentFeign.findRewardsaPunishmentCountByCondition4Like(rewardsaPunishment);
    }

    public void updateRewardsaPunishment(RewardsaPunishment rewardsaPunishment) {
        rewardsaPunishmentFeign.updateRewardsaPunishment(rewardsaPunishment);
    }

    public void deleteRewardsaPunishment(String id) {
        rewardsaPunishmentFeign.deleteRewardsaPunishment(id);
    }

    public void deleteRewardsaPunishmentByCondition(RewardsaPunishment rewardsaPunishment) {
        rewardsaPunishmentFeign.deleteRewardsaPunishmentByCondition(rewardsaPunishment);
    }
}
