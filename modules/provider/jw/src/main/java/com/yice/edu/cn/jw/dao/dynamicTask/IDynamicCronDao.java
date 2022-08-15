package com.yice.edu.cn.jw.dao.dynamicTask;

import java.util.List;

import com.yice.edu.cn.common.pojo.ts.dynamicCron.DynamicCron;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IDynamicCronDao {
    List<DynamicCron> findDynamicCronListByCondition(DynamicCron dynamicCron);

    long findDynamicCronCountByCondition(DynamicCron dynamicCron);

    DynamicCron findOneDynamicCronByCondition(DynamicCron dynamicCron);

    DynamicCron findDynamicCronById(@Param("id") String id);

    void saveDynamicCron(DynamicCron dynamicCron);

    void updateDynamicCron(DynamicCron dynamicCron);

    void deleteDynamicCron(@Param("id") String id);

    void deleteDynamicCronByCondition(DynamicCron dynamicCron);

    void batchSaveDynamicCron(List<DynamicCron> dynamicCrons);
}
