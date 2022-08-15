package com.yice.edu.cn.osp.service.dm.school;

import java.util.List;

import com.alicp.jetcache.anno.CacheInvalidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import com.alicp.jetcache.anno.Cached;
import com.yice.edu.cn.common.pojo.jw.school.School;
import com.yice.edu.cn.common.pojo.jw.school.SchoolExt;
import com.yice.edu.cn.osp.feignClient.jw.school.SchoolFeign;

@Service
public class SchoolService {
    @Autowired
    private SchoolFeign schoolFeign;
//    @Cached(name = "osp_find_school_",expire = 1000,key = "#id")
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

//    @CacheInvalidate(name = "osp_find_school_",key = "#id")
    public void updateSchool(School school,String id) {
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
    
//    @CacheInvalidate(name = "osp_find_school_",key = "#schoolExt.id")
    public void updateSchoolAndSaveSchoolYear(SchoolExt schoolExt) {
    	 schoolFeign.updateSchoolAndSaveSchoolYear(schoolExt);
    }
    
    public List<Integer> queryClockInRange(){
    	return schoolFeign.queryClockInRange();
    }
}
