package com.yice.edu.cn.bmp.service.electiveCourse;

import com.yice.edu.cn.common.pojo.jw.electiveCourse.ElectiveCourse;
import com.yice.edu.cn.common.pojo.jw.electiveCourse.StudentPojo;
import com.yice.edu.cn.bmp.feignClient.electiveCourse.ElectiveCourseFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ElectiveCourseService {
    @Autowired
    private ElectiveCourseFeign electiveCourseFeign;

    public ElectiveCourse findElectiveCourseById(String id) {
        return electiveCourseFeign.findElectiveCourseById(id);
    }

    public ElectiveCourse saveElectiveCourse(ElectiveCourse electiveCourse) {
        return electiveCourseFeign.saveElectiveCourse(electiveCourse);
    }

    public List<ElectiveCourse> findElectiveCourseListByCondition(ElectiveCourse electiveCourse) {
        return electiveCourseFeign.findElectiveCourseListByCondition(electiveCourse);
    }

    public ElectiveCourse findOneElectiveCourseByCondition(ElectiveCourse electiveCourse) {
        return electiveCourseFeign.findOneElectiveCourseByCondition(electiveCourse);
    }

    public long findElectiveCourseCountByCondition(ElectiveCourse electiveCourse) {
        return electiveCourseFeign.findElectiveCourseCountByCondition(electiveCourse);
    }

    public void updateElectiveCourse(ElectiveCourse electiveCourse) {
        electiveCourseFeign.updateElectiveCourse(electiveCourse);
    }

    public void deleteElectiveCourse(String id) {
        electiveCourseFeign.deleteElectiveCourse(id);
    }

    public void deleteElectiveCourseByCondition(ElectiveCourse electiveCourse) {
        electiveCourseFeign.deleteElectiveCourseByCondition(electiveCourse);
    }

    public List<StudentPojo> findStudentListByClassIdList(List<String> classIdList) {
        return  electiveCourseFeign.findStudentListByClassIdList(classIdList);
    }



    public List<ElectiveCourse> findElectiveCoursesByConditionForStu(ElectiveCourse electiveCourse) {
        return  electiveCourseFeign.findElectiveCoursesByConditionForStu(electiveCourse);
    }

    public long findElectiveCourseCountByConditionForStu(ElectiveCourse electiveCourse) {
        return  electiveCourseFeign.findElectiveCourseCountByConditionForStu(electiveCourse);
    }
}
