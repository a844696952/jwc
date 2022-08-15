package com.yice.edu.cn.jw.service.cloudMarket;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.jw.CloudMarket.CloudMarket;
import com.yice.edu.cn.jw.dao.cloudMarket.CloudMarketDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CloudMarketService {
    @Autowired
    private CloudMarketDao cloudMarketDao;
    @Autowired
    private SequenceId sequenceId;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @Transactional(readOnly = true)
    public CloudMarket findCloudMarketById(String id) {
        return cloudMarketDao.findCloudMarketById(id);
    }
    @Transactional
    public void saveCloudMarket(CloudMarket cloudMarket) {
        cloudMarket.setId(sequenceId.nextId());
        cloudMarketDao.saveCloudMarket(cloudMarket);
    }
    @Transactional(readOnly = true)
    public List<CloudMarket> findCloudMarketListByCondition(CloudMarket cloudMarket) {
        return cloudMarketDao.findCloudMarketListByCondition(cloudMarket);
    }
    @Transactional(readOnly = true)
    public CloudMarket findOneCloudMarketByCondition(CloudMarket cloudMarket) {
        return cloudMarketDao.findOneCloudMarketByCondition(cloudMarket);
    }
    @Transactional(readOnly = true)
    public long findCloudMarketCountByCondition(CloudMarket cloudMarket) {
        return cloudMarketDao.findCloudMarketCountByCondition(cloudMarket);
    }
    @Transactional
    public void updateCloudMarket(CloudMarket cloudMarket) {
        cloudMarketDao.updateCloudMarket(cloudMarket);
    }
    @Transactional
    public void updateCloudMarketForAll(CloudMarket cloudMarket) {
        cloudMarketDao.updateCloudMarketForAll(cloudMarket);
    }
    @Transactional
    public void deleteCloudMarket(String id) {
        cloudMarketDao.deleteCloudMarket(id);
    }
    @Transactional
    public void deleteCloudMarketByCondition(CloudMarket cloudMarket) {
        cloudMarketDao.deleteCloudMarketByCondition(cloudMarket);
    }
    @Transactional
    public void batchSaveCloudMarket(List<CloudMarket> cloudMarkets){
        cloudMarkets.forEach(cloudMarket -> cloudMarket.setId(sequenceId.nextId()));
        cloudMarketDao.batchSaveCloudMarket(cloudMarkets);
    }
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
