package com.yice.edu.cn.jy.dao.titleQuota;

import com.yice.edu.cn.common.pojo.jy.titleQuota.PlatformTopichiscoty;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface IPlatformTopichiscotyDao {
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    List<PlatformTopichiscoty> findPlatformTopichiscotyListByCondition(PlatformTopichiscoty platformTopichiscoty);

    long findPlatformTopichiscotyCountByCondition(PlatformTopichiscoty platformTopichiscoty);

    PlatformTopichiscoty findOnePlatformTopichiscotyByCondition(PlatformTopichiscoty platformTopichiscoty);

    PlatformTopichiscoty findPlatformTopichiscotyById(@Param("id") String id);

    void savePlatformTopichiscoty(PlatformTopichiscoty platformTopichiscoty);

    void updatePlatformTopichiscoty(PlatformTopichiscoty platformTopichiscoty);

    void updatePlatformTopichiscotyForAll(PlatformTopichiscoty platformTopichiscoty);

    void deletePlatformTopichiscoty(@Param("id") String id);

    void deletePlatformTopichiscotyByCondition(PlatformTopichiscoty platformTopichiscoty);

    void batchSavePlatformTopichiscoty(List<PlatformTopichiscoty> platformTopichiscotys);
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
