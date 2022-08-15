package com.yice.edu.cn.yed.service.jw.schoolYear;

import com.yice.edu.cn.common.pojo.jw.schoolYear.SchoolYear;
import com.yice.edu.cn.yed.feignClient.jw.schoolYear.SchoolYearFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SchoolYearService {
    @Autowired
    private SchoolYearFeign schoolYearFeign;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    public SchoolYear findSchoolYearById(String id) {
        return schoolYearFeign.findSchoolYearById(id);
    }

    public SchoolYear saveSchoolYear(SchoolYear schoolYear) {
        return schoolYearFeign.saveSchoolYear(schoolYear);
    }

    public void batchSaveSchoolYear(List<SchoolYear> schoolYears){
        schoolYearFeign.batchSaveSchoolYear(schoolYears);
    }

    public List<SchoolYear> findSchoolYearListByCondition(SchoolYear schoolYear) {
        return schoolYearFeign.findSchoolYearListByCondition(schoolYear);
    }

    public SchoolYear findOneSchoolYearByCondition(SchoolYear schoolYear) {
        return schoolYearFeign.findOneSchoolYearByCondition(schoolYear);
    }

    public long findSchoolYearCountByCondition(SchoolYear schoolYear) {
        return schoolYearFeign.findSchoolYearCountByCondition(schoolYear);
    }

    public void updateSchoolYear(SchoolYear schoolYear) {
        schoolYearFeign.updateSchoolYear(schoolYear);
    }

    public void updateSchoolYearForAll(SchoolYear schoolYear) {
        schoolYearFeign.updateSchoolYearForAll(schoolYear);
    }

    public void deleteSchoolYear(String id) {
        schoolYearFeign.deleteSchoolYear(id);
    }

    public void deleteSchoolYearByCondition(SchoolYear schoolYear) {
        schoolYearFeign.deleteSchoolYearByCondition(schoolYear);
    }
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
