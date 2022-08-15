package com.yice.edu.cn.jy.dao.collectivePlan;

import java.util.List;

import com.yice.edu.cn.common.pojo.jy.collectivePlan.CollectivePlan;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ICollectivePlanDao {
    List<CollectivePlan> findCollectivePlanListByCondition(CollectivePlan collectivePlan);

    CollectivePlan findOneCollectivePlanByCondition(CollectivePlan collectivePlan);

    long findCollectivePlanCountByCondition(CollectivePlan collectivePlan);

    CollectivePlan findCollectivePlanById(@Param("id") String id);

    void saveCollectivePlan(CollectivePlan collectivePlan);

    void updateCollectivePlan(CollectivePlan collectivePlan);

    void deleteCollectivePlan(@Param("id") String id);
    // 根据讨论组id 删除资源文件
    void deleteLessonsFileById(@Param("id") String id);
    // 根据讨论组id 删除集体教案
    void deleteTeamTeachingPlanById(@Param("id") String id);

    void deleteCollectivePlanByCondition(CollectivePlan collectivePlan);

    void batchSaveCollectivePlan(List<CollectivePlan> collectivePlans);
    //判断同一学年下的讨论组名称是否重名
    List<CollectivePlan> findCollectivePlanByPlanName(CollectivePlan collectivePlan);
    //查询我创建的备课组
    List<CollectivePlan> findCollectivePlanList(CollectivePlan collectivePlan);
    //查询集体备课首用讨论组
    List<CollectivePlan> findCollectivePlanListByTeacherId(CollectivePlan collectivePlan);
    //查询学年
    List<CollectivePlan> findSchoolYear(CollectivePlan collectivePlan);
}
