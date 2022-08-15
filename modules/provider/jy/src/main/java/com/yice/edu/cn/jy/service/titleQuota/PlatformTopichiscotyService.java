package com.yice.edu.cn.jy.service.titleQuota;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.jy.titleQuota.PlatformTopichiscoty;
import com.yice.edu.cn.jy.dao.titleQuota.IPlatformTopichiscotyDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PlatformTopichiscotyService {
    @Autowired
    private IPlatformTopichiscotyDao platformTopichiscotyDao;
    @Autowired
    private SequenceId sequenceId;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @Transactional(readOnly = true)
    public PlatformTopichiscoty findPlatformTopichiscotyById(String id) {
        return platformTopichiscotyDao.findPlatformTopichiscotyById(id);
    }
    @Transactional
    public void savePlatformTopichiscoty(PlatformTopichiscoty platformTopichiscoty) {
        platformTopichiscoty.setId(sequenceId.nextId());
        platformTopichiscotyDao.savePlatformTopichiscoty(platformTopichiscoty);
    }
    @Transactional(readOnly = true)
    public List<PlatformTopichiscoty> findPlatformTopichiscotyListByCondition(PlatformTopichiscoty platformTopichiscoty) {
        return platformTopichiscotyDao.findPlatformTopichiscotyListByCondition(platformTopichiscoty);
    }
    @Transactional(readOnly = true)
    public PlatformTopichiscoty findOnePlatformTopichiscotyByCondition(PlatformTopichiscoty platformTopichiscoty) {
        return platformTopichiscotyDao.findOnePlatformTopichiscotyByCondition(platformTopichiscoty);
    }
    @Transactional(readOnly = true)
    public long findPlatformTopichiscotyCountByCondition(PlatformTopichiscoty platformTopichiscoty) {
        return platformTopichiscotyDao.findPlatformTopichiscotyCountByCondition(platformTopichiscoty);
    }
    @Transactional
    public void updatePlatformTopichiscoty(PlatformTopichiscoty platformTopichiscoty) {
        platformTopichiscotyDao.updatePlatformTopichiscoty(platformTopichiscoty);
    }
    @Transactional
    public void updatePlatformTopichiscotyForAll(PlatformTopichiscoty platformTopichiscoty) {
        platformTopichiscotyDao.updatePlatformTopichiscotyForAll(platformTopichiscoty);
    }
    @Transactional
    public void deletePlatformTopichiscoty(String id) {
        platformTopichiscotyDao.deletePlatformTopichiscoty(id);
    }
    @Transactional
    public void deletePlatformTopichiscotyByCondition(PlatformTopichiscoty platformTopichiscoty) {
        platformTopichiscotyDao.deletePlatformTopichiscotyByCondition(platformTopichiscoty);
    }
    @Transactional
    public void batchSavePlatformTopichiscoty(List<PlatformTopichiscoty> platformTopichiscotys){
        platformTopichiscotys.forEach(platformTopichiscoty -> platformTopichiscoty.setId(sequenceId.nextId()));
        platformTopichiscotyDao.batchSavePlatformTopichiscoty(platformTopichiscotys);
    }
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
