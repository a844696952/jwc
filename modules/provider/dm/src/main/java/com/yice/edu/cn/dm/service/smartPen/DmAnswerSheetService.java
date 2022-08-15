package com.yice.edu.cn.dm.service.smartPen;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.dm.smartPen.DmAnswerSheet;
import com.yice.edu.cn.dm.dao.smartPen.IDmAnswerSheetDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DmAnswerSheetService {
    @Autowired
    private IDmAnswerSheetDao dmAnswerSheetDao;
    @Autowired
    private SequenceId sequenceId;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @Transactional(readOnly = true)
    public DmAnswerSheet findDmAnswerSheetById(String id) {
        return dmAnswerSheetDao.findDmAnswerSheetById(id);
    }
    @Transactional
    public void saveDmAnswerSheet(DmAnswerSheet dmAnswerSheet) {
        dmAnswerSheet.setId(sequenceId.nextId());
        dmAnswerSheetDao.saveDmAnswerSheet(dmAnswerSheet);
    }
    @Transactional(readOnly = true)
    public List<DmAnswerSheet> findDmAnswerSheetListByCondition(DmAnswerSheet dmAnswerSheet) {
        dmAnswerSheet.getPager().setPaging(false);
        return dmAnswerSheetDao.findDmAnswerSheetListByCondition(dmAnswerSheet);
    }
    @Transactional(readOnly = true)
    public DmAnswerSheet findOneDmAnswerSheetByCondition(DmAnswerSheet dmAnswerSheet) {
        return dmAnswerSheetDao.findOneDmAnswerSheetByCondition(dmAnswerSheet);
    }
    @Transactional(readOnly = true)
    public long findDmAnswerSheetCountByCondition(DmAnswerSheet dmAnswerSheet) {
        return dmAnswerSheetDao.findDmAnswerSheetCountByCondition(dmAnswerSheet);
    }
    @Transactional
    public void updateDmAnswerSheet(DmAnswerSheet dmAnswerSheet) {
        dmAnswerSheetDao.updateDmAnswerSheet(dmAnswerSheet);
    }
    @Transactional
    public void deleteDmAnswerSheet(String id) {
        dmAnswerSheetDao.deleteDmAnswerSheet(id);
    }
    @Transactional
    public void deleteDmAnswerSheetByCondition(DmAnswerSheet dmAnswerSheet) {
        dmAnswerSheetDao.deleteDmAnswerSheetByCondition(dmAnswerSheet);
    }
    @Transactional
    public void batchSaveDmAnswerSheet(List<DmAnswerSheet> dmAnswerSheets){
        dmAnswerSheets.forEach(dmAnswerSheet -> dmAnswerSheet.setId(sequenceId.nextId()));
        dmAnswerSheetDao.batchSaveDmAnswerSheet(dmAnswerSheets);
    }
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
