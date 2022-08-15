package com.yice.edu.cn.cc.service.cloudCourse;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yice.edu.cn.cc.feignClient.cloudCourse.CloudCourseResourceFeign;
import com.yice.edu.cn.common.pojo.cc.cloudCourse.CloudCourseResource;

@Service
public class CloudCourseResourceService {
    @Autowired
    private CloudCourseResourceFeign cloudCourseResourceFeign;

    public CloudCourseResource findCloudCourseResourceById(String id) {
        return cloudCourseResourceFeign.findCloudCourseResourceById(id);
    }

    public CloudCourseResource saveCloudCourseResource(CloudCourseResource cloudCourseResource) {
        return cloudCourseResourceFeign.saveCloudCourseResource(cloudCourseResource);
    }

    public List<CloudCourseResource> findCloudCourseResourceListByCondition(CloudCourseResource cloudCourseResource) {
        return cloudCourseResourceFeign.findCloudCourseResourceListByCondition(cloudCourseResource);
    }

    public CloudCourseResource findOneCloudCourseResourceByCondition(CloudCourseResource cloudCourseResource) {
        return cloudCourseResourceFeign.findOneCloudCourseResourceByCondition(cloudCourseResource);
    }

    public long findCloudCourseResourceCountByCondition(CloudCourseResource cloudCourseResource) {
        return cloudCourseResourceFeign.findCloudCourseResourceCountByCondition(cloudCourseResource);
    }

    public void updateCloudCourseResource(CloudCourseResource cloudCourseResource) {
        cloudCourseResourceFeign.updateCloudCourseResource(cloudCourseResource);
    }

    public void deleteCloudCourseResource(String id) {
        cloudCourseResourceFeign.deleteCloudCourseResource(id);
    }

    public void deleteCloudCourseResourceByCondition(CloudCourseResource cloudCourseResource) {
        cloudCourseResourceFeign.deleteCloudCourseResourceByCondition(cloudCourseResource);
    }
}
