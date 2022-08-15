package com.yice.edu.cn.ecc.service.classSchedule;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.classSchedule.ClassSchedule;
import com.yice.edu.cn.ecc.feignClient.classSchedule.ClassScheduleFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class ClassScheduleService {
    @Autowired
    private ClassScheduleFeign classScheduleFeign;

    /**
     * 获取班级课程列表
     * @param classSchedule
     * @return
     */
    public List<List<ClassSchedule>> findList(ClassSchedule classSchedule) {
        return classScheduleFeign.findList(classSchedule);
    }

    /**
     * 获取今明两天的课表
     * @param classSchedule
     * @return
     */
    public ResponseJson todayAndTomorrowClassSchede(ClassSchedule classSchedule){
        return classScheduleFeign.todayAndTomorrowClassSchede(classSchedule);
    }


    public  List<ClassSchedule> todayClassScheduleList(@RequestBody ClassSchedule classSchedule){
        return classScheduleFeign.todayClassScheduleList(classSchedule);
    }

    public List<ClassSchedule> findTodayClassScheduleListByUserName(String userName) {
        return classScheduleFeign.findTodayClassScheduleListByUserName(userName);
    }
}
