package com.yice.edu.cn.ws.service.cc;

import com.yice.edu.cn.common.pojo.cc.recording.UserRecodingVo;
import com.yice.edu.cn.ws.feignClient.cc.CloudCourseRecordFeign;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class CloudCourseRecordService {
    private Logger log = LoggerFactory.getLogger(CloudCourseRecordService.class);
    @Autowired
    private CloudCourseRecordFeign cloudCourseRecordFeign;

    public void beginRecord(String id){
        try {
            cloudCourseRecordFeign.beginRecord(id);
        }catch (Exception e){
            log.error("录制服务找不到,异常:"+e.getMessage());
        }
    }

    public void endRecord(String id){
        try {
            cloudCourseRecordFeign.endRecord(id);
        }catch (Exception e){
            log.error("录制服务找不到,异常:"+e.getMessage());
        }
    }

    public void addUserVideo(UserRecodingVo vo){
        cloudCourseRecordFeign.addUserVideo(vo);
    }

    public void removeUserVideo(UserRecodingVo vo){
        cloudCourseRecordFeign.removeUserVideo(vo);
    }
}
