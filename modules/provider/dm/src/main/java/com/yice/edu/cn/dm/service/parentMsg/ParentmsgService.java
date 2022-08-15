package com.yice.edu.cn.dm.service.parentMsg;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.dm.parentMsg.Parentmsg;
import com.yice.edu.cn.dm.dao.parentMsg.IParentmsgDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ParentmsgService {
    @Autowired
    private IParentmsgDao parentmsgDao;
    @Autowired
    private SequenceId sequenceId;

    @Transactional(readOnly = true)
    public Parentmsg findParentmsgById(String id) {
        return parentmsgDao.findParentmsgById(id);
    }
    @Transactional
    public void saveParentmsg(Parentmsg parentmsg) {
        parentmsg.setId(sequenceId.nextId());
        parentmsgDao.saveParentmsg(parentmsg);
    }
    @Transactional(readOnly = true)
    public List<Parentmsg> findParentmsgListByCondition(Parentmsg parentmsg) {
        return parentmsgDao.findParentmsgListByCondition(parentmsg);
    }
    @Transactional(readOnly = true)
    public Parentmsg findOneParentmsgByCondition(Parentmsg parentmsg) {
        return parentmsgDao.findOneParentmsgByCondition(parentmsg);
    }
    @Transactional(readOnly = true)
    public long findParentmsgCountByCondition(Parentmsg parentmsg) {
        return parentmsgDao.findParentmsgCountByCondition(parentmsg);
    }
    @Transactional
    public void updateParentmsg(Parentmsg parentmsg) {
        parentmsgDao.updateParentmsg(parentmsg);
    }
    @Transactional
    public void deleteParentmsg(String id) {
        parentmsgDao.deleteParentmsg(id);
    }
    @Transactional
    public void deleteParentmsgByCondition(Parentmsg parentmsg) {
        parentmsgDao.deleteParentmsgByCondition(parentmsg);
    }
    @Transactional
    public void batchSaveParentmsg(List<Parentmsg> parentmsgs){
        parentmsgs.forEach(parentmsg -> parentmsg.setId(sequenceId.nextId()));
        parentmsgDao.batchSaveParentmsg(parentmsgs);
    }

    @Transactional
    public void deleteParentmsgByState(Parentmsg parentmsg){
        parentmsgDao.deleteParentmsgByState(parentmsg);
    }

    @Transactional
    public void updateParentmsgByStuCardNo(Parentmsg parentmsg) {
        parentmsgDao.updateParentmsgByStuCardNo(parentmsg);
    }

    @Transactional
    public void deleteParentMsgTwoDayBefore(){
        parentmsgDao.deleteParentMsgTwoDayBefore();
    }

    @Transactional
    public void deleteParentMsgBySchoolId(String schoolId){
        parentmsgDao.deleteParentMsgBySchoolId(schoolId);
    }
}
