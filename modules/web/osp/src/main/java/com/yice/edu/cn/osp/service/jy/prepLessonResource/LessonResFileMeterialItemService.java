package com.yice.edu.cn.osp.service.jy.prepLessonResource;

import com.yice.edu.cn.common.pojo.jy.prepLessonResource.LessonResFileMeterialItem;
import com.yice.edu.cn.osp.feignClient.jy.prepLessonResource.LessonResFileMeterialItemFeign;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LessonResFileMeterialItemService {
    @Autowired
    private LessonResFileMeterialItemFeign lessonResFileMeterialItemFeign;

    public LessonResFileMeterialItem findLessonResFileMeterialItemById(String id) {
        return lessonResFileMeterialItemFeign.findLessonResFileMeterialItemById(id);
    }

    public LessonResFileMeterialItem saveLessonResFileMeterialItem(LessonResFileMeterialItem lessonResFileMeterialItem) {
        return lessonResFileMeterialItemFeign.saveLessonResFileMeterialItem(lessonResFileMeterialItem);
    }

    public List<LessonResFileMeterialItem> findLessonResFileMeterialItemListByCondition(LessonResFileMeterialItem lessonResFileMeterialItem) {
        return lessonResFileMeterialItemFeign.findLessonResFileMeterialItemListByCondition(lessonResFileMeterialItem);
    }

    public LessonResFileMeterialItem findOneLessonResFileMeterialItemByCondition(LessonResFileMeterialItem lessonResFileMeterialItem) {
        return lessonResFileMeterialItemFeign.findOneLessonResFileMeterialItemByCondition(lessonResFileMeterialItem);
    }

    public long findLessonResFileMeterialItemCountByCondition(LessonResFileMeterialItem lessonResFileMeterialItem) {
        return lessonResFileMeterialItemFeign.findLessonResFileMeterialItemCountByCondition(lessonResFileMeterialItem);
    }

    public void updateLessonResFileMeterialItem(LessonResFileMeterialItem lessonResFileMeterialItem) {
        lessonResFileMeterialItemFeign.updateLessonResFileMeterialItem(lessonResFileMeterialItem);
    }

    public void deleteLessonResFileMeterialItem(String id) {
        lessonResFileMeterialItemFeign.deleteLessonResFileMeterialItem(id);
    }

    public void deleteLessonResFileMeterialItemByCondition(LessonResFileMeterialItem lessonResFileMeterialItem) {
        lessonResFileMeterialItemFeign.deleteLessonResFileMeterialItemByCondition(lessonResFileMeterialItem);
    }
}
