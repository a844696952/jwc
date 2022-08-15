package com.yice.edu.cn.jw.service.nation;

import com.alicp.jetcache.anno.CacheRefresh;
import com.alicp.jetcache.anno.Cached;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.general.nation.Nation;
import com.yice.edu.cn.jw.dao.nation.INationDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class NationService {
    @Autowired
    private INationDao nationDao;
    @Autowired
    private SequenceId sequenceId;

    @Transactional(readOnly = true)
    public Nation findNationById(String id) {
        return nationDao.findNationById(id);
    }
    @Transactional
    public void saveNation(Nation nation) {
        nation.setId(sequenceId.nextId());
        nationDao.saveNation(nation);
    }
    @Transactional(readOnly = true)
    @Cached(name = "redis_nation",key="#nation")
    @CacheRefresh(refresh = 3600,refreshLockTimeout = 60)
    public List<Nation> findNationListByCondition(Nation nation) {
        return nationDao.findNationListByCondition(nation);
    }
    @Transactional(readOnly = true)
    public long findNationCountByCondition(Nation nation) {
        return nationDao.findNationCountByCondition(nation);
    }
    @Transactional
    public void updateNation(Nation nation) {
        nationDao.updateNation(nation);
    }
    @Transactional
    public void deleteNation(String id) {
        nationDao.deleteNation(id);
    }
}
