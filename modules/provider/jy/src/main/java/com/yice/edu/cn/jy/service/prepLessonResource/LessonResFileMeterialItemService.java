package com.yice.edu.cn.jy.service.prepLessonResource;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.jy.prepLessonResource.LessonResFileMeterialItem;
import com.yice.edu.cn.jy.dao.prepLessonResource.ILessonResFileMeterialItemDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LessonResFileMeterialItemService {
    @Autowired
    private ILessonResFileMeterialItemDao lessonResFileMeterialItemDao;
    @Autowired
    private SequenceId sequenceId;

    @Transactional(readOnly = true)
    public LessonResFileMeterialItem findLessonResFileMeterialItemById(String id) {
        return lessonResFileMeterialItemDao.findLessonResFileMeterialItemById(id);
    }
    @Transactional
    public void saveLessonResFileMeterialItem(LessonResFileMeterialItem lessonResFileMeterialItem) {
        lessonResFileMeterialItem.setId(sequenceId.nextId());
        lessonResFileMeterialItemDao.saveLessonResFileMeterialItem(lessonResFileMeterialItem);
    }
    @Transactional(readOnly = true)
    public List<LessonResFileMeterialItem> findLessonResFileMeterialItemListByCondition(LessonResFileMeterialItem lessonResFileMeterialItem) {
        return lessonResFileMeterialItemDao.findLessonResFileMeterialItemListByCondition(lessonResFileMeterialItem);
    }
    @Transactional(readOnly = true)
    public LessonResFileMeterialItem findOneLessonResFileMeterialItemByCondition(LessonResFileMeterialItem lessonResFileMeterialItem) {
        return lessonResFileMeterialItemDao.findOneLessonResFileMeterialItemByCondition(lessonResFileMeterialItem);
    }
    @Transactional(readOnly = true)
    public long findLessonResFileMeterialItemCountByCondition(LessonResFileMeterialItem lessonResFileMeterialItem) {
        return lessonResFileMeterialItemDao.findLessonResFileMeterialItemCountByCondition(lessonResFileMeterialItem);
    }
    @Transactional
    public void updateLessonResFileMeterialItem(LessonResFileMeterialItem lessonResFileMeterialItem) {
        lessonResFileMeterialItemDao.updateLessonResFileMeterialItem(lessonResFileMeterialItem);
    }
    @Transactional
    public void deleteLessonResFileMeterialItem(String id) {
        lessonResFileMeterialItemDao.deleteLessonResFileMeterialItem(id);
    }
    @Transactional
    public void deleteLessonResFileMeterialItemByCondition(LessonResFileMeterialItem lessonResFileMeterialItem) {
        lessonResFileMeterialItemDao.deleteLessonResFileMeterialItemByCondition(lessonResFileMeterialItem);
    }
    @Transactional
    public void batchSaveLessonResFileMeterialItem(List<LessonResFileMeterialItem> lessonResFileMeterialItems){
        lessonResFileMeterialItems.forEach(lessonResFileMeterialItem -> lessonResFileMeterialItem.setId(sequenceId.nextId()));
        lessonResFileMeterialItemDao.batchSaveLessonResFileMeterialItem(lessonResFileMeterialItems);
    }

}
