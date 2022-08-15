package com.yice.edu.cn.yed.service.baseData.educationBureau;

import com.yice.edu.cn.common.pojo.jw.educationBureau.EducationBureau;
import com.yice.edu.cn.common.pojo.jw.school.School;
import com.yice.edu.cn.yed.feignClient.baseData.educationBureau.EducationBureauFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EducationBureauService {
    @Autowired
    private EducationBureauFeign educationBureauFeign;

    public EducationBureau findEducationBureauById(String id) {
        return educationBureauFeign.findEducationBureauById(id);
    }

    public EducationBureau saveEducationBureau(EducationBureau educationBureau) {
        return educationBureauFeign.saveEducationBureau(educationBureau);
    }

    public List<EducationBureau> findEducationBureauListByCondition(EducationBureau educationBureau) {
        return educationBureauFeign.findEducationBureauListByCondition(educationBureau);
    }

    public EducationBureau findOneEducationBureauByCondition(EducationBureau educationBureau) {
        return educationBureauFeign.findOneEducationBureauByCondition(educationBureau);
    }

    public long findEducationBureauCountByCondition(EducationBureau educationBureau) {
        return educationBureauFeign.findEducationBureauCountByCondition(educationBureau);
    }

    public void updateEducationBureau(EducationBureau educationBureau) {
        educationBureauFeign.updateEducationBureau(educationBureau);
    }

    public void deleteEducationBureau(String id) {
        educationBureauFeign.deleteEducationBureau(id);
    }

    public void deleteEducationBureauByCondition(EducationBureau educationBureau) {
        educationBureauFeign.deleteEducationBureauByCondition(educationBureau);
    }

    public List<School> findUnSelectedSchoolsById(String educationBureauId) {
        return educationBureauFeign.findUnSelectedSchoolsById(educationBureauId);
    }

    public List<String> findSelectedSchoolsById(String educationBureauId) {
        return educationBureauFeign.findSelectedSchoolsById(educationBureauId);
    }

}
