package com.yice.edu.cn.jw.service.practice;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.jw.practice.PracticeFile;
import com.yice.edu.cn.jw.dao.practice.IPracticeFileDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PracticeFileService {
    @Autowired
    private IPracticeFileDao practiceFileDao;
    @Autowired
    private SequenceId sequenceId;

    @Transactional(readOnly = true)
    public PracticeFile findPracticeFileById(String id) {
        return practiceFileDao.findPracticeFileById(id);
    }

    @Transactional
    public void savePracticeFile(PracticeFile practiceFile) {
        practiceFile.setId(sequenceId.nextId());
        practiceFileDao.savePracticeFile(practiceFile);
    }
    @Transactional(readOnly = true)
    public List<PracticeFile> findPracticeFileListByCondition(PracticeFile practiceFile) {
        return practiceFileDao.findPracticeFileListByCondition(practiceFile);
    }
    @Transactional(readOnly = true)
    public PracticeFile findOnePracticeFileByCondition(PracticeFile practiceFile) {
        return practiceFileDao.findOnePracticeFileByCondition(practiceFile);
    }
    @Transactional(readOnly = true)
    public long findPracticeFileCountByCondition(PracticeFile practiceFile) {
        return practiceFileDao.findPracticeFileCountByCondition(practiceFile);
    }
    @Transactional
    public void updatePracticeFile(PracticeFile practiceFile) {
        practiceFileDao.updatePracticeFile(practiceFile);
    }
    @Transactional
    public void deletePracticeFile(String id) {
        practiceFileDao.deletePracticeFile(id);
    }
    @Transactional
    public void deletePracticeFileByCondition(PracticeFile practiceFile) {
        practiceFileDao.deletePracticeFileByCondition(practiceFile);
    }
    @Transactional
    public void batchSavePracticeFile(List<PracticeFile> practiceFiles){
        practiceFiles.forEach(practiceFile -> practiceFile.setId(sequenceId.nextId()));
        practiceFileDao.batchSavePracticeFile(practiceFiles);
    }
    @Transactional
    public List<PracticeFile> findPracticeFileListById(String id){
        return practiceFileDao.findPracticeFileListById(id);
    }

}
