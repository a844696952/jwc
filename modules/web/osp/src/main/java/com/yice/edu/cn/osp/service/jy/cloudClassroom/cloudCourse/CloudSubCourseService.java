package com.yice.edu.cn.osp.service.jy.cloudClassroom.cloudCourse;

import com.yice.edu.cn.common.pojo.cc.cloudCourse.CloudSubCourse;
import com.yice.edu.cn.common.pojo.cc.cloudCourse.SrsQrCodeVo;
import com.yice.edu.cn.osp.feignClient.jy.cloudClassroom.cloudCourse.CloudSubCourseFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CloudSubCourseService {
    @Autowired
    private CloudSubCourseFeign cloudSubCourseFeign;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    public CloudSubCourse findCloudSubCourseById(String id) {
        return cloudSubCourseFeign.findCloudSubCourseById(id);
    }

    public CloudSubCourse saveCloudSubCourse(CloudSubCourse cloudSubCourse) {
        return cloudSubCourseFeign.saveCloudSubCourse(cloudSubCourse);
    }

    public void batchSaveCloudSubCourse(List<CloudSubCourse> cloudSubCourses){
        cloudSubCourseFeign.batchSaveCloudSubCourse(cloudSubCourses);
    }

    public List<CloudSubCourse> findCloudSubCourseListByCondition(CloudSubCourse cloudSubCourse) {
        return cloudSubCourseFeign.findCloudSubCourseListByCondition(cloudSubCourse);
    }

    public CloudSubCourse findOneCloudSubCourseByCondition(CloudSubCourse cloudSubCourse) {
        return cloudSubCourseFeign.findOneCloudSubCourseByCondition(cloudSubCourse);
    }

    public long findCloudSubCourseCountByCondition(CloudSubCourse cloudSubCourse) {
        return cloudSubCourseFeign.findCloudSubCourseCountByCondition(cloudSubCourse);
    }

    public void updateCloudSubCourse(CloudSubCourse cloudSubCourse) {
        cloudSubCourseFeign.updateCloudSubCourse(cloudSubCourse);
    }

    public void updateCloudSubCourseForAll(CloudSubCourse cloudSubCourse) {
        cloudSubCourseFeign.updateCloudSubCourseForAll(cloudSubCourse);
    }

    public void deleteCloudSubCourse(String id) {
        cloudSubCourseFeign.deleteCloudSubCourse(id);
    }

    public void deleteCloudSubCourseByCondition(CloudSubCourse cloudSubCourse) {
        cloudSubCourseFeign.deleteCloudSubCourseByCondition(cloudSubCourse);
    }

    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/

    /**
     * ???????????????id?????????????????????id??????
     * @param id
     * @return
     */
    public List<String> findCloudSubCourseIdListByCloudCourseId(String id) {
        return cloudSubCourseFeign.findCloudSubCourseIdListByCloudCourseId(id);
    }

    /**
     * ???????????????????????????????????????id??????
     * @param cloudSubCourse
     * @return
     */
    public List<String> findCloudCourseIdListByTeacher(CloudSubCourse cloudSubCourse) {
        return cloudSubCourseFeign.findCloudCourseIdListByTeacher(cloudSubCourse);
    }

    /**
     * ???????????????id????????????
     * @param cloudSubCourseIdList
     */
    public void deleteCloudSubCourseByIds(List<String> cloudSubCourseIdList) {
        cloudSubCourseFeign.deleteCloudSubCourseByIds(cloudSubCourseIdList);
    }

    /**
     * ?????????????????????????????????????????????????????????
     * @param cloudSubCourse
     * @return
     */
    public long findOnGoingOrFinishCloudSubCourseCountByCondition(CloudSubCourse cloudSubCourse) {
        return  cloudSubCourseFeign.findOnGoingOrFinishCloudSubCourseCountByCondition(cloudSubCourse);
    }

    public SrsQrCodeVo genQrCode(CloudSubCourse cloudSubCourse) {
        return cloudSubCourseFeign.genQrCode(cloudSubCourse);
    }
}
