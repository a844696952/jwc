package com.yice.edu.cn.jy.service.handout;

import cn.hutool.core.date.DateUtil;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.jy.handout.HandoutFile;
import com.yice.edu.cn.jy.dao.handout.IHandoutFileDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class HandoutFileService {
    @Autowired
    private IHandoutFileDao handoutFileDao;
    @Autowired
    private SequenceId sequenceId;

    @Transactional(readOnly = true)
    public HandoutFile findHandoutFileById(String id) {
        return handoutFileDao.findHandoutFileById(id);
    }
    @Transactional
    public void saveHandoutFile(HandoutFile handoutFile) {
        handoutFile.setId(sequenceId.nextId());
        handoutFile.setCreateTime(DateUtil.now());
        handoutFileDao.saveHandoutFile(handoutFile);
    }
    @Transactional(readOnly = true)
    public List<HandoutFile> findHandoutFileListByCondition(HandoutFile handoutFile) {
        return handoutFileDao.findHandoutFileListByCondition(handoutFile);
    }
    @Transactional(readOnly = true)
    public HandoutFile findOneHandoutFileByCondition(HandoutFile handoutFile) {
        return handoutFileDao.findOneHandoutFileByCondition(handoutFile);
    }
    @Transactional(readOnly = true)
    public long findHandoutFileCountByCondition(HandoutFile handoutFile) {
        return handoutFileDao.findHandoutFileCountByCondition(handoutFile);
    }
    @Transactional
    public void updateHandoutFile(HandoutFile handoutFile) {
        handoutFileDao.updateHandoutFile(handoutFile);
    }
    @Transactional
    public void deleteHandoutFile(String id) {
        handoutFileDao.deleteHandoutFile(id);
    }
    @Transactional
    public void deleteHandoutFileByCondition(HandoutFile handoutFile) {
        handoutFileDao.deleteHandoutFileByCondition(handoutFile);
    }
    @Transactional
    public void batchSaveHandoutFile(List<HandoutFile> handoutFiles){
        handoutFileDao.batchSaveHandoutFile(handoutFiles);
    }

}
