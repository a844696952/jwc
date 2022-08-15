package com.yice.edu.cn.jw.service.loc;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.loc.LocVersion;
import com.yice.edu.cn.jw.dao.loc.LocVersionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LocVersionService {
    @Autowired
    private LocVersionDao locVersionDao;
    @Autowired
    private SequenceId sequenceId;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @Transactional(readOnly = true)
    public LocVersion findLocVersionById(String id) {
        return locVersionDao.findLocVersionById(id);
    }
    @Transactional
    public void saveLocVersion(LocVersion locVersion) {
        locVersion.setId(sequenceId.nextId());
        locVersionDao.saveLocVersion(locVersion);
    }
    @Transactional(readOnly = true)
    public List<LocVersion> findLocVersionListByCondition(LocVersion locVersion) {
        return locVersionDao.findLocVersionListByCondition(locVersion);
    }
    @Transactional(readOnly = true)
    public LocVersion findOneLocVersionByCondition(LocVersion locVersion) {
        return locVersionDao.findOneLocVersionByCondition(locVersion);
    }
    @Transactional(readOnly = true)
    public long findLocVersionCountByCondition(LocVersion locVersion) {
        return locVersionDao.findLocVersionCountByCondition(locVersion);
    }
    @Transactional
    public void updateLocVersion(LocVersion locVersion) {
        locVersionDao.updateLocVersion(locVersion);
    }
    @Transactional
    public void updateLocVersionForAll(LocVersion locVersion) {
        locVersionDao.updateLocVersionForAll(locVersion);
    }
    @Transactional
    public void deleteLocVersion(String id) {
        locVersionDao.deleteLocVersion(id);
    }
    @Transactional
    public void deleteLocVersionByCondition(LocVersion locVersion) {
        locVersionDao.deleteLocVersionByCondition(locVersion);
    }
    @Transactional
    public void batchSaveLocVersion(List<LocVersion> locVersions){
        locVersions.forEach(locVersion -> locVersion.setId(sequenceId.nextId()));
        locVersionDao.batchSaveLocVersion(locVersions);
    }
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/

    @Transactional(readOnly = true)
    public long findVersionNameRepetition(LocVersion locVersion){
        return locVersionDao.findVersionNameRepetition(locVersion);
    }
}
