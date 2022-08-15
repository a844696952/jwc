package com.yice.edu.cn.osp.service.jw.schoolNotify;

import com.yice.edu.cn.common.pojo.jw.schoolNotify.SchoolNotify;
import com.yice.edu.cn.osp.feignClient.jw.schoolNotify.SchoolNotifyFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SchoolNotifyService {
    @Autowired
    private SchoolNotifyFeign schoolNotifyFeign;

    public SchoolNotify findSchoolNotifyById(String id) {
        return schoolNotifyFeign.findSchoolNotifyById(id);
    }

    public SchoolNotify saveSchoolNotify(SchoolNotify schoolNotify) {
        return schoolNotifyFeign.saveSchoolNotify(schoolNotify);
    }

    public List<SchoolNotify> findSchoolNotifyListByCondition(SchoolNotify schoolNotify) {
        return schoolNotifyFeign.findSchoolNotifyListByCondition(schoolNotify);
    }

    public SchoolNotify findOneSchoolNotifyByCondition(SchoolNotify schoolNotify) {
        return schoolNotifyFeign.findOneSchoolNotifyByCondition(schoolNotify);
    }

    public long findSchoolNotifyCountByCondition(SchoolNotify schoolNotify) {
        return schoolNotifyFeign.findSchoolNotifyCountByCondition(schoolNotify);
    }

    public void updateSchoolNotify(SchoolNotify schoolNotify) {
        schoolNotifyFeign.updateSchoolNotify(schoolNotify);
    }

    public void deleteSchoolNotify(String id) {
        schoolNotifyFeign.deleteSchoolNotify(id);
    }

    public void deleteSchoolNotifyByCondition(SchoolNotify schoolNotify) {
        schoolNotifyFeign.deleteSchoolNotifyByCondition(schoolNotify);
    }
}
