package com.yice.edu.cn.tap.service.schoolYear;

import com.yice.edu.cn.common.pojo.jw.schoolYear.CurSchoolYear;
import com.yice.edu.cn.tap.feignClient.schoolYear.SchoolYearFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class SchoolYearService {

    @Autowired
    private SchoolYearFeign schoolYearFeign;

    public CurSchoolYear findCurSchoolYear(String schoolId) {
        return schoolYearFeign.findCurSchoolYear(schoolId);
    }

    @Transactional(readOnly = true)
    public String[] findMaxSchoolYear(String schoolId){
        return schoolYearFeign.findMaxSchoolYear(schoolId);
    }
}
