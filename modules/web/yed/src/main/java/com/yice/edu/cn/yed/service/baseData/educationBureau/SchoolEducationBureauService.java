package com.yice.edu.cn.yed.service.baseData.educationBureau;

import com.yice.edu.cn.common.pojo.jw.educationBureau.SchoolEducationBureau;
import com.yice.edu.cn.yed.feignClient.baseData.educationBureau.SchoolEducationBureauFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SchoolEducationBureauService {
    @Autowired
    private SchoolEducationBureauFeign schoolEducationBureauFeign;

    public SchoolEducationBureau findSchoolEducationBureauById(String id) {
        return schoolEducationBureauFeign.findSchoolEducationBureauById(id);
    }

    public SchoolEducationBureau saveSchoolEducationBureau(SchoolEducationBureau schoolEducationBureau) {
        return schoolEducationBureauFeign.saveSchoolEducationBureau(schoolEducationBureau);
    }

    public List<SchoolEducationBureau> findSchoolEducationBureauListByCondition(SchoolEducationBureau schoolEducationBureau) {
        return schoolEducationBureauFeign.findSchoolEducationBureauListByCondition(schoolEducationBureau);
    }

    public SchoolEducationBureau findOneSchoolEducationBureauByCondition(SchoolEducationBureau schoolEducationBureau) {
        return schoolEducationBureauFeign.findOneSchoolEducationBureauByCondition(schoolEducationBureau);
    }

    public long findSchoolEducationBureauCountByCondition(SchoolEducationBureau schoolEducationBureau) {
        return schoolEducationBureauFeign.findSchoolEducationBureauCountByCondition(schoolEducationBureau);
    }

    public void updateSchoolEducationBureau(SchoolEducationBureau schoolEducationBureau) {
        schoolEducationBureauFeign.updateSchoolEducationBureau(schoolEducationBureau);
    }

    public void deleteSchoolEducationBureau(String id) {
        schoolEducationBureauFeign.deleteSchoolEducationBureau(id);
    }

    public void deleteSchoolEducationBureauByCondition(SchoolEducationBureau schoolEducationBureau) {
        schoolEducationBureauFeign.deleteSchoolEducationBureauByCondition(schoolEducationBureau);
    }

    public void resetSchoolEducationBureaus(SchoolEducationBureau schoolEducationBureau) {
        schoolEducationBureauFeign.resetSchoolEducationBureaus(schoolEducationBureau);
    }
}
