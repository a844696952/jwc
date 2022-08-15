package com.yice.edu.cn.osp.service.jy.cloudClassroom.cloudCourse;

import com.yice.edu.cn.common.pojo.cc.cloudCourse.CloudCoursewareVo;
import com.yice.edu.cn.common.pojo.cc.cloudCourse.CloudSubCourse;
import com.yice.edu.cn.common.pojo.cc.cloudCourse.CloudSubCourseLessonsFile;
import com.yice.edu.cn.osp.feignClient.jy.cloudClassroom.cloudCourse.CloudSubCourseLessonsFileFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CloudSubCourseLessonsFileService {
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
    public List<CloudSubCourse> findCloudSubCourseListByCloudCourseId(String id){
       return cloudSubCourseLessonsFileFeign.findCloudSubCourseListByCloudCourseId(id);
    }
    public void unifiedLessons(List<CloudSubCourseLessonsFile> lessonsFileList){
        cloudSubCourseLessonsFileFeign.unifiedLessons(lessonsFileList);
    }

    public void saveCoursewareListByCondition(CloudCoursewareVo cloudCoursewareVo){
        cloudSubCourseLessonsFileFeign.saveCoursewareListByCondition(cloudCoursewareVo);
    }
}
