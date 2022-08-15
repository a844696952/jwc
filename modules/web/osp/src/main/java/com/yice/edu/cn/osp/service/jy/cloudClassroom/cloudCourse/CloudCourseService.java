package com.yice.edu.cn.osp.service.jy.cloudClassroom.cloudCourse;

import com.yice.edu.cn.common.pojo.cc.cloudCourse.CloudCourse;
import com.yice.edu.cn.osp.feignClient.jy.cloudClassroom.cloudCourse.CloudCourseFeign;
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


    public List<CloudCourse> findCloudCoursesByConditionOther(CloudCourse cloudCourse) {
        return cloudCourseFeign.findCloudCoursesByConditionOther(cloudCourse);
    }

    public long findCloudCourseCountByConditionOther(CloudCourse cloudCourse) {
        return cloudCourseFeign.findCloudCourseCountByConditionOther(cloudCourse);
    }

    public List<CloudCourse> findCloudCoursesByConditionMine(CloudCourse cloudCourse) {
        return cloudCourseFeign.findCloudCoursesByConditionMine(cloudCourse);
    }

    public long findCloudCourseCountByConditionMine(CloudCourse cloudCourse) {
        return cloudCourseFeign.findCloudCourseCountByConditionMine(cloudCourse);
    }
}
