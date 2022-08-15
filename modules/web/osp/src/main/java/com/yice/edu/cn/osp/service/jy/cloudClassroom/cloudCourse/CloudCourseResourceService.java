package com.yice.edu.cn.osp.service.jy.cloudClassroom.cloudCourse;


import com.yice.edu.cn.common.pojo.cc.cloudCourse.CloudCourseResource;
import com.yice.edu.cn.osp.feignClient.jy.cloudClassroom.cloudCourse.CloudCourseFileResourceFeign;
import com.yice.edu.cn.osp.feignClient.jy.cloudClassroom.cloudCourse.CloudCourseResourceFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CloudCourseResourceService {
    @Autowired
    private CloudCourseResourceFeign cloudCourseResourceFeign;
    @Autowired
    private CloudCourseFileResourceFeign cCFileResourceFeign;

    public CloudCourseResource findCloudCourseResourceById(String id) {
        return cloudCourseResourceFeign.findCloudCourseResourceById(id);
    }

    public CloudCourseResource saveCloudCourseResource(CloudCourseResource cloudCourseResource) {
        return cloudCourseResourceFeign.saveCloudCourseResource(cloudCourseResource);
    }

    public List<CloudCourseResource> findCloudCourseResourceListByCondition(CloudCourseResource cloudCourseResource) {
        return cloudCourseResourceFeign.findCloudCourseResourceListByCondition(cloudCourseResource);
    }

    public List<CloudCourseResource> findRecordingAndBroadcastingResources(CloudCourseResource cloudCourseResource) {
        return cloudCourseResourceFeign.findRecordingAndBroadcastingResources(cloudCourseResource);
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

    public void deleteCloudCourseResourceRe(String id) {

        cloudCourseResourceFeign.deleteCloudCourseResourceRe(id);
    }

    public List<CloudCourseResource> findResourceRecordByCondition(CloudCourseResource cloudCourseResource) {
        return cloudCourseResourceFeign.findResourceRecordByCondition(cloudCourseResource);
    }

    public void deleteResourceMsg(String id) {

        cloudCourseResourceFeign.deleteResourceMsg(id);
    }
}
