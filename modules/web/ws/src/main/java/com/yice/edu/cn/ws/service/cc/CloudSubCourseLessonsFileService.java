package com.yice.edu.cn.ws.service.cc;

import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.cc.cloudCourse.CloudSubCourse;
import com.yice.edu.cn.common.pojo.cc.cloudCourse.CloudSubCourseLessonsFile;
import com.yice.edu.cn.common.pojo.cc.cloudCourse.FileVo;
import com.yice.edu.cn.ws.feignClient.cc.CloudSubCourseLessonsFileFeign;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class CloudSubCourseLessonsFileService {

    private Logger log = LoggerFactory.getLogger(CloudSubCourseLessonsFileService.class);

    @Autowired
    private CloudSubCourseService cloudSubCourseService;

    @Autowired
    private CloudSubCourseLessonsFileFeign cloudSubCourseLessonsFileFeign;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    public CloudSubCourseLessonsFile findCloudSubCourseLessonsFileById(String id) {
        return cloudSubCourseLessonsFileFeign.findCloudSubCourseLessonsFileById(id);
    }

    public CloudSubCourseLessonsFile saveCloudSubCourseLessonsFile(CloudSubCourseLessonsFile cloudSubCourseLessonsFile) {
        return cloudSubCourseLessonsFileFeign.saveCloudSubCourseLessonsFile(cloudSubCourseLessonsFile);
    }

    public void batchSaveCloudSubCourseLessonsFile(List<CloudSubCourseLessonsFile> cloudSubCourseLessonsFiles){
        cloudSubCourseLessonsFileFeign.batchSaveCloudSubCourseLessonsFile(cloudSubCourseLessonsFiles);
    }

    public List<CloudSubCourseLessonsFile> findCloudSubCourseLessonsFileListByCondition(CloudSubCourseLessonsFile cloudSubCourseLessonsFile) {
        return cloudSubCourseLessonsFileFeign.findCloudSubCourseLessonsFileListByCondition(cloudSubCourseLessonsFile);
    }

    public CloudSubCourseLessonsFile findOneCloudSubCourseLessonsFileByCondition(CloudSubCourseLessonsFile cloudSubCourseLessonsFile) {
        return cloudSubCourseLessonsFileFeign.findOneCloudSubCourseLessonsFileByCondition(cloudSubCourseLessonsFile);
    }

    public long findCloudSubCourseLessonsFileCountByCondition(CloudSubCourseLessonsFile cloudSubCourseLessonsFile) {
        return cloudSubCourseLessonsFileFeign.findCloudSubCourseLessonsFileCountByCondition(cloudSubCourseLessonsFile);
    }

    public void updateCloudSubCourseLessonsFile(CloudSubCourseLessonsFile cloudSubCourseLessonsFile) {
        cloudSubCourseLessonsFileFeign.updateCloudSubCourseLessonsFile(cloudSubCourseLessonsFile);
    }

    public void updateCloudSubCourseLessonsFileForAll(CloudSubCourseLessonsFile cloudSubCourseLessonsFile) {
        cloudSubCourseLessonsFileFeign.updateCloudSubCourseLessonsFileForAll(cloudSubCourseLessonsFile);
    }

    public void deleteCloudSubCourseLessonsFile(String id) {
        cloudSubCourseLessonsFileFeign.deleteCloudSubCourseLessonsFile(id);
    }

    public void deleteCloudSubCourseLessonsFileByCondition(CloudSubCourseLessonsFile cloudSubCourseLessonsFile) {
        cloudSubCourseLessonsFileFeign.deleteCloudSubCourseLessonsFileByCondition(cloudSubCourseLessonsFile);
    }
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/

    public List<FileVo> getLessonFileList(String groupName){
        CloudSubCourse curCourse = cloudSubCourseService.getCurCourse(groupName);
        if (curCourse == null || curCourse.getTeacher() == null) {
            log.info("课程{}不存在或该课程无授课老师", groupName);
            return null;
        }
        CloudSubCourseLessonsFile cloudSubCourseLessonsFile = new CloudSubCourseLessonsFile();
        cloudSubCourseLessonsFile.setTeacherId(curCourse.getTeacher().getTeacherId());
        List<CloudSubCourseLessonsFile> cloudSubCourseLessonsFileList = cloudSubCourseLessonsFileFeign.findCloudSubCourseLessonsFileListByCondition(cloudSubCourseLessonsFile);

        AtomicInteger index = new AtomicInteger(0);
        List<FileVo> fileVoList = cloudSubCourseLessonsFileList.stream().map(lessonFile -> {
            FileVo fileVo = new FileVo();
            fileVo.setId(index.incrementAndGet());
            String lessonsFilePath = lessonFile.getLessonsFilePath();
            String ext = lessonsFilePath.substring(lessonsFilePath.lastIndexOf(".") + 1);
            fileVo.setName(lessonFile.getLessonsFileName() + "." + ext);
            fileVo.setPath(Constant.RES_PRE + "/" + lessonsFilePath);
            return fileVo;
        }).collect(Collectors.toList());
        return fileVoList;
    }
}
