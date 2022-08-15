package com.yice.edu.cn.tap.service.courseware;

import com.yice.edu.cn.common.pojo.jy.courseware.CourseRes;
import com.yice.edu.cn.tap.feignClient.courseware.CourseResFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseResService {
    @Autowired
    private CourseResFeign courseResFeign;

    public CourseRes findCourseResById(String id) {
        return courseResFeign.findCourseResById(id);
    }

    public CourseRes saveCourseRes(CourseRes courseRes) {
        return courseResFeign.saveCourseRes(courseRes);
    }

    public List<CourseRes> findCourseResListByCondition(CourseRes courseRes) {
        return courseResFeign.findCourseResListByCondition(courseRes);
    }

    public CourseRes findOneCourseResByCondition(CourseRes courseRes) {
        return courseResFeign.findOneCourseResByCondition(courseRes);
    }

    public long findCourseResCountByCondition(CourseRes courseRes) {
        return courseResFeign.findCourseResCountByCondition(courseRes);
    }

    public void updateCourseRes(CourseRes courseRes) {
        courseResFeign.updateCourseRes(courseRes);
    }

    public void deleteCourseRes(String id) {
        courseResFeign.deleteCourseRes(id);
    }

    public void deleteCourseResByCondition(CourseRes courseRes) {
        courseResFeign.deleteCourseResByCondition(courseRes);
    }

    public CourseRes mv(CourseRes courseRes) {
        return courseResFeign.mv(courseRes);
    }

    public void mvs(CourseRes courseRes) {
        courseResFeign.mvs(courseRes);
    }

    public void deletes(List<String> ids) {
        courseResFeign.deletes(ids);
    }

    public CourseRes rename(CourseRes courseRes) {
        return courseResFeign.rename(courseRes);
    }

    public CourseRes saveTestCourseRes(CourseRes courseRes) {
        return courseResFeign.saveTestCourseRes(courseRes);
    }

    public void remark(CourseRes courseRes) {
        courseResFeign.remark(courseRes);
    }
}
