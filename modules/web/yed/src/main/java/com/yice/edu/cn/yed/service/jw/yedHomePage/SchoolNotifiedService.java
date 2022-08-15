package com.yice.edu.cn.yed.service.jw.yedHomePage;

import com.yice.edu.cn.common.pojo.jw.yed.SchoolNotified;
import com.yice.edu.cn.yed.feignClient.jw.yedHomePage.SchoolNotifiedFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SchoolNotifiedService {
    @Autowired
    private SchoolNotifiedFeign schoolNotifiedFeign;

    public SchoolNotified findSchoolNotifiedById(String id) {
        return schoolNotifiedFeign.findSchoolNotifiedById(id);
    }

    public SchoolNotified saveSchoolNotified(SchoolNotified schoolNotified) {
        return schoolNotifiedFeign.saveSchoolNotified(schoolNotified);
    }

    public List<SchoolNotified> findSchoolNotifiedListByCondition(SchoolNotified schoolNotified) {
        return schoolNotifiedFeign.findSchoolNotifiedListByCondition(schoolNotified);
    }

    public SchoolNotified findOneSchoolNotifiedByCondition(SchoolNotified schoolNotified) {
        return schoolNotifiedFeign.findOneSchoolNotifiedByCondition(schoolNotified);
    }

    public long findSchoolNotifiedCountByCondition(SchoolNotified schoolNotified) {
        return schoolNotifiedFeign.findSchoolNotifiedCountByCondition(schoolNotified);
    }

    public void updateSchoolNotified(SchoolNotified schoolNotified) {
        schoolNotifiedFeign.updateSchoolNotified(schoolNotified);
    }

    public void deleteSchoolNotified(String id) {
        schoolNotifiedFeign.deleteSchoolNotified(id);
    }

    public void deleteSchoolNotifiedByCondition(SchoolNotified schoolNotified) {
        schoolNotifiedFeign.deleteSchoolNotifiedByCondition(schoolNotified);
    }
}
