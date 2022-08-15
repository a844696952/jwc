package com.yice.edu.cn.tap.service.school;

import com.yice.edu.cn.common.pojo.jw.school.School;

import com.yice.edu.cn.tap.feignClient.school.SchoolFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SchoolService {
    @Autowired
    private SchoolFeign schoolFeign;

    public School findSchoolById(String id) {
        return schoolFeign.findSchoolById(id);
    }

    public School saveSchool(School school) {
        return schoolFeign.saveSchool(school);
    }

    public List<School> findSchoolListByCondition(School school) {
        return schoolFeign.findSchoolListByCondition(school);
    }
    public long findSchoolCountByCondition(School school) {
        return schoolFeign.findSchoolCountByCondition(school);
    }

    public void updateSchool(School school) {
        schoolFeign.updateSchool(school);
    }

    public void deleteSchool(String id) {
        schoolFeign.deleteSchool(id);
    }

    public void deleteSchoolByCondition(School school) {
        schoolFeign.deleteSchoolByCondition(school);
    }
}
