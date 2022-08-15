package com.yice.edu.cn.xw.service.zc.assetsFile;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.xw.zc.assetsFile.AssetsFile;
import com.yice.edu.cn.xw.dao.zc.assetsFile.IAssetsFileDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AssetsFileService {
    @Autowired
    private IAssetsFileDao assetsFileDao;
    @Autowired
    private SequenceId sequenceId;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @Transactional(readOnly = true)
    public AssetsFile findAssetsFileById(String id) {
        return assetsFileDao.findAssetsFileById(id);
    }
    @Transactional
    public void saveAssetsFile(AssetsFile assetsFile) {
        assetsFile.setId(sequenceId.nextId());
        assetsFile.setAssetsThresholdValue(0);
        assetsFileDao.saveAssetsFile(assetsFile);
    }
    @Transactional(readOnly = true)
    public List<AssetsFile> findAssetsFileListByCondition(AssetsFile assetsFile) {
        return assetsFileDao.findAssetsFileListByCondition(assetsFile);
    }
    @Transactional(readOnly = true)
    public AssetsFile findOneAssetsFileByCondition(AssetsFile assetsFile) {
        return assetsFileDao.findOneAssetsFileByCondition(assetsFile);
    }
    @Transactional(readOnly = true)
    public long findAssetsFileCountByCondition(AssetsFile assetsFile) {
        return assetsFileDao.findAssetsFileCountByCondition(assetsFile);
    }
    @Transactional
    public void updateAssetsFile(AssetsFile assetsFile) {
        assetsFileDao.updateAssetsFile(assetsFile);
    }
    @Transactional
    public void updateAssetsFileForAll(AssetsFile assetsFile) {
        assetsFileDao.updateAssetsFileForAll(assetsFile);
    }
    @Transactional
    public void deleteAssetsFile(String id) {
        assetsFileDao.deleteAssetsFile(id);
    }
    @Transactional
    public void deleteAssetsFileByCondition(AssetsFile assetsFile) {
        assetsFileDao.deleteAssetsFileByCondition(assetsFile);
    }
    @Transactional
    public void batchSaveAssetsFile(List<AssetsFile> assetsFiles){
        assetsFiles.forEach(assetsFile -> assetsFile.setId(sequenceId.nextId()));
        assetsFileDao.batchSaveAssetsFile(assetsFiles);
    }
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
