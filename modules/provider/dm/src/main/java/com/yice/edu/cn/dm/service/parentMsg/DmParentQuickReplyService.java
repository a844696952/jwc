package com.yice.edu.cn.dm.service.parentMsg;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.dm.parentMsg.DmParentQuickReply;
import com.yice.edu.cn.dm.dao.parentMsg.IDmParentQuickReplyDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DmParentQuickReplyService {
    @Autowired
    private IDmParentQuickReplyDao dmParentQuickReplyDao;
    @Autowired
    private SequenceId sequenceId;

    @Transactional(readOnly = true)
    public DmParentQuickReply findDmParentQuickReplyById(String id) {
        return dmParentQuickReplyDao.findDmParentQuickReplyById(id);
    }
    @Transactional
    public void saveDmParentQuickReply(DmParentQuickReply dmParentQuickReply) {
        dmParentQuickReply.setId(sequenceId.nextId());
        dmParentQuickReplyDao.saveDmParentQuickReply(dmParentQuickReply);
    }
    @Transactional(readOnly = true)
    public List<DmParentQuickReply> findDmParentQuickReplyListByCondition(DmParentQuickReply dmParentQuickReply) {
        return dmParentQuickReplyDao.findDmParentQuickReplyListByCondition(dmParentQuickReply);
    }
    @Transactional(readOnly = true)
    public DmParentQuickReply findOneDmParentQuickReplyByCondition(DmParentQuickReply dmParentQuickReply) {
        return dmParentQuickReplyDao.findOneDmParentQuickReplyByCondition(dmParentQuickReply);
    }
    @Transactional(readOnly = true)
    public long findDmParentQuickReplyCountByCondition(DmParentQuickReply dmParentQuickReply) {
        return dmParentQuickReplyDao.findDmParentQuickReplyCountByCondition(dmParentQuickReply);
    }
    @Transactional
    public void updateDmParentQuickReply(DmParentQuickReply dmParentQuickReply) {
        dmParentQuickReplyDao.updateDmParentQuickReply(dmParentQuickReply);
    }
    @Transactional
    public void deleteDmParentQuickReply(String id) {
        dmParentQuickReplyDao.deleteDmParentQuickReply(id);
    }
    @Transactional
    public void deleteDmParentQuickReplyByCondition(DmParentQuickReply dmParentQuickReply) {
        dmParentQuickReplyDao.deleteDmParentQuickReplyByCondition(dmParentQuickReply);
    }
    @Transactional
    public void batchSaveDmParentQuickReply(List<DmParentQuickReply> dmParentQuickReplys){
        dmParentQuickReplys.forEach(dmParentQuickReply -> dmParentQuickReply.setId(sequenceId.nextId()));
        dmParentQuickReplyDao.batchSaveDmParentQuickReply(dmParentQuickReplys);
    }

}
