package com.yice.edu.cn.jw.service.dd;

import com.alicp.jetcache.anno.CacheRefresh;
import com.alicp.jetcache.anno.Cached;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.general.dd.Dd;
import com.yice.edu.cn.jw.dao.dd.IDdDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DdService {
    @Autowired
    private IDdDao ddDao;
    @Autowired
    private SequenceId sequenceId;

    @Transactional(readOnly = true)
    public Dd findDdById(String id) {
        return ddDao.findDdById(id);
    }
    @Transactional
    public void saveDd(Dd dd) {
        dd.setId(sequenceId.nextId());
        ddDao.saveDd(dd);
    }
    @Transactional(readOnly = true)
    @Cached(name = "redis_dd",key="#dd")
    @CacheRefresh(refresh = 3600,refreshLockTimeout = 60)
    public List<Dd> findDdListByCondition(Dd dd) {
        return ddDao.findDdListByCondition(dd);
    }
    @Transactional(readOnly = true)
    public long findDdCountByCondition(Dd dd) {
        return ddDao.findDdCountByCondition(dd);
    }
    @Transactional
    public void updateDd(Dd dd) {
        ddDao.updateDd(dd);
    }
    @Transactional
    public void deleteDd(String id) {
        ddDao.deleteDd(id);
    }

    public List<Dd> findJwClassesList(Dd dd) {
        return ddDao.findJwClassesList(dd);
    }

    @Transactional(readOnly = true)
    public List<Dd> findDdListBySchoolId(String schoolId) {
        Dd dd = new Dd();
        dd.setLevelType(ddDao.selectSchoolTypeIdBySchoolId(schoolId));
        dd.setTypeId(Constant.DD_TYPE.GRADE);
        return ddDao.findDdListByCondition(dd);
    }
}
