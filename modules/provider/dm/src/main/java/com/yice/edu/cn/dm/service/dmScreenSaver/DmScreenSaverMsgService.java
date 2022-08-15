package com.yice.edu.cn.dm.service.dmScreenSaver;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.dm.dmScreenSaver.DmScreenSaverMsg;
import com.yice.edu.cn.dm.dao.dmScreenSaver.IDmScreenSaverMsgDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DmScreenSaverMsgService {
    @Autowired
    private IDmScreenSaverMsgDao dmScreenSaverMsgDao;
    @Autowired
    private SequenceId sequenceId;

    @Transactional(readOnly = true)
    public DmScreenSaverMsg findDmScreenSaverMsgById(String id) {
        return dmScreenSaverMsgDao.findDmScreenSaverMsgById(id);
    }
    @Transactional
    public void saveDmScreenSaverMsg(DmScreenSaverMsg dmScreenSaverMsg) {
        dmScreenSaverMsg.setId(sequenceId.nextId());
        dmScreenSaverMsg.setPwd("1234");
        dmScreenSaverMsgDao.saveDmScreenSaverMsg(dmScreenSaverMsg);
    }
    @Transactional(readOnly = true)
    public List<DmScreenSaverMsg> findDmScreenSaverMsgListByCondition(DmScreenSaverMsg dmScreenSaverMsg) {
        return dmScreenSaverMsgDao.findDmScreenSaverMsgListByCondition(dmScreenSaverMsg);
    }
    @Transactional(readOnly = true)
    public DmScreenSaverMsg findOneDmScreenSaverMsgByCondition(DmScreenSaverMsg dmScreenSaverMsg) {
        return dmScreenSaverMsgDao.findOneDmScreenSaverMsgByCondition(dmScreenSaverMsg);
    }
    @Transactional(readOnly = true)
    public long findDmScreenSaverMsgCountByCondition(DmScreenSaverMsg dmScreenSaverMsg) {
        return dmScreenSaverMsgDao.findDmScreenSaverMsgCountByCondition(dmScreenSaverMsg);
    }
    @Transactional
    public void updateDmScreenSaverMsg(DmScreenSaverMsg dmScreenSaverMsg) {
        dmScreenSaverMsgDao.updateDmScreenSaverMsg(dmScreenSaverMsg);
    }
    @Transactional
    public void deleteDmScreenSaverMsg(String id) {
        dmScreenSaverMsgDao.deleteDmScreenSaverMsg(id);
    }
    @Transactional
    public void deleteDmScreenSaverMsgByCondition(DmScreenSaverMsg dmScreenSaverMsg) {
        dmScreenSaverMsgDao.deleteDmScreenSaverMsgByCondition(dmScreenSaverMsg);
    }
    @Transactional
    public void batchSaveDmScreenSaverMsg(List<DmScreenSaverMsg> dmScreenSaverMsgs){
        dmScreenSaverMsgs.forEach(dmScreenSaverMsg -> dmScreenSaverMsg.setId(sequenceId.nextId()));
        dmScreenSaverMsgDao.batchSaveDmScreenSaverMsg(dmScreenSaverMsgs);
    }

    @Transactional
    public void batchUpdateDmScreenSaverMsg(DmScreenSaverMsg dmScreenSaverMsg){
        dmScreenSaverMsgDao.batchUpdateDmScreenSaverMsg(dmScreenSaverMsg);
    }

    @Transactional
    public void batchDeleteDmScreenSaverMsg(DmScreenSaverMsg dmScreenSaverMsg){
        dmScreenSaverMsgDao.batchDeleteDmScreenSaverMsg(dmScreenSaverMsg);
    }

    @Transactional
    public void batchUpdateDmScreenSaverMsgStatus(DmScreenSaverMsg dmScreenSaverMsg){
        dmScreenSaverMsgDao.batchUpdateDmScreenSaverMsgStatus(dmScreenSaverMsg);
    }

    @Transactional
    public DmScreenSaverMsg getRunNingDmScreenSaverMsg(DmScreenSaverMsg dmScreenSaverMsg){
        return dmScreenSaverMsgDao.getRunNingDmScreenSaverMsg(dmScreenSaverMsg);
    }

}
