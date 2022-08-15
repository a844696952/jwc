package com.yice.edu.cn.jy.dao.prepLessonResource;

import java.util.List;

import com.yice.edu.cn.common.pojo.jy.prepLessonResource.CollectorFile;
import com.yice.edu.cn.common.pojo.jy.prepLessonResource.PrepLessonResourceFile;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ICollectorFileDao {
    List<CollectorFile> findCollectorFileListByCondition(CollectorFile collectorFile);

    long findCollectorFileCountByCondition(CollectorFile collectorFile);

    CollectorFile findOneCollectorFileByCondition(CollectorFile collectorFile);

    CollectorFile findCollectorFileById(@Param("id") String id);

    void saveCollectorFile(CollectorFile collectorFile);

    void updateCollectorFile(CollectorFile collectorFile);

    void deleteCollectorFile(@Param("id") String id);

    void deleteCollectorFileByCondition(CollectorFile collectorFile);

    void batchSaveCollectorFile(List<CollectorFile> collectorFiles);

    List<PrepLessonResourceFile> findCollectorFilesByConditionToApp(CollectorFile collectorFile);

    long findCollectorFileCountByConditionToApp(CollectorFile collectorFile);

}
