package com.yice.edu.cn.jy.dao.resourceCenter;

import java.util.List;

import com.yice.edu.cn.common.pojo.jy.resourceCenter.ResourceCenterType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IResourceCenterTypeDao {
    List<ResourceCenterType> findResourceCenterTypeListByCondition(ResourceCenterType resourceCenterType);

    long findResourceCenterTypeCountByCondition(ResourceCenterType resourceCenterType);

    ResourceCenterType findOneResourceCenterTypeByCondition(ResourceCenterType resourceCenterType);

    ResourceCenterType findResourceCenterTypeById(@Param("id") String id);

    void saveResourceCenterType(ResourceCenterType resourceCenterType);

    void updateResourceCenterType(ResourceCenterType resourceCenterType);

    void deleteResourceCenterType(@Param("id") String id);

    void deleteResourceCenterTypeByCondition(ResourceCenterType resourceCenterType);

    void batchSaveResourceCenterType(List<ResourceCenterType> resourceCenterTypes);
}
