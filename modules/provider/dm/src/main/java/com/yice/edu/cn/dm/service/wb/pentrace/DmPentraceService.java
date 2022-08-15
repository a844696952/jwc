package com.yice.edu.cn.dm.service.wb.pentrace;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.wb.pentrace.DmPentrace;
import com.yice.edu.cn.dm.dao.wb.pentrace.IDmPentraceDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DmPentraceService {
    @Autowired
    private IDmPentraceDao dmPentraceDao;
    @Autowired
    private SequenceId sequenceId;

    @Transactional(readOnly = true)
    public DmPentrace findDmPentraceById(String id) {
        return dmPentraceDao.findDmPentraceById(id);
    }
    @Transactional
    public void saveDmPentrace(DmPentrace dmPentrace) {
        dmPentrace.setId(sequenceId.nextId());
        dmPentraceDao.saveDmPentrace(dmPentrace);
    }
    @Transactional(readOnly = true)
    public List<DmPentrace> findDmPentraceListByCondition(DmPentrace dmPentrace) {
        return dmPentraceDao.findDmPentraceListByCondition(dmPentrace);
    }
    @Transactional(readOnly = true)
    public DmPentrace findOneDmPentraceByCondition(DmPentrace dmPentrace) {
        return dmPentraceDao.findOneDmPentraceByCondition(dmPentrace);
    }
    @Transactional(readOnly = true)
    public long findDmPentraceCountByCondition(DmPentrace dmPentrace) {
        return dmPentraceDao.findDmPentraceCountByCondition(dmPentrace);
    }
    @Transactional
    public void updateDmPentrace(DmPentrace dmPentrace) {
        dmPentraceDao.updateDmPentrace(dmPentrace);
    }
    @Transactional
    public void deleteDmPentrace(String id) {
        dmPentraceDao.deleteDmPentrace(id);
    }
    @Transactional
    public void deleteDmPentraceByCondition(DmPentrace dmPentrace) {
        dmPentraceDao.deleteDmPentraceByCondition(dmPentrace);
    }
    @Transactional
    public void batchSaveDmPentrace(List<DmPentrace> dmPentraces){
        dmPentraces.forEach(dmPentrace -> dmPentrace.setId(sequenceId.nextId()));
        dmPentraceDao.batchSaveDmPentrace(dmPentraces);
    }

}
