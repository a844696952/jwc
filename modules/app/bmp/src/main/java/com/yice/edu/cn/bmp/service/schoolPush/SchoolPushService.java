package com.yice.edu.cn.bmp.service.schoolPush;


import com.yice.edu.cn.bmp.feignClient.schoolPush.SchoolPushFeign;
import com.yice.edu.cn.common.pojo.jw.schoolPush.SchoolPush;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class SchoolPushService {
    @Autowired
    private SchoolPushFeign schoolPushFeign;

    public SchoolPush findSchoolPushById(String id) {
        return schoolPushFeign.findSchoolPushById(id);
    }

    public List<SchoolPush> findSchoolPushsByCondition(SchoolPush schoolPush){
        return  schoolPushFeign.findSchoolPushsByCondition(schoolPush);
    }
}
