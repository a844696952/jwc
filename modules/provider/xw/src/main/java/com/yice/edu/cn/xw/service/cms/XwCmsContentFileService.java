package com.yice.edu.cn.xw.service.cms;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.xw.cms.XwCmsContentFile;
import com.yice.edu.cn.xw.dao.cms.IXwCmsContentFileDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class XwCmsContentFileService {
    @Autowired
    private IXwCmsContentFileDao xwCmsContentFileDao;
    @Autowired
    private SequenceId sequenceId;

    /*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public XwCmsContentFile findXwCmsContentFileById(String id) {
        return xwCmsContentFileDao.findXwCmsContentFileById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public void saveXwCmsContentFile(XwCmsContentFile xwCmsContentFile) {
        xwCmsContentFile.setId(sequenceId.nextId());
        xwCmsContentFileDao.saveXwCmsContentFile(xwCmsContentFile);
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public List<XwCmsContentFile> findXwCmsContentFileListByCondition(XwCmsContentFile xwCmsContentFile) {
        return xwCmsContentFileDao.findXwCmsContentFileListByCondition(xwCmsContentFile);
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public XwCmsContentFile findOneXwCmsContentFileByCondition(XwCmsContentFile xwCmsContentFile) {
        return xwCmsContentFileDao.findOneXwCmsContentFileByCondition(xwCmsContentFile);
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public long findXwCmsContentFileCountByCondition(XwCmsContentFile xwCmsContentFile) {
        return xwCmsContentFileDao.findXwCmsContentFileCountByCondition(xwCmsContentFile);
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateXwCmsContentFile(XwCmsContentFile xwCmsContentFile) {
        xwCmsContentFileDao.updateXwCmsContentFile(xwCmsContentFile);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteXwCmsContentFile(String id) {
        xwCmsContentFileDao.deleteXwCmsContentFile(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteXwCmsContentFileByCondition(XwCmsContentFile xwCmsContentFile) {
        xwCmsContentFileDao.deleteXwCmsContentFileByCondition(xwCmsContentFile);
    }

    @Transactional(rollbackFor = Exception.class)
    public void batchSaveXwCmsContentFile(List<XwCmsContentFile> xwCmsContentFiles) {
        xwCmsContentFiles.forEach(xwCmsContentFile -> xwCmsContentFile.setId(sequenceId.nextId()));
        xwCmsContentFileDao.batchSaveXwCmsContentFile(xwCmsContentFiles);
    }
    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
