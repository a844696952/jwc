package com.yice.edu.cn.tap.service.classSchedule;

import com.yice.edu.cn.common.pojo.jw.classSchedule.ClassSchedule;
import com.yice.edu.cn.tap.feignClient.classSchedule.ClassScheduleFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;


@Service
public class ClassScheduleService {
    @Autowired
    private ClassScheduleFeign classScheduleFeign;


    public List<List<ClassSchedule>> findList(@RequestBody ClassSchedule classSchedule) {
        return classScheduleFeign.findList(classSchedule);
    }

    public  List<ClassSchedule> todayClassScheduleList(@RequestBody ClassSchedule classSchedule){
        return classScheduleFeign.todayClassScheduleList(classSchedule);
    }
}
