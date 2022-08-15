package com.yice.edu.cn.jy.service.prepLessonResource;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.jy.prepLessonResource.LessonResFileMeterialItem;
import com.yice.edu.cn.common.pojo.jy.prepLessonResource.PrepLessonResourceFile;
import com.yice.edu.cn.jy.dao.prepLessonResource.ILessonResFileMeterialItemDao;
import com.yice.edu.cn.jy.dao.prepLessonResource.IPrepLessonResourceFileDao;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class PrepLessonResourceFileService {
    @Autowired
    private IPrepLessonResourceFileDao prepLessonResourceFileDao;
    @Autowired
    private ILessonResFileMeterialItemDao lessonResFileMeterialItemDao;
    @Autowired
    private SequenceId sequenceId;
    @Autowired
    private SequenceId sequenceId2;

    @Transactional(readOnly = true)
    public PrepLessonResourceFile findPrepLessonResourceFileById(String id) {
        return prepLessonResourceFileDao.findPrepLessonResourceFileById(id);
    }
    @Transactional
    public void savePrepLessonResourceFile(PrepLessonResourceFile prepLessonResourceFile) {
        //prepLessonResourceFile.setId(sequenceId.nextId());
        List<PrepLessonResourceFile> list=prepLessonResourceFile.getFiles();
        list.forEach(p -> {
            p.setId(sequenceId.nextId());
            p.setAdminName(prepLessonResourceFile.getAdminName());
            p.setAdminId(prepLessonResourceFile.getAdminId());
        });
        prepLessonResourceFileDao.batchSavePrepLessonResourceFile(prepLessonResourceFile.getFiles());
        //关联表查询
        LessonResFileMeterialItem lm = prepLessonResourceFile.getLessonResFileMeterialItem();
        List<LessonResFileMeterialItem> list1 = new ArrayList<>();
        for (PrepLessonResourceFile p : list){
            LessonResFileMeterialItem lfmi= new LessonResFileMeterialItem();
            BeanUtils.copyProperties(lm,lfmi);
            if (lfmi.getRole()==null||lfmi.getRole().equals("")){
                lfmi.setRole("0");
            }
            lfmi.setId(sequenceId.nextId());//主键id
            lfmi.setResourceFileId(p.getId());//文件id
            list1.add(lfmi);
        }
         lessonResFileMeterialItemDao.batchSaveLessonResFileMeterialItem(list1);

    }
    @Transactional(readOnly = true)
    public List<PrepLessonResourceFile> findPrepLessonResourceFileListByCondition(PrepLessonResourceFile prepLessonResourceFile) {
        return prepLessonResourceFileDao.findPrepLessonResourceFileListByCondition(prepLessonResourceFile);
    }
    @Transactional(readOnly = true)
    public List<PrepLessonResourceFile> findPrepLessonResourceFileListByCondition2(PrepLessonResourceFile prepLessonResourceFile) {
        return prepLessonResourceFileDao.findPrepLessonResourceFileListByCondition2(prepLessonResourceFile);
    }
    @Transactional(readOnly = true)
    public PrepLessonResourceFile findOnePrepLessonResourceFileByCondition(PrepLessonResourceFile prepLessonResourceFile) {
        return prepLessonResourceFileDao.findOnePrepLessonResourceFileByCondition(prepLessonResourceFile);
    }
    @Transactional(readOnly = true)
    public long findPrepLessonResourceFileCountByCondition(PrepLessonResourceFile prepLessonResourceFile) {
        return prepLessonResourceFileDao.findPrepLessonResourceFileCountByCondition(prepLessonResourceFile);
    }
    @Transactional(readOnly = true)
    public long findPrepLessonResourceFileCountByCondition2(PrepLessonResourceFile prepLessonResourceFile) {
        return prepLessonResourceFileDao.findPrepLessonResourceFileCountByCondition2(prepLessonResourceFile);
    }
    @Transactional
    public void updatePrepLessonResourceFile(PrepLessonResourceFile prepLessonResourceFile) {
        prepLessonResourceFileDao.updatePrepLessonResourceFile(prepLessonResourceFile);
    }
    @Transactional
    public void deletePrepLessonResourceFile(String id) {
        prepLessonResourceFileDao.deletePrepLessonResourceFile(id);
        LessonResFileMeterialItem lrfmi = new LessonResFileMeterialItem();
        lrfmi.setResourceFileId(id);
        lessonResFileMeterialItemDao.deleteLessonResFileMeterialItemByCondition(lrfmi);

    }
    @Transactional
    public void deletePrepLessonResourceFileByCondition(PrepLessonResourceFile prepLessonResourceFile) {
        prepLessonResourceFileDao.deletePrepLessonResourceFileByCondition(prepLessonResourceFile);
    }
    @Transactional
    public void batchSavePrepLessonResourceFile(List<PrepLessonResourceFile> prepLessonResourceFiles){
        prepLessonResourceFiles.forEach(prepLessonResourceFile -> prepLessonResourceFile.setId(sequenceId.nextId()));
        prepLessonResourceFileDao.batchSavePrepLessonResourceFile(prepLessonResourceFiles);
    }
//    downloadCountChange
    @Transactional
    public void downloadCountChange(String id) {
        prepLessonResourceFileDao.downloadCountChange(id);
    }
    @Transactional
    public void numLookChange(String id) {
        prepLessonResourceFileDao.numLookChange(id);
    }
    @Transactional(readOnly = true)
    public List<PrepLessonResourceFile>  findMatFileListByMatItemid(PrepLessonResourceFile prepLessonResourceFile) {
      return   prepLessonResourceFileDao.findMatFileListByMatItemid(prepLessonResourceFile);
    }
    @Transactional(readOnly = true)
    public long findMatFilesCountByMatItemid(PrepLessonResourceFile prepLessonResourceFile) {
         return  prepLessonResourceFileDao.findMatFilesCountByMatItemid(prepLessonResourceFile);
    }
    @Transactional(readOnly = true)
    public List<PrepLessonResourceFile>  findMatFileListByCondition3(PrepLessonResourceFile prepLessonResourceFile) {
        return   prepLessonResourceFileDao.findMatFileListByCondition3(prepLessonResourceFile);
    }
    @Transactional(readOnly = true)
    public long findMatFilesCountByCondition3(PrepLessonResourceFile prepLessonResourceFile) {
        return  prepLessonResourceFileDao.findMatFilesCountByCondition3(prepLessonResourceFile);
    }
}
