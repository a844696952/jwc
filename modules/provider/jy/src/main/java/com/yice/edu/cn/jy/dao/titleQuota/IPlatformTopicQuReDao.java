package com.yice.edu.cn.jy.dao.titleQuota;

import com.yice.edu.cn.common.pojo.jy.titleQuota.PlatformTopicQuRe;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface IPlatformTopicQuReDao {
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    List<PlatformTopicQuRe> findPlatformTopicQuReListByCondition(PlatformTopicQuRe platformTopicQuRe);

    long findPlatformTopicQuReCountByCondition(PlatformTopicQuRe platformTopicQuRe);

    PlatformTopicQuRe findOnePlatformTopicQuReByCondition(PlatformTopicQuRe platformTopicQuRe);

    PlatformTopicQuRe findPlatformTopicQuReById(@Param("id") String id);

    void savePlatformTopicQuRe(PlatformTopicQuRe platformTopicQuRe);

    void updatePlatformTopicQuRe(PlatformTopicQuRe platformTopicQuRe);

    void updatePlatformTopicQuReForAll(PlatformTopicQuRe platformTopicQuRe);

    void deletePlatformTopicQuRe(@Param("id") String id);

    void deletePlatformTopicQuReByCondition(PlatformTopicQuRe platformTopicQuRe);

    void batchSavePlatformTopicQuRe(List<PlatformTopicQuRe> platformTopicQuRes);
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
