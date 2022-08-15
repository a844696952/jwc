package com.yice.edu.cn.dm.service.schoolActive;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.dm.schoolActive.DmAttachmentFile;
import com.yice.edu.cn.dm.dao.schoolActive.IDmAttachmentFileDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DmAttachmentFileService {
    @Autowired
    private IDmAttachmentFileDao dmAttachmentFileDao;
    @Autowired
    private SequenceId sequenceId;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @Transactional(readOnly = true)
    public DmAttachmentFile findDmAttachmentFileById(String id) {
        return dmAttachmentFileDao.findDmAttachmentFileById(id);
    }
    @Transactional
    public void saveDmAttachmentFile(DmAttachmentFile dmAttachmentFile) {
        dmAttachmentFile.setId(sequenceId.nextId());
        dmAttachmentFileDao.saveDmAttachmentFile(dmAttachmentFile);
    }
    @Transactional(readOnly = true)
    public List<DmAttachmentFile> findDmAttachmentFileListByCondition(DmAttachmentFile dmAttachmentFile) {
        return dmAttachmentFileDao.findDmAttachmentFileListByCondition(dmAttachmentFile);
    }
    @Transactional(readOnly = true)
    public DmAttachmentFile findOneDmAttachmentFileByCondition(DmAttachmentFile dmAttachmentFile) {
        return dmAttachmentFileDao.findOneDmAttachmentFileByCondition(dmAttachmentFile);
    }
    @Transactional(readOnly = true)
    public long findDmAttachmentFileCountByCondition(DmAttachmentFile dmAttachmentFile) {
        return dmAttachmentFileDao.findDmAttachmentFileCountByCondition(dmAttachmentFile);
    }
    @Transactional
    public void updateDmAttachmentFile(DmAttachmentFile dmAttachmentFile) {
        dmAttachmentFileDao.updateDmAttachmentFile(dmAttachmentFile);
    }
    @Transactional
    public void deleteDmAttachmentFile(String id) {
        dmAttachmentFileDao.deleteDmAttachmentFile(id);
    }
    @Transactional
    public void deleteDmAttachmentFileByCondition(DmAttachmentFile dmAttachmentFile) {
        dmAttachmentFileDao.deleteDmAttachmentFileByCondition(dmAttachmentFile);
    }
    @Transactional
    public void batchSaveDmAttachmentFile(List<DmAttachmentFile> dmAttachmentFiles){
        dmAttachmentFiles.forEach(dmAttachmentFile -> dmAttachmentFile.setId(sequenceId.nextId()));
        dmAttachmentFileDao.batchSaveDmAttachmentFile(dmAttachmentFiles);
    }
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
