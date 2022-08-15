package com.yice.edu.cn.jy.service.prepLessonResource;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.jy.prepLessonResource.CollectorFile;
import com.yice.edu.cn.common.pojo.jy.prepLessonResource.PrepLessonResourceFile;
import com.yice.edu.cn.jy.dao.prepLessonResource.ICollectorFileDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CollectorFileService {
    @Autowired
    private ICollectorFileDao collectorFileDao;
    @Autowired
    private SequenceId sequenceId;

    @Transactional(readOnly = true)
    public CollectorFile findCollectorFileById(String id) {
        return collectorFileDao.findCollectorFileById(id);
    }
    @Transactional
    public void saveCollectorFile(CollectorFile collectorFile) {
        collectorFile.setId(sequenceId.nextId());
        collectorFileDao.saveCollectorFile(collectorFile);
    }
    @Transactional(readOnly = true)
    public List<CollectorFile> findCollectorFileListByCondition(CollectorFile collectorFile) {
        return collectorFileDao.findCollectorFileListByCondition(collectorFile);
    }
    @Transactional(readOnly = true)
    public CollectorFile findOneCollectorFileByCondition(CollectorFile collectorFile) {
        return collectorFileDao.findOneCollectorFileByCondition(collectorFile);
    }
    @Transactional(readOnly = true)
    public long findCollectorFileCountByCondition(CollectorFile collectorFile) {
        return collectorFileDao.findCollectorFileCountByCondition(collectorFile);
    }
    @Transactional
    public void updateCollectorFile(CollectorFile collectorFile) {
        collectorFileDao.updateCollectorFile(collectorFile);
    }
    @Transactional
    public void deleteCollectorFile(String id) {
        collectorFileDao.deleteCollectorFile(id);
    }
    @Transactional
    public void deleteCollectorFileByCondition(CollectorFile collectorFile) {
        collectorFileDao.deleteCollectorFileByCondition(collectorFile);
    }
    @Transactional
    public void batchSaveCollectorFile(List<CollectorFile> collectorFiles){
        collectorFiles.forEach(collectorFile -> collectorFile.setId(sequenceId.nextId()));
        collectorFileDao.batchSaveCollectorFile(collectorFiles);
    }
    @Transactional(readOnly = true)
    public List<PrepLessonResourceFile> findCollectorFilesByConditionToApp(CollectorFile collectorFile) {
        return collectorFileDao.findCollectorFilesByConditionToApp(collectorFile);
    }
    @Transactional(readOnly = true)
    public long findCollectorFileCountByConditionToApp(CollectorFile collectorFile) {
        return collectorFileDao.findCollectorFileCountByConditionToApp(collectorFile);
    }
}
