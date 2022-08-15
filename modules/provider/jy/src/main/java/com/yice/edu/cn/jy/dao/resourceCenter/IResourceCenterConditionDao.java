package com.yice.edu.cn.jy.dao.resourceCenter;

import java.util.List;

import com.yice.edu.cn.common.pojo.jy.resourceCenter.ResourceCenterCondition;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IResourceCenterConditionDao {
    List<ResourceCenterCondition> findResourceCenterConditionListByCondition(ResourceCenterCondition resourceCenterCondition);

    long findResourceCenterConditionCountByCondition(ResourceCenterCondition resourceCenterCondition);

    ResourceCenterCondition findOneResourceCenterConditionByCondition(ResourceCenterCondition resourceCenterCondition);

    ResourceCenterCondition findResourceCenterConditionById(@Param("id") String id);

    void saveResourceCenterCondition(ResourceCenterCondition resourceCenterCondition);

    void updateResourceCenterCondition(ResourceCenterCondition resourceCenterCondition);

    void deleteResourceCenterCondition(@Param("id") String id);

    void deleteResourceCenterConditionByCondition(ResourceCenterCondition resourceCenterCondition);

    void batchSaveResourceCenterCondition(List<ResourceCenterCondition> resourceCenterConditions);
}
