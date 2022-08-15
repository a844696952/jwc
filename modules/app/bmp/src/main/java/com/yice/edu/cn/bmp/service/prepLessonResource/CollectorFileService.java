package com.yice.edu.cn.bmp.service.prepLessonResource;

import com.yice.edu.cn.bmp.feignClient.prepLessonResource.CollectorFileFeign;
import com.yice.edu.cn.common.pojo.jy.prepLessonResource.CollectorFile;
import com.yice.edu.cn.common.pojo.jy.prepLessonResource.PrepLessonResourceFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CollectorFileService {
    @Autowired
    private CollectorFileFeign collectorFileFeign;

    public CollectorFile findCollectorFileById(String id) {
        return collectorFileFeign.findCollectorFileById(id);
    }

    public CollectorFile saveCollectorFile(CollectorFile collectorFile) {
        return collectorFileFeign.saveCollectorFile(collectorFile);
    }

    public List<CollectorFile> findCollectorFileListByCondition(CollectorFile collectorFile) {
        return collectorFileFeign.findCollectorFileListByCondition(collectorFile);
    }

    public CollectorFile findOneCollectorFileByCondition(CollectorFile collectorFile) {
        return collectorFileFeign.findOneCollectorFileByCondition(collectorFile);
    }

    public long findCollectorFileCountByCondition(CollectorFile collectorFile) {
        return collectorFileFeign.findCollectorFileCountByCondition(collectorFile);
    }

    public void updateCollectorFile(CollectorFile collectorFile) {
        collectorFileFeign.updateCollectorFile(collectorFile);
    }

    public void deleteCollectorFile(String id) {
        collectorFileFeign.deleteCollectorFile(id);
    }

    public void deleteCollectorFileByCondition(CollectorFile collectorFile) {
        collectorFileFeign.deleteCollectorFileByCondition(collectorFile);
    }

    public List<PrepLessonResourceFile> findCollectorFilesByConditionToApp(CollectorFile collectorFile) {
        return collectorFileFeign.findCollectorFilesByConditionToApp(collectorFile);
    }
    public long findCollectorFileCountByConditionToApp(CollectorFile collectorFile) {
        return collectorFileFeign.findCollectorFileCountByConditionToApp(collectorFile);
    }
}
