package com.yice.edu.cn.dm.service.classSchedule;

import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.jw.classSchedule.ClassScheduleInit;
import com.yice.edu.cn.dm.feignClient.jw.classSchedule.ClassScheduleInitFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassScheduleInitService {
    @Autowired
    private ClassScheduleInitFeign classScheduleInitFeign;

    public List<ClassScheduleInit> findListClassScheduleInitBySchool(String schoolId){
        ClassScheduleInit classScheduleInit = new ClassScheduleInit();
        classScheduleInit.setSchoolId(schoolId);
        Pager pager = new Pager();
        pager.setSortOrder("asc");
        pager.setSortField("number");
        pager.setPaging(false);
        classScheduleInit.setPager(pager);
        return classScheduleInitFeign.findListClassScheduleInitBySchool(classScheduleInit);
    }


}
