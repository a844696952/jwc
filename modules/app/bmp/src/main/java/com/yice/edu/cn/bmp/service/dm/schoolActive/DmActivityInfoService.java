package com.yice.edu.cn.bmp.service.dm.schoolActive;

import com.yice.edu.cn.bmp.feignClient.dm.schoolActive.DmActivityInfoFeign;
import com.yice.edu.cn.common.pojo.dm.schoolActive.DmActivityInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DmActivityInfoService {

    @Autowired
    private DmActivityInfoFeign dmActivityInfoFeign;
    
    public DmActivityInfo findDmActivityInfoByActivityId(String activityId){
        return dmActivityInfoFeign.findDmActivityInfoByActivityId(activityId);
    }

}
