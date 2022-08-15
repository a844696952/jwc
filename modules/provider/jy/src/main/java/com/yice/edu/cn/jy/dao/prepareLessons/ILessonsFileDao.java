package com.yice.edu.cn.jy.dao.prepareLessons;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.yice.edu.cn.common.pojo.jy.prepareLessons.LessonsFile;

@Mapper
public interface ILessonsFileDao {

    int deleteLessonsFileByCondition(LessonsFile lessonsFile);

    int batchSaveLessonsFile(List<LessonsFile> lessonsFiles);
    
    int deleteLessonsFile(@Param("id") String id);
}
