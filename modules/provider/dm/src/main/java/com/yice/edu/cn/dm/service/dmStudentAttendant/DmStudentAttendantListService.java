package com.yice.edu.cn.dm.service.dmStudentAttendant;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.dm.dmStudentAttendant.DmStudentAttendantList;
import com.yice.edu.cn.dm.dao.dmStudentAttendant.IDmStudentAttendantListDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DmStudentAttendantListService {
    @Autowired
    private IDmStudentAttendantListDao dmStudentAttendantListDao;
    @Autowired
    private SequenceId sequenceId;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @Transactional(readOnly = true)
    public DmStudentAttendantList findDmStudentAttendantListById(String id) {
        return dmStudentAttendantListDao.findDmStudentAttendantListById(id);
    }
    @Transactional
    public void saveDmStudentAttendantList(DmStudentAttendantList dmStudentAttendantList) {
        dmStudentAttendantList.setId(sequenceId.nextId());
        dmStudentAttendantListDao.saveDmStudentAttendantList(dmStudentAttendantList);
    }
    @Transactional(readOnly = true)
    public List<DmStudentAttendantList> findDmStudentAttendantListListByCondition(DmStudentAttendantList dmStudentAttendantList) {
        return dmStudentAttendantListDao.findDmStudentAttendantListListByCondition(dmStudentAttendantList);
    }
    @Transactional(readOnly = true)
    public DmStudentAttendantList findOneDmStudentAttendantListByCondition(DmStudentAttendantList dmStudentAttendantList) {
        return dmStudentAttendantListDao.findOneDmStudentAttendantListByCondition(dmStudentAttendantList);
    }
    @Transactional(readOnly = true)
    public long findDmStudentAttendantListCountByCondition(DmStudentAttendantList dmStudentAttendantList) {
        return dmStudentAttendantListDao.findDmStudentAttendantListCountByCondition(dmStudentAttendantList);
    }
    @Transactional
    public void updateDmStudentAttendantList(DmStudentAttendantList dmStudentAttendantList) {
        dmStudentAttendantListDao.updateDmStudentAttendantList(dmStudentAttendantList);
    }
    @Transactional
    public void updateDmStudentAttendantListForAll(DmStudentAttendantList dmStudentAttendantList) {
        dmStudentAttendantListDao.updateDmStudentAttendantListForAll(dmStudentAttendantList);
    }
    @Transactional
    public void deleteDmStudentAttendantList(String id) {
        dmStudentAttendantListDao.deleteDmStudentAttendantList(id);
    }
    @Transactional
    public void deleteDmStudentAttendantListByCondition(DmStudentAttendantList dmStudentAttendantList) {
        dmStudentAttendantListDao.deleteDmStudentAttendantListByCondition(dmStudentAttendantList);
    }
    @Transactional
    public void batchSaveDmStudentAttendantList(List<DmStudentAttendantList> dmStudentAttendantLists){
        dmStudentAttendantLists.forEach(dmStudentAttendantList -> dmStudentAttendantList.setId(sequenceId.nextId()));
        dmStudentAttendantListDao.batchSaveDmStudentAttendantList(dmStudentAttendantLists);
    }
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
