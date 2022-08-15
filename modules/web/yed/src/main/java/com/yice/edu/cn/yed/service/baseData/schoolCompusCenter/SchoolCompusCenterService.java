package com.yice.edu.cn.yed.service.baseData.schoolCompusCenter;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.schoolCompusCenter.SchoolCompusCenter;
import com.yice.edu.cn.yed.feignClient.baseData.schoolCompusCenter.SchoolCompusCenterFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SchoolCompusCenterService {
    @Autowired
    private SchoolCompusCenterFeign schoolCompusCenterFeign;

    public SchoolCompusCenter findSchoolCompusCenterById(String id) {
        return schoolCompusCenterFeign.findSchoolCompusCenterById(id);
    }

    public SchoolCompusCenter saveSchoolCompusCenter(SchoolCompusCenter schoolCompusCenter) {
        return schoolCompusCenterFeign.saveSchoolCompusCenter(schoolCompusCenter);
    }

    public List<SchoolCompusCenter> findSchoolCompusCenterListByCondition(SchoolCompusCenter schoolCompusCenter) {
        return schoolCompusCenterFeign.findSchoolCompusCenterListByCondition(schoolCompusCenter);
    }

    public SchoolCompusCenter findOneSchoolCompusCenterByCondition(SchoolCompusCenter schoolCompusCenter) {
        return schoolCompusCenterFeign.findOneSchoolCompusCenterByCondition(schoolCompusCenter);
    }

    public long findSchoolCompusCenterCountByCondition(SchoolCompusCenter schoolCompusCenter) {
        return schoolCompusCenterFeign.findSchoolCompusCenterCountByCondition(schoolCompusCenter);
    }

    public void updateSchoolCompusCenter(SchoolCompusCenter schoolCompusCenter) {
        schoolCompusCenterFeign.updateSchoolCompusCenter(schoolCompusCenter);
    }

    public void deleteSchoolCompusCenter(String id) {
        schoolCompusCenterFeign.deleteSchoolCompusCenter(id);
    }

    public void deleteSchoolCompusCenterByCondition(SchoolCompusCenter schoolCompusCenter) {
        schoolCompusCenterFeign.deleteSchoolCompusCenterByCondition(schoolCompusCenter);
    }

    public ResponseJson findSchoolCompusCenter(String schoolId){
        return schoolCompusCenterFeign.findSchoolCompusCenter(schoolId);
    }

    public void saveSchoolCompusCenterData(SchoolCompusCenter schoolCompusCenter){
        schoolCompusCenterFeign.saveSchoolCompusCenterData(schoolCompusCenter);
    }
}
