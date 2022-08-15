package com.yice.edu.cn.cc.service.cloudCourse;

import com.yice.edu.cn.cc.feignClient.cloudCourse.CCFileResourceFeign;
import com.yice.edu.cn.common.pojo.cc.cloudCourse.CloudCourseFileResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CCFileResourceService {
    @Autowired
    private CCFileResourceFeign cCFileResourceFeign;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    public CloudCourseFileResource findCCFileResourceById(String id) {
        return cCFileResourceFeign.findCCFileResourceById(id);
    }

    public CloudCourseFileResource saveCCFileResource(CloudCourseFileResource cCFileResource) {
        return cCFileResourceFeign.saveCCFileResource(cCFileResource);
    }

    public void batchSaveCCFileResource(List<CloudCourseFileResource> cCFileResources){
        cCFileResourceFeign.batchSaveCCFileResource(cCFileResources);
    }

    public List<CloudCourseFileResource> findCCFileResourceListByCondition(CloudCourseFileResource cCFileResource) {
        return cCFileResourceFeign.findCCFileResourceListByCondition(cCFileResource);
    }

    public CloudCourseFileResource findOneCCFileResourceByCondition(CloudCourseFileResource cCFileResource) {
        return cCFileResourceFeign.findOneCCFileResourceByCondition(cCFileResource);
    }

    public long findCCFileResourceCountByCondition(CloudCourseFileResource cCFileResource) {
        return cCFileResourceFeign.findCCFileResourceCountByCondition(cCFileResource);
    }

    public void updateCCFileResource(CloudCourseFileResource cCFileResource) {
        cCFileResourceFeign.updateCCFileResource(cCFileResource);
    }

    public void updateCCFileResourceForAll(CloudCourseFileResource cCFileResource) {
        cCFileResourceFeign.updateCCFileResourceForAll(cCFileResource);
    }

    public void deleteCCFileResource(String id) {
        cCFileResourceFeign.deleteCCFileResource(id);
    }

    public void deleteCCFileResourceByCondition(CloudCourseFileResource cCFileResource) {
        cCFileResourceFeign.deleteCCFileResourceByCondition(cCFileResource);
    }
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
