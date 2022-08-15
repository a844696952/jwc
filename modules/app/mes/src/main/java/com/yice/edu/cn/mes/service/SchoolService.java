package com.yice.edu.cn.mes.service;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.school.School;
import com.yice.edu.cn.mes.feign.SchoolFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SchoolService {
    @Autowired
    private SchoolFeign schoolFeign;

    public List<School> findSchoolListByCondition(School school) {
        return schoolFeign.findSchoolListByCondition(school);
    }

    public ResponseJson findSchoolExpireOrSchoolYear(String schoolId) {
        return schoolFeign.findSchoolExpireOrSchoolYear(schoolId);
    }

}
