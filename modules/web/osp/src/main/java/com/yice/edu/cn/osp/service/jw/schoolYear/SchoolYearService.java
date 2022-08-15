package com.yice.edu.cn.osp.service.jw.schoolYear;

import com.yice.edu.cn.common.pojo.jw.schoolYear.CurSchoolYear;
import com.yice.edu.cn.common.pojo.jw.schoolYear.SchoolYear;
import com.yice.edu.cn.osp.feignClient.jw.schoolYear.SchoolYearFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SchoolYearService {
    @Autowired
    private SchoolYearFeign schoolYearFeign;

    public List<SchoolYear> findSchoolYears(String schoolId) {
        return schoolYearFeign.findSchoolYears(schoolId);
    }

    public CurSchoolYear findCurSchoolYear(String schoolId) {
        return schoolYearFeign.findCurSchoolYear(schoolId);
    }

    public SchoolYear findSchoolYearById(String id){
        return schoolYearFeign.findSchoolYearById(id);
    }

    public String[] findCurrentSchoolRange(String schoolId){
        return schoolYearFeign.findCurrentSchoolRange(schoolId);
    }
}
