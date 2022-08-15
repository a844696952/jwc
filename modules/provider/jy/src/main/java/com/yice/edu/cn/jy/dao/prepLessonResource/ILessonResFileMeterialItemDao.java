package com.yice.edu.cn.jy.dao.prepLessonResource;

import java.util.List;

import com.yice.edu.cn.common.pojo.jy.prepLessonResource.LessonResFileMeterialItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ILessonResFileMeterialItemDao {
    List<LessonResFileMeterialItem> findLessonResFileMeterialItemListByCondition(LessonResFileMeterialItem lessonResFileMeterialItem);

    long findLessonResFileMeterialItemCountByCondition(LessonResFileMeterialItem lessonResFileMeterialItem);

    LessonResFileMeterialItem findOneLessonResFileMeterialItemByCondition(LessonResFileMeterialItem lessonResFileMeterialItem);

    LessonResFileMeterialItem findLessonResFileMeterialItemById(@Param("id") String id);

    void saveLessonResFileMeterialItem(LessonResFileMeterialItem lessonResFileMeterialItem);

    void updateLessonResFileMeterialItem(LessonResFileMeterialItem lessonResFileMeterialItem);

    void deleteLessonResFileMeterialItem(@Param("id") String id);

    void deleteLessonResFileMeterialItemByCondition(LessonResFileMeterialItem lessonResFileMeterialItem);

    void batchSaveLessonResFileMeterialItem(List<LessonResFileMeterialItem> lessonResFileMeterialItems);
}
