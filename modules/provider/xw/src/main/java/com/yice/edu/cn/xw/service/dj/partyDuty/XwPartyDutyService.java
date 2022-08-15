package com.yice.edu.cn.xw.service.dj.partyDuty;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.xw.dj.partyDuty.XwPartyDuty;
import com.yice.edu.cn.xw.dao.dj.partyDuty.XwPartyDutyDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class XwPartyDutyService {
    @Autowired
    private XwPartyDutyDao xwPartyDutyDao;
    @Autowired
    private SequenceId sequenceId;

    @Transactional(readOnly = true)
    public XwPartyDuty findXwPartyDutyById(String id) {
        return xwPartyDutyDao.findXwPartyDutyById(id);
    }
    @Transactional
    public void saveXwPartyDuty(XwPartyDuty xwPartyDuty) {
        xwPartyDuty.setId(sequenceId.nextId());
        xwPartyDutyDao.saveXwPartyDuty(xwPartyDuty);
    }
    @Transactional(readOnly = true)
    public List<XwPartyDuty> findXwPartyDutyListByCondition(XwPartyDuty xwPartyDuty) {
        return xwPartyDutyDao.findXwPartyDutyListByCondition(xwPartyDuty);
    }
    @Transactional(readOnly = true)
    public XwPartyDuty findOneXwPartyDutyByCondition(XwPartyDuty xwPartyDuty) {
        return xwPartyDutyDao.findOneXwPartyDutyByCondition(xwPartyDuty);
    }
    @Transactional(readOnly = true)
    public long findXwPartyDutyCountByCondition(XwPartyDuty xwPartyDuty) {
        return xwPartyDutyDao.findXwPartyDutyCountByCondition(xwPartyDuty);
    }
    @Transactional
    public void updateXwPartyDuty(XwPartyDuty xwPartyDuty) {
        xwPartyDutyDao.updateXwPartyDuty(xwPartyDuty);
    }
    @Transactional
    public void deleteXwPartyDuty(String id) {
        xwPartyDutyDao.deleteXwPartyDuty(id);
    }
    @Transactional
    public void deleteXwPartyDutyByCondition(XwPartyDuty xwPartyDuty) {
        xwPartyDutyDao.deleteXwPartyDutyByCondition(xwPartyDuty);
    }
    @Transactional
    public void batchSaveXwPartyDuty(List<XwPartyDuty> xwPartyDutys){
        xwPartyDutys.forEach(xwPartyDuty -> xwPartyDuty.setId(sequenceId.nextId()));
        xwPartyDutyDao.batchSaveXwPartyDuty(xwPartyDutys);
    }
    @Transactional(readOnly = true)
    public XwPartyDuty findXwPartyDutyByName(String name) {
        return xwPartyDutyDao.findXwPartyDutyByName(name);
    }
}
