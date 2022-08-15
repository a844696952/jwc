package com.yice.edu.cn.jy.dao.prepareLessons;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.yice.edu.cn.common.pojo.jy.prepareLessons.TeachingPlan;

@Mapper
public interface ITeachingPlanDao {
	
	List<TeachingPlan> findTeachingPlanListByCondition(TeachingPlan teachingPlan);

    long findTeachingPlanCountByCondition(TeachingPlan teachingPlan);

    TeachingPlan findTeachingPlanById(@Param("id") String id);

    int saveTeachingPlan(TeachingPlan teachingPlan);

    int updateTeachingPlan(TeachingPlan teachingPlan);

    int deleteTeachingPlan(@Param("id") String id);
    
    int updateViewCount(@Param("id") String id);
    
    int updateDownloadCount(@Param("id") String id);


}
