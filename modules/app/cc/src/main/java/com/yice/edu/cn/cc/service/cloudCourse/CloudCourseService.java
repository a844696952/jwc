package com.yice.edu.cn.cc.service.cloudCourse;

import com.yice.edu.cn.cc.feignClient.cloudCourse.CloudCourseFeign;
import com.yice.edu.cn.common.pojo.cc.cloudCourse.CloudCourse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CloudCourseService {
    @Autowired
    private CloudCourseFeign cloudCourseFeign;

    public CloudCourse findCloudCourseById(String id) {
        return cloudCourseFeign.findCloudCourseById(id);
    }

    public CloudCourse saveCloudCourse(CloudCourse cloudCourse) {
        return cloudCourseFeign.saveCloudCourse(cloudCourse);
    }

    public List<CloudCourse> findCloudCourseListByCondition(CloudCourse cloudCourse) {
        return cloudCourseFeign.findCloudCourseListByCondition(cloudCourse);
    }

    public CloudCourse findOneCloudCourseByCondition(CloudCourse cloudCourse) {
        return cloudCourseFeign.findOneCloudCourseByCondition(cloudCourse);
    }

    public long findCloudCourseCountByCondition(CloudCourse cloudCourse) {
        return cloudCourseFeign.findCloudCourseCountByCondition(cloudCourse);
    }

    public void updateCloudCourse(CloudCourse cloudCourse) {
        cloudCourseFeign.updateCloudCourse(cloudCourse);
    }

    public void deleteCloudCourse(String id) {
        cloudCourseFeign.deleteCloudCourse(id);
    }

    public void deleteCloudCourseByCondition(CloudCourse cloudCourse) {
        cloudCourseFeign.deleteCloudCourseByCondition(cloudCourse);
    }

    public List<CloudCourse> findCloudCourseListForTeacher(CloudCourse cloudCourse) {
        return cloudCourseFeign.findCloudCourseListForTeacher(cloudCourse);
    }

    public long findCloudCourseCountForTeacher(CloudCourse cloudCourse) {
        return cloudCourseFeign.findCloudCourseCountForTeacher(cloudCourse);
    }

    public List<CloudCourse> findCloudCourseListForOther(CloudCourse cloudCourse) {
        return cloudCourseFeign.findCloudCourseListForOther(cloudCourse);
    }

    public long findCloudCourseCountForOther(CloudCourse cloudCourse) {
        return cloudCourseFeign.findCloudCourseCountForOther(cloudCourse);
    }
    
    public List<CloudCourse> findCloudCourseListForAll(CloudCourse cloudCourse) {
        return cloudCourseFeign.findCloudCourseListForAll(cloudCourse);
    }
    
    public long findCloudCourseCountForAll(CloudCourse cloudCourse) {
        return cloudCourseFeign.findCloudCourseCountForAll(cloudCourse);
    }
    public Integer findCloudCourseValid(CloudCourse cloudCourse) {
    	return cloudCourseFeign.findCloudCourseValid(cloudCourse);
    }

}
