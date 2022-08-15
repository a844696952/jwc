package com.yice.edu.cn.jy.dao.titleQuota;

import com.yice.edu.cn.common.pojo.jy.titleQuota.ResourcePlatform;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface IResourcePlatformDao {
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    List<ResourcePlatform> findResourcePlatformListByCondition(ResourcePlatform resourcePlatform);

    long findResourcePlatformCountByCondition(ResourcePlatform resourcePlatform);

    ResourcePlatform findOneResourcePlatformByCondition(ResourcePlatform resourcePlatform);

    ResourcePlatform findResourcePlatformById(@Param("id") String id);

    void saveResourcePlatform(ResourcePlatform resourcePlatform);

    void updateResourcePlatform(ResourcePlatform resourcePlatform);

    void updateResourcePlatformForAll(ResourcePlatform resourcePlatform);

    void deleteResourcePlatform(@Param("id") String id);

    void deleteResourcePlatformByCondition(ResourcePlatform resourcePlatform);

    void batchSaveResourcePlatform(List<ResourcePlatform> resourcePlatforms);
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
    long findCount(ResourcePlatform resourcePlatform);
}
