package com.yice.edu.cn.bmp.service.prepLessonResource;

import com.yice.edu.cn.bmp.feignClient.prepLessonResource.PrepLessonResourceFileFeign;
import com.yice.edu.cn.common.pojo.jy.prepLessonResource.PrepLessonResourceFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrepLessonResourceFileService {
    @Autowired
    private PrepLessonResourceFileFeign prepLessonResourceFileFeign;

    public PrepLessonResourceFile findPrepLessonResourceFileById(String id) {
        return prepLessonResourceFileFeign.findPrepLessonResourceFileById(id);
    }

    public PrepLessonResourceFile savePrepLessonResourceFile(PrepLessonResourceFile prepLessonResourceFile) {
        return prepLessonResourceFileFeign.savePrepLessonResourceFile(prepLessonResourceFile);
    }

    public List<PrepLessonResourceFile> findPrepLessonResourceFileListByCondition(PrepLessonResourceFile prepLessonResourceFile) {
        return prepLessonResourceFileFeign.findPrepLessonResourceFileListByCondition(prepLessonResourceFile);
    }
    public List<PrepLessonResourceFile> findPrepLessonResourceFileListByCondition2(PrepLessonResourceFile prepLessonResourceFile) {
        return prepLessonResourceFileFeign.findPrepLessonResourceFileListByCondition2(prepLessonResourceFile);
    }

    public PrepLessonResourceFile findOnePrepLessonResourceFileByCondition(PrepLessonResourceFile prepLessonResourceFile) {
        return prepLessonResourceFileFeign.findOnePrepLessonResourceFileByCondition(prepLessonResourceFile);
    }

    public long findPrepLessonResourceFileCountByCondition(PrepLessonResourceFile prepLessonResourceFile) {
        return prepLessonResourceFileFeign.findPrepLessonResourceFileCountByCondition(prepLessonResourceFile);
    }
    public long findPrepLessonResourceFileCountByCondition2(PrepLessonResourceFile prepLessonResourceFile) {
        return prepLessonResourceFileFeign.findPrepLessonResourceFileCountByCondition2(prepLessonResourceFile);
    }
    public void updatePrepLessonResourceFile(PrepLessonResourceFile prepLessonResourceFile) {
        prepLessonResourceFileFeign.updatePrepLessonResourceFile(prepLessonResourceFile);
    }

    public void deletePrepLessonResourceFile(String id) {
        prepLessonResourceFileFeign.deletePrepLessonResourceFile(id);
    }

    public void deletePrepLessonResourceFileByCondition(PrepLessonResourceFile prepLessonResourceFile) {
        prepLessonResourceFileFeign.deletePrepLessonResourceFileByCondition(prepLessonResourceFile);
    }

    public void downloadCountChange(String id) {
        prepLessonResourceFileFeign.downloadCountChange(id);
    }
    public void numLookChange(String id) {
        prepLessonResourceFileFeign.numLookChange(id);
    }
    public long findMatFilesCountByMatItemid(PrepLessonResourceFile prepLessonResourceFile) {
        return prepLessonResourceFileFeign.findMatFilesCountByMatItemid(prepLessonResourceFile);
    }
    public List<PrepLessonResourceFile> findMatFileListByMatItemid(PrepLessonResourceFile prepLessonResourceFile) {
        return prepLessonResourceFileFeign.findMatFileListByMatItemid(prepLessonResourceFile);
    }
    public List<PrepLessonResourceFile> findMatFileListByCondition3(PrepLessonResourceFile prepLessonResourceFile) {
        return prepLessonResourceFileFeign.findMatFileListByCondition3(prepLessonResourceFile);
    }
    public long findMatFilesCountByCondition3(PrepLessonResourceFile prepLessonResourceFile) {
        return prepLessonResourceFileFeign.findMatFilesCountByCondition3(prepLessonResourceFile);
    }

}
