package com.yice.edu.cn.osp.service.jy.cloudClassroom.cloudCourse;

import com.yice.edu.cn.common.pojo.cc.cloudCourse.CloudCourseFileResource;
import com.yice.edu.cn.common.pojo.cc.cloudCourse.CloudCourseResource;
import com.yice.edu.cn.osp.feignClient.jy.cloudClassroom.cloudCourse.CloudCourseFileResourceFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CloudCourseFileResourceService {
    @Autowired
    private CloudCourseFileResourceFeign cloudCourseFileResourceFeign;
    /*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    public CloudCourseFileResource findCloudCourseFileResourceById(String id) {
        return cloudCourseFileResourceFeign.findCloudCourseFileResourceById(id);
    }

    public CloudCourseFileResource saveCloudCourseFileResource(CloudCourseFileResource CloudCourseFileResource) {
        return cloudCourseFileResourceFeign.saveCloudCourseFileResource(CloudCourseFileResource);
    }

    public void batchSaveCloudCourseFileResource(List<CloudCourseFileResource> CloudCourseFileResources){
        cloudCourseFileResourceFeign.batchSaveCloudCourseFileResource(CloudCourseFileResources);
    }

    public List<CloudCourseFileResource> findCloudCourseFileResourceListByCondition(CloudCourseFileResource CloudCourseFileResource) {
        return cloudCourseFileResourceFeign.findCloudCourseFileResourceListByCondition(CloudCourseFileResource);
    }

    public CloudCourseFileResource findOneCloudCourseFileResourceByCondition(CloudCourseFileResource CloudCourseFileResource) {
        return cloudCourseFileResourceFeign.findOneCloudCourseFileResourceByCondition(CloudCourseFileResource);
    }

    public long findCloudCourseFileResourceCountByCondition(CloudCourseFileResource CloudCourseFileResource) {
        return cloudCourseFileResourceFeign.findCloudCourseFileResourceCountByCondition(CloudCourseFileResource);
    }

    public void updateCloudCourseFileResource(CloudCourseFileResource CloudCourseFileResource) {
        cloudCourseFileResourceFeign.updateCloudCourseFileResource(CloudCourseFileResource);
    }

    public void updateCloudCourseFileResourceForAll(CloudCourseFileResource CloudCourseFileResource) {
        cloudCourseFileResourceFeign.updateCloudCourseFileResourceForAll(CloudCourseFileResource);
    }

    public void deleteCloudCourseFileResource(String id) {
        cloudCourseFileResourceFeign.deleteCloudCourseFileResource(id);
    }

    public void deleteCloudCourseFileResourceByCondition(CloudCourseFileResource CloudCourseFileResource) {
        cloudCourseFileResourceFeign.deleteCloudCourseFileResourceByCondition(CloudCourseFileResource);
    }
    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
    public CloudCourseResource findCloudCourseFileResourceByCloudCourseId(String id) {
        return cloudCourseFileResourceFeign.findCloudCourseFileResourceByCloudCourseId(id);
    }

    public void deleteCloudCourseFileResourceByList(List<String> ids) {
        cloudCourseFileResourceFeign.deleteCloudCourseFileResourceByList(ids);
    }
}
