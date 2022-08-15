package com.yice.edu.cn.osp.feignClient.jw.teacher;

import com.yice.edu.cn.common.pojo.jw.teacher.RewardsaPunishment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="jw",contextId = "rewardsaPunishmentFeign",path = "/rewardsaPunishment")
public interface RewardsaPunishmentFeign {
    @GetMapping("/findRewardsaPunishmentById/{id}")
    RewardsaPunishment findRewardsaPunishmentById(@PathVariable("id") String id);
    @PostMapping("/saveRewardsaPunishment")
    RewardsaPunishment saveRewardsaPunishment(RewardsaPunishment rewardsaPunishment);
    @PostMapping("/findRewardsaPunishmentListByCondition")
    List<RewardsaPunishment> findRewardsaPunishmentListByCondition(RewardsaPunishment rewardsaPunishment);
    @PostMapping("/findRewardsaPunishmentListByCondition4Like")
    List<RewardsaPunishment> findRewardsaPunishmentListByCondition4Like(RewardsaPunishment rewardsaPunishment);
    @PostMapping("/findOneRewardsaPunishmentByCondition")
    RewardsaPunishment findOneRewardsaPunishmentByCondition(RewardsaPunishment rewardsaPunishment);
    @PostMapping("/findRewardsaPunishmentCountByCondition")
    long findRewardsaPunishmentCountByCondition(RewardsaPunishment rewardsaPunishment);
    @PostMapping("/findRewardsaPunishmentCountByCondition4Like")
    long findRewardsaPunishmentCountByCondition4Like(RewardsaPunishment rewardsaPunishment);
    @PostMapping("/updateRewardsaPunishment")
    void updateRewardsaPunishment(RewardsaPunishment rewardsaPunishment);
    @GetMapping("/deleteRewardsaPunishment/{id}")
    void deleteRewardsaPunishment(@PathVariable("id") String id);
    @PostMapping("/deleteRewardsaPunishmentByCondition")
    void deleteRewardsaPunishmentByCondition(RewardsaPunishment rewardsaPunishment);
}
