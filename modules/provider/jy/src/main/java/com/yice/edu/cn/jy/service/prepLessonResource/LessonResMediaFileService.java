package com.yice.edu.cn.jy.service.prepLessonResource;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.jy.prepLessonResource.LessonResFileMeterialItem;
import com.yice.edu.cn.common.pojo.jy.prepLessonResource.LessonResMediaFile;
import com.yice.edu.cn.jy.dao.prepLessonResource.ILessonResFileMeterialItemDao;
import com.yice.edu.cn.jy.dao.prepLessonResource.ILessonResMediaFileDao;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class LessonResMediaFileService {
    @Autowired
    private ILessonResMediaFileDao lessonResMediaFileDao;
    @Autowired
    private ILessonResFileMeterialItemDao lessonResFileMeterialItemDao;
    @Autowired
    private SequenceId sequenceId;

    @Transactional(readOnly = true)
    public LessonResMediaFile findLessonResMediaFileById(String id) {
        return lessonResMediaFileDao.findLessonResMediaFileById(id);
    }
    @Transactional
    public void saveLessonResMediaFile(LessonResMediaFile lessonResMediaFile) {
        System.out.println(lessonResMediaFile);
        List<LessonResMediaFile> list=lessonResMediaFile.getFiles();
        list.forEach(p -> {
            p.setId(sequenceId.nextId());

            p.setAdminName(lessonResMediaFile.getAdminName());
            p.setAdminId(lessonResMediaFile.getAdminId());
            p.setCover(lessonResMediaFile.getCover());
            p.setSpeaker(lessonResMediaFile.getSpeaker());

        });
        lessonResMediaFileDao.batchSaveLessonResMediaFile(lessonResMediaFile.getFiles());

        LessonResFileMeterialItem lm = lessonResMediaFile.getLessonResFileMeterialItem();
        List<LessonResFileMeterialItem> list1 = new ArrayList<>();
        for (LessonResMediaFile p : list){
            LessonResFileMeterialItem lfmi= new LessonResFileMeterialItem();
            BeanUtils.copyProperties(lm,lfmi);
            if (lfmi.getRole()==null||lfmi.getRole().equals("")){
                lfmi.setRole("0");
            }
            lfmi.setResourceFileId(p.getId());//文件id
            lfmi.setId(sequenceId.nextId());//主键id
            list1.add(lfmi);
        }
        lessonResFileMeterialItemDao.batchSaveLessonResFileMeterialItem(list1);
    }
    @Transactional(readOnly = true)
    public List<LessonResMediaFile> findLessonResMediaFileListByCondition(LessonResMediaFile lessonResMediaFile) {
        return lessonResMediaFileDao.findLessonResMediaFileListByCondition(lessonResMediaFile);
    }
    @Transactional(readOnly = true)
    public List<LessonResMediaFile> findLessonResMediaFileListByCondition2(LessonResMediaFile lessonResMediaFile) {
        return lessonResMediaFileDao.findLessonResMediaFileListByCondition2(lessonResMediaFile);
    }
    @Transactional(readOnly = true)
    public LessonResMediaFile findOneLessonResMediaFileByCondition(LessonResMediaFile lessonResMediaFile) {
        return lessonResMediaFileDao.findOneLessonResMediaFileByCondition(lessonResMediaFile);
    }
    @Transactional(readOnly = true)
    public long findLessonResMediaFileCountByCondition(LessonResMediaFile lessonResMediaFile) {
        return lessonResMediaFileDao.findLessonResMediaFileCountByCondition(lessonResMediaFile);
    }
    @Transactional(readOnly = true)
    public long findLessonResMediaFileCountByCondition2(LessonResMediaFile lessonResMediaFile) {
        return lessonResMediaFileDao.findLessonResMediaFileCountByCondition2(lessonResMediaFile);
    }
    @Transactional
    public void updateLessonResMediaFile(LessonResMediaFile lessonResMediaFile) {
        lessonResMediaFileDao.updateLessonResMediaFile(lessonResMediaFile);
    }
    @Transactional
    public void deleteLessonResMediaFile(String id) {
        lessonResMediaFileDao.deleteLessonResMediaFile(id);
        LessonResFileMeterialItem lrfmi = new LessonResFileMeterialItem();
        lrfmi.setResourceFileId(id);
        lessonResFileMeterialItemDao.deleteLessonResFileMeterialItemByCondition(lrfmi);

    }
    @Transactional
    public void deleteLessonResMediaFileByCondition(LessonResMediaFile lessonResMediaFile) {
        lessonResMediaFileDao.deleteLessonResMediaFileByCondition(lessonResMediaFile);
    }
    @Transactional
    public void batchSaveLessonResMediaFile(List<LessonResMediaFile> lessonResMediaFiles){
        lessonResMediaFiles.forEach(lessonResMediaFile -> lessonResMediaFile.setId(sequenceId.nextId()));
        lessonResMediaFileDao.batchSaveLessonResMediaFile(lessonResMediaFiles);
    }
    @Transactional
    public void downloadCountChange(String id) {
        lessonResMediaFileDao.downloadCountChange(id);
    }
    @Transactional
    public void numLookChange(String id) {
        lessonResMediaFileDao.numLookChange(id);
    }
}
