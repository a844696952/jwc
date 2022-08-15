package com.yice.edu.cn.ewb.service.school;

import com.alicp.jetcache.anno.Cached;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.school.School;
import com.yice.edu.cn.ewb.feignClient.school.SchoolFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SchoolService {

    @Autowired
    private SchoolFeign schoolFeign;


    public ResponseJson findSchoolExpireOrSchoolYear(String schoolId){
       return schoolFeign.findSchoolExpireOrSchoolYear(schoolId);
    }

    @Cached(name = "osp_find_school_",expire = 1000)
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

    public boolean validateSchoolAccount(String schoolId) {
        return schoolFeign.validateSchoolAccount(schoolId);
    }



}
