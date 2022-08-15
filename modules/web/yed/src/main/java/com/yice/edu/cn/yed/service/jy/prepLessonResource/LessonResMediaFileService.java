package com.yice.edu.cn.yed.service.jy.prepLessonResource;

import com.yice.edu.cn.common.pojo.jy.prepLessonResource.LessonResMediaFile;
import com.yice.edu.cn.yed.feignClient.jy.prepLessonResource.LessonResMediaFileFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LessonResMediaFileService {
    @Autowired
    private LessonResMediaFileFeign lessonResMediaFileFeign;

    public LessonResMediaFile findLessonResMediaFileById(String id) {
        return lessonResMediaFileFeign.findLessonResMediaFileById(id);
    }

    public LessonResMediaFile saveLessonResMediaFile(LessonResMediaFile lessonResMediaFile) {
        return lessonResMediaFileFeign.saveLessonResMediaFile(lessonResMediaFile);
    }

    public List<LessonResMediaFile> findLessonResMediaFileListByCondition(LessonResMediaFile lessonResMediaFile) {
        return lessonResMediaFileFeign.findLessonResMediaFileListByCondition(lessonResMediaFile);
    }
    public List<LessonResMediaFile> findLessonResMediaFileListByCondition2(LessonResMediaFile lessonResMediaFile) {
        return lessonResMediaFileFeign.findLessonResMediaFileListByCondition2(lessonResMediaFile);
    }
    public LessonResMediaFile findOneLessonResMediaFileByCondition(LessonResMediaFile lessonResMediaFile) {
        return lessonResMediaFileFeign.findOneLessonResMediaFileByCondition(lessonResMediaFile);
    }

    public long findLessonResMediaFileCountByCondition(LessonResMediaFile lessonResMediaFile) {
        return lessonResMediaFileFeign.findLessonResMediaFileCountByCondition(lessonResMediaFile);
    }
    public long findLessonResMediaFileCountByCondition2(LessonResMediaFile lessonResMediaFile) {
        return lessonResMediaFileFeign.findLessonResMediaFileCountByCondition2(lessonResMediaFile);
    }
    public void updateLessonResMediaFile(LessonResMediaFile lessonResMediaFile) {
        lessonResMediaFileFeign.updateLessonResMediaFile(lessonResMediaFile);
    }

    public void deleteLessonResMediaFile(String id) {
        lessonResMediaFileFeign.deleteLessonResMediaFile(id);
    }

    public void deleteLessonResMediaFileByCondition(LessonResMediaFile lessonResMediaFile) {
        lessonResMediaFileFeign.deleteLessonResMediaFileByCondition(lessonResMediaFile);
    }
    public void downloadCountChange(String id) {
        lessonResMediaFileFeign.downloadCountChange(id);
    }
    //查看次数+1
    public void numLookChange(String id) {
        lessonResMediaFileFeign.numLookChange(id);
    }

}
