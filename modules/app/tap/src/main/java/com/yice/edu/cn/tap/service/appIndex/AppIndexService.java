package com.yice.edu.cn.tap.service.appIndex;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.appIndex.AppIndex;
import com.yice.edu.cn.tap.feignClient.school.SchoolFeign;
import com.yice.edu.cn.tap.feignClient.appIndex.AppIndexFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppIndexService {
    @Autowired
    private AppIndexFeign appIndeXFeign;

    @Autowired
    private SchoolFeign schoolFeign;

    public List<AppIndex> getAppIndexesForTeacher(String schoolId, String id) {
        return appIndeXFeign.getAppIndexesForTeacher(schoolId,id);
    }

    public Long getCountLeaveSchool(String schoolId, String id) {
        return appIndeXFeign.getCountLeaveSchool(schoolId,id);
    }

    public ResponseJson findSchoolExpireOrSchoolYear(String schoolId){
        return schoolFeign.findSchoolExpireOrSchoolYear(schoolId);
    }

}
