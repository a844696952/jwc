package com.yice.edu.cn.ecc.service.schoolPush;

import com.yice.edu.cn.common.pojo.jw.schoolPush.SchoolPush;
import com.yice.edu.cn.ecc.feignClient.schoolPush.SchoolPushFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SchoolPushService {
    @Autowired
    private SchoolPushFeign schoolPushFeign;
    public  List<SchoolPush> findPageSchoolPushesByAppIdAndSchoolId(SchoolPush schoolPush){
        return  schoolPushFeign.findPageSchoolPushesByAppIdAndSchoolId(schoolPush);
    }
}
