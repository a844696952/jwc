package com.yice.edu.cn.jy.dao.resourceCenter;

import java.util.List;

import com.yice.edu.cn.common.pojo.jy.resourceCenter.ResourceCenterTypeCondition;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IResourceCenterTypeConditionDao {
    List<ResourceCenterTypeCondition> findResourceCenterTypeConditionListByCondition(ResourceCenterTypeCondition resourceCenterTypeCondition);

    long findResourceCenterTypeConditionCountByCondition(ResourceCenterTypeCondition resourceCenterTypeCondition);

    ResourceCenterTypeCondition findOneResourceCenterTypeConditionByCondition(ResourceCenterTypeCondition resourceCenterTypeCondition);

    ResourceCenterTypeCondition findResourceCenterTypeConditionById(@Param("id") String id);

    void saveResourceCenterTypeCondition(ResourceCenterTypeCondition resourceCenterTypeCondition);

    void updateResourceCenterTypeCondition(ResourceCenterTypeCondition resourceCenterTypeCondition);

    void deleteResourceCenterTypeCondition(@Param("id") String id);

    void deleteResourceCenterTypeConditionByCondition(ResourceCenterTypeCondition resourceCenterTypeCondition);

    void batchSaveResourceCenterTypeCondition(List<ResourceCenterTypeCondition> resourceCenterTypeConditions);
}
