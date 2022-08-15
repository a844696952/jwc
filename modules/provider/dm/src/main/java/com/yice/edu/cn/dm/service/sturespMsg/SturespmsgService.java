package com.yice.edu.cn.dm.service.sturespMsg;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.dm.sturespMsg.Sturespmsg;
import com.yice.edu.cn.dm.dao.sturespMsg.ISturespmsgDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SturespmsgService {
    @Autowired
    private ISturespmsgDao sturespmsgDao;
    @Autowired
    private SequenceId sequenceId;

    @Transactional(readOnly = true)
    public Sturespmsg findSturespmsgById(String id) {
        return sturespmsgDao.findSturespmsgById(id);
    }
    @Transactional
    public void saveSturespmsg(Sturespmsg sturespmsg) {
        sturespmsg.setId(sequenceId.nextId());
        sturespmsgDao.saveSturespmsg(sturespmsg);
    }
    @Transactional(readOnly = true)
    public List<Sturespmsg> findSturespmsgListByCondition(Sturespmsg sturespmsg) {
        return sturespmsgDao.findSturespmsgListByCondition(sturespmsg);
    }
    @Transactional(readOnly = true)
    public Sturespmsg findOneSturespmsgByCondition(Sturespmsg sturespmsg) {
        return sturespmsgDao.findOneSturespmsgByCondition(sturespmsg);
    }
    @Transactional(readOnly = true)
    public long findSturespmsgCountByCondition(Sturespmsg sturespmsg) {
        return sturespmsgDao.findSturespmsgCountByCondition(sturespmsg);
    }
    @Transactional
    public void updateSturespmsg(Sturespmsg sturespmsg) {
        sturespmsgDao.updateSturespmsg(sturespmsg);
    }
    @Transactional
    public void deleteSturespmsg(String id) {
        sturespmsgDao.deleteSturespmsg(id);
    }
    @Transactional
    public void deleteSturespmsgByCondition(Sturespmsg sturespmsg) {
        sturespmsgDao.deleteSturespmsgByCondition(sturespmsg);
    }
    @Transactional
    public void batchSaveSturespmsg(List<Sturespmsg> sturespmsgs){
        sturespmsgs.forEach(sturespmsg -> sturespmsg.setId(sequenceId.nextId()));
        sturespmsgDao.batchSaveSturespmsg(sturespmsgs);
    }

    @Transactional
    public void deleteSturespmsgBeforeTwoDay() {
        sturespmsgDao.deleteSturespmsgBeforeTwoDay();
    }
}
