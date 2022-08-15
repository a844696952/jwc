package com.yice.edu.cn.ws.service.cc;

import com.yice.edu.cn.common.pojo.cc.cloudCourse.CloudCourse;
import com.yice.edu.cn.common.pojo.cc.cloudCourse.CloudSubCourse;
import com.yice.edu.cn.ws.feignClient.cc.CloudSubCourseFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CloudSubCourseService {

    @Autowired
    private CloudSubCourseFeign cloudSubCourseFeign;

    public CloudSubCourse findCloudSubCourseById(String id) {
        return cloudSubCourseFeign.findCloudSubCourseById(id);
    }

    public CloudSubCourse findOneCloudSubCourseByCondition(CloudSubCourse cloudSubCourse) {
        return cloudSubCourseFeign.findOneCloudSubCourseByCondition(cloudSubCourse);
    }

    public List<CloudSubCourse> findCloudSubCourseListByLoginIdAndNow(CloudSubCourse cloudSubCourse) {
        List<CloudSubCourse> cloudSubCourseList= cloudSubCourseFeign.findCloudSubCourseListByLoginIdAndNow(cloudSubCourse);
        cloudSubCourseList.forEach(item->{
            CloudCourse cloudCourse = item.getCloudCourse();
            cloudCourse.setListenTeachers(null);
            cloudCourse.setOtherSchoolAccounts(null);
        });
        return cloudSubCourseList;
    }

    public CloudSubCourse endCourse(CloudSubCourse cloudSubCourse) {
        return cloudSubCourseFeign.endCourse(cloudSubCourse);
    }

    public CloudSubCourse startCourse(CloudSubCourse cloudSubCourse) {
        return cloudSubCourseFeign.startCourse(cloudSubCourse);
    }

    public CloudSubCourse getCurCourse(String groupName) {
        CloudSubCourse queryCloudSubCourse = new CloudSubCourse();
        queryCloudSubCourse.setBroadcastCode(groupName);
        CloudSubCourse curCourse = this.findOneCloudSubCourseByCondition(queryCloudSubCourse);
        return curCourse;
    }
}
