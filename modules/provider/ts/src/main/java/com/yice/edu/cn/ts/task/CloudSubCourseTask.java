package com.yice.edu.cn.ts.task;

import com.yice.edu.cn.common.pojo.cc.cloudCourse.CloudSubCourse;
import com.yice.edu.cn.ts.feignClient.CloudSubCourseFeign;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class CloudSubCourseTask {
    @Autowired
    private CloudSubCourseFeign cloudSubCourseFeign;
    private final static Logger logger = LoggerFactory.getLogger(CloudSubCourseTask.class);

    @Async
    @Scheduled(cron = "0 0 10 * * ? ")
    public void pushCloudSubCourse10(){
        cloudSubCourseFeign.pushCloudSubCourse10();
    }


    @Async
    @Scheduled(cron = "0 0 21 * * ? ")
    public void pushCloudSubCourse21(){
        cloudSubCourseFeign.pushCloudSubCourse21();
    }
}
