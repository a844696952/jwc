package com.yice.edu.cn.api.service.school;

import com.yice.edu.cn.api.feign.school.SchoolFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SchoolService {
    @Autowired
    private SchoolFeign schoolFeign;

    public boolean validateSchoolAccount(String schoolId) {
        return schoolFeign.validateSchoolAccount(schoolId);
    }

}
