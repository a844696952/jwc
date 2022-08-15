package com.yice.edu.cn.bmp.service.school;

import com.yice.edu.cn.bmp.feignClient.school.SchoolYearFeign;
import com.yice.edu.cn.common.pojo.jw.schoolYear.CurSchoolYear;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SchoolYearService {

    @Autowired
    private SchoolYearFeign schoolYearFeign;

    @Transactional(readOnly = true)
    public CurSchoolYear findCurSchoolYear( String schoolId){
        return  schoolYearFeign.findCurSchoolYear(schoolId);
    }

    @Transactional(readOnly = true)
    public String[] findMaxSchoolYear(String schoolId){
        return schoolYearFeign.findMaxSchoolYear(schoolId);
    }


}
