package com.yice.edu.cn.jw.dao.teacher;

import java.util.List;

import com.yice.edu.cn.common.pojo.jw.teacher.RewardsaPunishment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IRewardsaPunishmentDao {
    List<RewardsaPunishment> findRewardsaPunishmentListByCondition(RewardsaPunishment rewardsaPunishment);

    List<RewardsaPunishment> findRewardsaPunishmentListByCondition4Like(RewardsaPunishment rewardsaPunishment);

    RewardsaPunishment findOneRewardsaPunishmentByCondition(RewardsaPunishment rewardsaPunishment);

    long findRewardsaPunishmentCountByCondition(RewardsaPunishment rewardsaPunishment);

    long findRewardsaPunishmentCountByCondition4Like(RewardsaPunishment rewardsaPunishment);

    RewardsaPunishment findRewardsaPunishmentById(@Param("id") String id);

    void saveRewardsaPunishment(RewardsaPunishment rewardsaPunishment);

    void updateRewardsaPunishment(RewardsaPunishment rewardsaPunishment);

    void deleteRewardsaPunishment(@Param("id") String id);

    void deleteRewardsaPunishmentByCondition(RewardsaPunishment rewardsaPunishment);

    void batchSaveRewardsaPunishment(List<RewardsaPunishment> rewardsaPunishments);
}
