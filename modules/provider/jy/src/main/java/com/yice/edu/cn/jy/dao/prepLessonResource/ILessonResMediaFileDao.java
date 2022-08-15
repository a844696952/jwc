package com.yice.edu.cn.jy.dao.prepLessonResource;

import java.util.List;

import com.yice.edu.cn.common.pojo.jy.prepLessonResource.LessonResMediaFile;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ILessonResMediaFileDao {
    List<LessonResMediaFile> findLessonResMediaFileListByCondition(LessonResMediaFile lessonResMediaFile);
    List<LessonResMediaFile> findLessonResMediaFileListByCondition2(LessonResMediaFile lessonResMediaFile);

    LessonResMediaFile findOneLessonResMediaFileByCondition(LessonResMediaFile lessonResMediaFile);

    long findLessonResMediaFileCountByCondition(LessonResMediaFile lessonResMediaFile);
    long findLessonResMediaFileCountByCondition2(LessonResMediaFile lessonResMediaFile);

    LessonResMediaFile findLessonResMediaFileById(@Param("id") String id);

    void saveLessonResMediaFile(LessonResMediaFile lessonResMediaFile);

    void updateLessonResMediaFile(LessonResMediaFile lessonResMediaFile);

    void deleteLessonResMediaFile(@Param("id") String id);

    void deleteLessonResMediaFileByCondition(LessonResMediaFile lessonResMediaFile);

    void batchSaveLessonResMediaFile(List<LessonResMediaFile> lessonResMediaFiles);

    void numLookChange(@Param("id") String id);
    void downloadCountChange(@Param("id") String id);

}
