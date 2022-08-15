package com.yice.edu.cn.ts.task;

import com.yice.edu.cn.common.pojo.jw.SchoolDateCenter.SchoolDateCenter;
import com.yice.edu.cn.common.pojo.jw.SchoolDateCenter.schoolDateAssist.StudentThisKq;
import com.yice.edu.cn.ts.feignClient.SchoolDateCenterFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.Schedules;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.util.List;

@Component
@Async
public class SchoolDateCenterTask {
    @Autowired
    private SchoolDateCenterFeign schoolDateCenterFeign;

    @Scheduled(cron = "0 0 0 1/1 * ?")
    @Async
    public void updateSchoolDateCenter(){
        SchoolDateCenter schoolDateCenter = new SchoolDateCenter();
        List<SchoolDateCenter> schoolDateCenterList = schoolDateCenterFeign.findSchoolDateCenterListByCondition(schoolDateCenter);
        for (SchoolDateCenter s: schoolDateCenterList){
            schoolDateCenterFeign.updateSchoolDateCenterByType(s,9);
        }

    }
}
