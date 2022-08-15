package com.yice.edu.cn.bmp.service.appIndex;

import com.yice.edu.cn.bmp.feignClient.appIndex.AppIndexFeign;
import com.yice.edu.cn.bmp.feignClient.school.SchoolFeign;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.appIndex.AppIndex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppIndexService {
    @Autowired
    private AppIndexFeign appIndeXFeign;

    @Autowired
    private SchoolFeign schoolFeign;

    public List<AppIndex> getAppIndexesForParents(String schoolId, String id) {
        return appIndeXFeign.getAppIndexesForParents(schoolId,id);
    }

    public ResponseJson findSchoolExpireOrSchoolYear(String schoolId){
        return schoolFeign.findSchoolExpireOrSchoolYear(schoolId);
    }
}