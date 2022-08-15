package com.yice.edu.cn.jy.service.titleQuota;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.jy.titleQuota.SuperTel;
import com.yice.edu.cn.jy.dao.titleQuota.ISuperTelDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SuperTelService {
    @Autowired
    private ISuperTelDao superTelDao;
    @Autowired
    private SequenceId sequenceId;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @Transactional(readOnly = true)
    public SuperTel findSuperTelById(String id) {
        return superTelDao.findSuperTelById(id);
    }
    @Transactional
    public void saveSuperTel(SuperTel superTel) {
        superTel.setId(sequenceId.nextId());
        superTelDao.saveSuperTel(superTel);
    }
    @Transactional(readOnly = true)
    public List<SuperTel> findSuperTelListByCondition(SuperTel superTel) {
        return superTelDao.findSuperTelListByCondition(superTel);
    }
    @Transactional(readOnly = true)
    public SuperTel findOneSuperTelByCondition(SuperTel superTel) {
        return superTelDao.findOneSuperTelByCondition(superTel);
    }
    @Transactional(readOnly = true)
    public long findSuperTelCountByCondition(SuperTel superTel) {
        return superTelDao.findSuperTelCountByCondition(superTel);
    }
    @Transactional
    public void updateSuperTel(SuperTel superTel) {
        superTelDao.updateSuperTel(superTel);
    }
    @Transactional
    public void updateSuperTelForAll(SuperTel superTel) {
        superTelDao.updateSuperTelForAll(superTel);
    }
    @Transactional
    public void deleteSuperTel(String id) {
        superTelDao.deleteSuperTel(id);
    }
    @Transactional
    public void deleteSuperTelByCondition(SuperTel superTel) {
        superTelDao.deleteSuperTelByCondition(superTel);
    }
    @Transactional
    public void batchSaveSuperTel(List<SuperTel> superTels){
        superTels.forEach(superTel -> superTel.setId(sequenceId.nextId()));
        superTelDao.batchSaveSuperTel(superTels);
    }
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
