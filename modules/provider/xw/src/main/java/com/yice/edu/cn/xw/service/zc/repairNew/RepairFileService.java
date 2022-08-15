package com.yice.edu.cn.xw.service.zc.repairNew;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.xw.zc.repairNew.RepairFile;
import com.yice.edu.cn.xw.dao.zc.repairNew.IRepairFileDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RepairFileService {
    @Autowired
    private IRepairFileDao repairFileDao;
    @Autowired
    private SequenceId sequenceId;

    @Transactional(readOnly = true)
    public RepairFile findRepairFileById(String id) {
        return repairFileDao.findRepairFileById(id);
    }
    @Transactional
    public void saveRepairFile(RepairFile repairFile) {
        repairFile.setId(sequenceId.nextId());
        repairFileDao.saveRepairFile(repairFile);
    }
    @Transactional(readOnly = true)
    public List<RepairFile> findRepairFileListByCondition(RepairFile repairFile) {
        return repairFileDao.findRepairFileListByCondition(repairFile);
    }
    @Transactional(readOnly = true)
    public RepairFile findOneRepairFileByCondition(RepairFile repairFile) {
        return repairFileDao.findOneRepairFileByCondition(repairFile);
    }
    @Transactional(readOnly = true)
    public long findRepairFileCountByCondition(RepairFile repairFile) {
        return repairFileDao.findRepairFileCountByCondition(repairFile);
    }
    @Transactional
    public void updateRepairFile(RepairFile repairFile) {
        repairFileDao.updateRepairFile(repairFile);
    }
    @Transactional
    public void deleteRepairFile(String id) {
        repairFileDao.deleteRepairFile(id);
    }
    @Transactional
    public void deleteRepairFileByCondition(RepairFile repairFile) {
        repairFileDao.deleteRepairFileByCondition(repairFile);
    }
    @Transactional
    public void batchSaveRepairFile(List<RepairFile> repairFiles){
        repairFiles.forEach(repairFile -> repairFile.setId(sequenceId.nextId()));
        repairFileDao.batchSaveRepairFile(repairFiles);
    }

}
