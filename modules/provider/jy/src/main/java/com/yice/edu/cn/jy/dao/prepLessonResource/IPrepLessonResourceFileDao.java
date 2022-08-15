package com.yice.edu.cn.jy.dao.prepLessonResource;

import java.util.List;

import com.yice.edu.cn.common.pojo.jy.prepLessonResource.PrepLessonResourceFile;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IPrepLessonResourceFileDao {
    List<PrepLessonResourceFile> findPrepLessonResourceFileListByCondition(PrepLessonResourceFile prepLessonResourceFile);

    List<PrepLessonResourceFile> findPrepLessonResourceFileListByCondition2(PrepLessonResourceFile prepLessonResourceFile);

    PrepLessonResourceFile findOnePrepLessonResourceFileByCondition(PrepLessonResourceFile prepLessonResourceFile);

    long findPrepLessonResourceFileCountByCondition(PrepLessonResourceFile prepLessonResourceFile);

    long findPrepLessonResourceFileCountByCondition2(PrepLessonResourceFile prepLessonResourceFile);

    PrepLessonResourceFile findPrepLessonResourceFileById(@Param("id") String id);

    void savePrepLessonResourceFile(PrepLessonResourceFile prepLessonResourceFile);

    void updatePrepLessonResourceFile(PrepLessonResourceFile prepLessonResourceFile);

    void deletePrepLessonResourceFile(@Param("id") String id);

    void deletePrepLessonResourceFileByCondition(PrepLessonResourceFile prepLessonResourceFile);

    void batchSavePrepLessonResourceFile(List<PrepLessonResourceFile> prepLessonResourceFiles);

//    downloadCountChange
    void downloadCountChange(@Param("id") String id);
    void numLookChange(@Param("id") String id);

    List<PrepLessonResourceFile> findMatFileListByMatItemid(PrepLessonResourceFile prepLessonResourceFile);
    long findMatFilesCountByMatItemid(PrepLessonResourceFile prepLessonResourceFile);


    List<PrepLessonResourceFile> findMatFileListByCondition3(PrepLessonResourceFile prepLessonResourceFile);
    long findMatFilesCountByCondition3(PrepLessonResourceFile prepLessonResourceFile);

}
