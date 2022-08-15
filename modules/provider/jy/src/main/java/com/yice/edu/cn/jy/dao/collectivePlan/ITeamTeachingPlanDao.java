package com.yice.edu.cn.jy.dao.collectivePlan;

import java.util.List;

import com.yice.edu.cn.common.pojo.jy.collectivePlan.TeamTeachingPlan;
import com.yice.edu.cn.common.pojo.jy.prepareLessons.TeachingPlan;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ITeamTeachingPlanDao {

    TeamTeachingPlan findOneTeamTeachingPlanByCondition(TeamTeachingPlan teachingPlan);

    void deleteTeamTeachingPlanByCondition(TeamTeachingPlan teachingPlan);

    void batchSaveTeamTeachingPlan(List<TeamTeachingPlan> teachingPlan);


    /***
     * 复用个人备课中的Dao
     * @param teachingPlan
     * @return
     */
    List<TeamTeachingPlan> findTeamTeachingPlanListByCondition(TeamTeachingPlan teachingPlan);

    long findTeamTeachingPlanCountByCondition(TeamTeachingPlan teachingPlan);

    TeamTeachingPlan findTeamTeachingPlanById(@Param("id") String id);

    TeamTeachingPlan findTeachingPlanById(@Param("id") String id);

    int saveTeamTeachingPlan(TeamTeachingPlan teachingPlan);

    int updateTeamTeachingPlan(TeamTeachingPlan teachingPlan);

    int deleteTeamTeachingPlan(@Param("id") String id);

    int updateTeamViewCount(@Param("id") String id);

    int updateTeamDownloadCount(@Param("id") String id);

    //查询 集体备课讨论组下的 已完成 和 正在讨论的教案
    List<TeamTeachingPlan> findTeachingPlanList(TeamTeachingPlan teachingPlan);

    // 查询教师的个人教案列表（未被选择提交的教案）
    List<TeamTeachingPlan> findTeachingPlanListNotChosen(TeamTeachingPlan teachingPlan);

    // 查询当前教师 是否为主备人
    List<TeamTeachingPlan> findTeachingPlanIsPrincipal(TeamTeachingPlan teachingPlan);
}
