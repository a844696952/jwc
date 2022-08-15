package com.yice.edu.cn.xw.service.dj.partyMemberFile;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.xw.dj.partyMemberFile.XwDjAttachmentFile;
import com.yice.edu.cn.xw.dao.dj.partyMemberFile.IXwDjAttachmentFileDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class XwDjAttachmentFileService {
    @Autowired
    private IXwDjAttachmentFileDao xwDjAttachmentFileDao;
    @Autowired
    private SequenceId sequenceId;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @Transactional(readOnly = true)
    public XwDjAttachmentFile findXwDjAttachmentFileById(String id) {
        return xwDjAttachmentFileDao.findXwDjAttachmentFileById(id);
    }
    @Transactional
    public void saveXwDjAttachmentFile(XwDjAttachmentFile xwDjAttachmentFile) {
        xwDjAttachmentFile.setId(sequenceId.nextId());
        xwDjAttachmentFileDao.saveXwDjAttachmentFile(xwDjAttachmentFile);
    }
    @Transactional(readOnly = true)
    public List<XwDjAttachmentFile> findXwDjAttachmentFileListByCondition(XwDjAttachmentFile xwDjAttachmentFile) {
        return xwDjAttachmentFileDao.findXwDjAttachmentFileListByCondition(xwDjAttachmentFile);
    }
    @Transactional(readOnly = true)
    public XwDjAttachmentFile findOneXwDjAttachmentFileByCondition(XwDjAttachmentFile xwDjAttachmentFile) {
        return xwDjAttachmentFileDao.findOneXwDjAttachmentFileByCondition(xwDjAttachmentFile);
    }
    @Transactional(readOnly = true)
    public long findXwDjAttachmentFileCountByCondition(XwDjAttachmentFile xwDjAttachmentFile) {
        return xwDjAttachmentFileDao.findXwDjAttachmentFileCountByCondition(xwDjAttachmentFile);
    }
    @Transactional
    public void updateXwDjAttachmentFile(XwDjAttachmentFile xwDjAttachmentFile) {
        xwDjAttachmentFileDao.updateXwDjAttachmentFile(xwDjAttachmentFile);
    }
    @Transactional
    public void deleteXwDjAttachmentFile(String id) {
        xwDjAttachmentFileDao.deleteXwDjAttachmentFile(id);
    }
    @Transactional
    public void deleteXwDjAttachmentFileByCondition(XwDjAttachmentFile xwDjAttachmentFile) {
        xwDjAttachmentFileDao.deleteXwDjAttachmentFileByCondition(xwDjAttachmentFile);
    }
    @Transactional
    public void batchSaveXwDjAttachmentFile(List<XwDjAttachmentFile> xwDjAttachmentFiles){
        xwDjAttachmentFiles.forEach(xwDjAttachmentFile -> xwDjAttachmentFile.setId(sequenceId.nextId()));
        xwDjAttachmentFileDao.batchSaveXwDjAttachmentFile(xwDjAttachmentFiles);
    }
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
