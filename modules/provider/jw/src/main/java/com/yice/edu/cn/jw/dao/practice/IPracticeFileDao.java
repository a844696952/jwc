package com.yice.edu.cn.jw.dao.practice;

import java.util.List;

import com.yice.edu.cn.common.pojo.jw.practice.PracticeFile;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IPracticeFileDao {
    List<PracticeFile> findPracticeFileListByCondition(PracticeFile practiceFile);

    PracticeFile findOnePracticeFileByCondition(PracticeFile practiceFile);

    long findPracticeFileCountByCondition(PracticeFile practiceFile);

    PracticeFile findPracticeFileById(@Param("id") String id);


    void savePracticeFile(PracticeFile practiceFile);

    void updatePracticeFile(PracticeFile practiceFile);

    void deletePracticeFile(@Param("id") String id);

    void deletePracticeFileByCondition(PracticeFile practiceFile);

    void batchSavePracticeFile(List<PracticeFile> practiceFiles);

    List<PracticeFile> findPracticeFileListById(@Param("id") String id);
}
