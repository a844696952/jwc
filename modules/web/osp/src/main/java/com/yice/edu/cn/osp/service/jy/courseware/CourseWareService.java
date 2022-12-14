package com.yice.edu.cn.osp.service.jy.courseware;

import com.yice.edu.cn.common.pojo.jy.courseware.CourseRes;
import com.yice.edu.cn.common.pojo.jy.courseware.CourseWare;
import com.yice.edu.cn.common.pojo.jy.courseware.ResType;
import com.yice.edu.cn.osp.feignClient.jy.courseware.CourseResFeign;
import com.yice.edu.cn.osp.feignClient.jy.courseware.CourseWareFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.myId;
import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@Service
public class CourseWareService {
    @Autowired
    private CourseWareFeign courseWareFeign;
    @Autowired
    private CourseResFeign courseResFeign;

    public CourseWare findCourseWareById(String id) {
        return courseWareFeign.findCourseWareById(id);
    }

    public CourseWare saveCourseWare(CourseWare courseWare) {
        return courseWareFeign.saveCourseWare(courseWare);
    }

    public List<CourseWare> findCourseWareListByCondition(CourseWare courseWare) {
        return courseWareFeign.findCourseWareListByCondition(courseWare);
    }

    public CourseWare findOneCourseWareByCondition(CourseWare courseWare) {
        return courseWareFeign.findOneCourseWareByCondition(courseWare);
    }

    public long findCourseWareCountByCondition(CourseWare courseWare) {
        return courseWareFeign.findCourseWareCountByCondition(courseWare);
    }

    public void updateCourseWare(CourseWare courseWare) {
        courseWareFeign.updateCourseWare(courseWare);
    }

    public void deleteCourseWare(String id) {
        CourseWare ware =courseWareFeign.findCourseWareById(id);
        courseResFeign.deleteCourseRes(ware.getResoucesId());
        courseWareFeign.deleteCourseWare(id);
    }

    public void deleteCourseWareByCondition(CourseWare courseWare) {
        courseWareFeign.deleteCourseWareByCondition(courseWare);
    }
    public void batchUpdateCourseWare(CourseWare courseWare) {
        courseWareFeign.batchUpdateCourseWare(courseWare);
    }

    public void deleteCourseWareByIds(List<String> ids) {
        courseWareFeign.deleteCourseWareByIds(ids);
    }


    public CourseRes saveCourseWareAndRes(CourseWare ware) {
        CourseRes courseRes = new CourseRes();
        courseRes.setName(ware.getCoursewareName()+"??????");
        courseRes.setLv1(ware.getLv1());
        courseRes.setLv2(ware.getLv2());
        courseRes.setLv3(ware.getLv4());
        courseRes.setLv4(ware.getLv4());
        courseRes.setSchoolId(mySchoolId());
        courseRes.setSubjectMateriaId(ware.getSubjectMateriaId());
        courseRes.setResSize(Integer.valueOf(ware.getCoursewareSize()));
        courseRes.setResType(ResType.COURSEWARE);
        courseRes.setResUrl(ware.getCoursewareUrl());
        courseRes.setTeacherId(myId());
        courseRes.setTextbook(ware.getTextbook());
        courseRes.setTitle(ware.getTitle());
        return courseResFeign.saveCourseRes(courseRes);
    }

}
