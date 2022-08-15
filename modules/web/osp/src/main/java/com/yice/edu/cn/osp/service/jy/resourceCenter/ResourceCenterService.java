package com.yice.edu.cn.osp.service.jy.resourceCenter;

import com.yice.edu.cn.common.pojo.jy.resourceCenter.ResourceCenter;
import com.yice.edu.cn.common.pojo.jy.resourceCenter.TeacherCourse;
import com.yice.edu.cn.osp.feignClient.jy.resourceCenter.ResourceCenterFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@Service
public class ResourceCenterService {
    @Autowired
    private ResourceCenterFeign resourceCenterFeign;

    public ResourceCenter findResourceCenterById(String id) {
        return resourceCenterFeign.findResourceCenterById(id);
    }

    public ResourceCenter saveResourceCenter(ResourceCenter resourceCenter) {
        resourceCenter.setSchoolId(mySchoolId());
        return resourceCenterFeign.saveResourceCenter(resourceCenter);
    }

    public List<ResourceCenter> findResourceCenterListByCondition(ResourceCenter resourceCenter) {
        return resourceCenterFeign.findResourceCenterListByCondition(resourceCenter);
    }

    public ResourceCenter findOneResourceCenterByCondition(ResourceCenter resourceCenter) {
        return resourceCenterFeign.findOneResourceCenterByCondition(resourceCenter);
    }

    public long findResourceCenterCountByCondition(ResourceCenter resourceCenter) {
        return resourceCenterFeign.findResourceCenterCountByCondition(resourceCenter);
    }

    public void updateResourceCenter(ResourceCenter resourceCenter) {
        resourceCenterFeign.updateResourceCenter(resourceCenter);
    }

    public void deleteResourceCenter(String id) {
        resourceCenterFeign.deleteResourceCenter(id);
    }

    public void deleteResourceCenterByCondition(ResourceCenter resourceCenter) {
        resourceCenterFeign.deleteResourceCenterByCondition(resourceCenter);
    }

    public List<TeacherCourse> findTeacherCourseListBySchoolId(TeacherCourse teacherCourse) {
        return  resourceCenterFeign.findTeacherCourseListBySchoolId(teacherCourse);
    }

    public long findTeacherCourseCountBySchoolId(TeacherCourse teacherCourse) {
        return  resourceCenterFeign.findTeacherCourseCountBySchoolId(teacherCourse);
    }

    public List<ResourceCenter> findResourceCentersForH5ByCondition(ResourceCenter resourceCenter) {
        return  resourceCenterFeign.findResourceCentersForH5ByCondition(resourceCenter);
    }

    public long findResourceCenterCountForH5ByCondition(ResourceCenter resourceCenter) {
        return  resourceCenterFeign.findResourceCenterCountForH5ByCondition(resourceCenter);
    }
}
