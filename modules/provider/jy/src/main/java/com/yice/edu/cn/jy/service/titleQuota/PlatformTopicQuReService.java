package com.yice.edu.cn.jy.service.titleQuota;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.jy.titleQuota.PlatformTopicQuRe;
import com.yice.edu.cn.jy.dao.titleQuota.IPlatformTopicQuReDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PlatformTopicQuReService {
    @Autowired
    private IPlatformTopicQuReDao platformTopicQuReDao;
    @Autowired
    private SequenceId sequenceId;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @Transactional(readOnly = true)
    public PlatformTopicQuRe findPlatformTopicQuReById(String id) {
        return platformTopicQuReDao.findPlatformTopicQuReById(id);
    }
    @Transactional
    public void savePlatformTopicQuRe(PlatformTopicQuRe platformTopicQuRe) {
        platformTopicQuRe.setId(sequenceId.nextId());
        platformTopicQuReDao.savePlatformTopicQuRe(platformTopicQuRe);
    }
    @Transactional(readOnly = true)
    public List<PlatformTopicQuRe> findPlatformTopicQuReListByCondition(PlatformTopicQuRe platformTopicQuRe) {
        return platformTopicQuReDao.findPlatformTopicQuReListByCondition(platformTopicQuRe);
    }
    @Transactional(readOnly = true)
    public PlatformTopicQuRe findOnePlatformTopicQuReByCondition(PlatformTopicQuRe platformTopicQuRe) {
        return platformTopicQuReDao.findOnePlatformTopicQuReByCondition(platformTopicQuRe);
    }
    @Transactional(readOnly = true)
    public long findPlatformTopicQuReCountByCondition(PlatformTopicQuRe platformTopicQuRe) {
        return platformTopicQuReDao.findPlatformTopicQuReCountByCondition(platformTopicQuRe);
    }
    @Transactional
    public void updatePlatformTopicQuRe(PlatformTopicQuRe platformTopicQuRe) {
        platformTopicQuReDao.updatePlatformTopicQuRe(platformTopicQuRe);
    }
    @Transactional
    public void updatePlatformTopicQuReForAll(PlatformTopicQuRe platformTopicQuRe) {
        platformTopicQuReDao.updatePlatformTopicQuReForAll(platformTopicQuRe);
    }
    @Transactional
    public void deletePlatformTopicQuRe(String id) {
        platformTopicQuReDao.deletePlatformTopicQuRe(id);
    }
    @Transactional
    public void deletePlatformTopicQuReByCondition(PlatformTopicQuRe platformTopicQuRe) {
        platformTopicQuReDao.deletePlatformTopicQuReByCondition(platformTopicQuRe);
    }
    @Transactional
    public void batchSavePlatformTopicQuRe(List<PlatformTopicQuRe> platformTopicQuRes){
        platformTopicQuRes.forEach(platformTopicQuRe -> platformTopicQuRe.setId(sequenceId.nextId()));
        platformTopicQuReDao.batchSavePlatformTopicQuRe(platformTopicQuRes);
    }
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
