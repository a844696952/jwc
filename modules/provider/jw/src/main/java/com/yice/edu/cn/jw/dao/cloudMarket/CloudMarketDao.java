package com.yice.edu.cn.jw.dao.cloudMarket;

import java.util.List;

import com.yice.edu.cn.common.pojo.jw.CloudMarket.CloudMarket;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CloudMarketDao {
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    List<CloudMarket> findCloudMarketListByCondition(CloudMarket cloudMarket);

    long findCloudMarketCountByCondition(CloudMarket cloudMarket);

    CloudMarket findOneCloudMarketByCondition(CloudMarket cloudMarket);

    CloudMarket findCloudMarketById(@Param("id") String id);

    void saveCloudMarket(CloudMarket cloudMarket);

    void updateCloudMarket(CloudMarket cloudMarket);

    void updateCloudMarketForAll(CloudMarket cloudMarket);

    void deleteCloudMarket(@Param("id") String id);

    void deleteCloudMarketByCondition(CloudMarket cloudMarket);

    void batchSaveCloudMarket(List<CloudMarket> cloudMarkets);
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
