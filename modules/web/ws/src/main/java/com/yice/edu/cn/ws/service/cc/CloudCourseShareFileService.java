package com.yice.edu.cn.ws.service.cc;


import com.yice.edu.cn.common.pojo.cc.cloudCourse.CloudCourseShareFile;
import com.yice.edu.cn.ws.feignClient.cc.CloudCourseShareFileFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CloudCourseShareFileService {
    @Autowired
    private CloudCourseShareFileFeign cloudCourseShareFileFeign;

    public CloudCourseShareFile findCloudCourseShareFileById(String id) {
        return cloudCourseShareFileFeign.findCloudCourseShareFileById(id);
    }

    public CloudCourseShareFile saveCloudCourseShareFile(CloudCourseShareFile cloudCourseShareFile) {
        return cloudCourseShareFileFeign.saveCloudCourseShareFile(cloudCourseShareFile);
    }

    public List<CloudCourseShareFile> findCloudCourseShareFileListByCondition(CloudCourseShareFile cloudCourseShareFile) {
        return cloudCourseShareFileFeign.findCloudCourseShareFileListByCondition(cloudCourseShareFile);
    }

    public CloudCourseShareFile findOneCloudCourseShareFileByCondition(CloudCourseShareFile cloudCourseShareFile) {
        return cloudCourseShareFileFeign.findOneCloudCourseShareFileByCondition(cloudCourseShareFile);
    }

    public long findCloudCourseShareFileCountByCondition(CloudCourseShareFile cloudCourseShareFile) {
        return cloudCourseShareFileFeign.findCloudCourseShareFileCountByCondition(cloudCourseShareFile);
    }

    public void updateCloudCourseShareFile(CloudCourseShareFile cloudCourseShareFile) {
        cloudCourseShareFileFeign.updateCloudCourseShareFile(cloudCourseShareFile);
    }

    public void deleteCloudCourseShareFile(String id) {
        cloudCourseShareFileFeign.deleteCloudCourseShareFile(id);
    }

    public void deleteCloudCourseShareFileByCondition(CloudCourseShareFile cloudCourseShareFile) {
        cloudCourseShareFileFeign.deleteCloudCourseShareFileByCondition(cloudCourseShareFile);
    }
}
