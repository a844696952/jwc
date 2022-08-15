package com.yice.edu.cn.bmp.service.classSchedule;

import com.yice.edu.cn.bmp.feignClient.classSchedule.ClassScheduleFeign;
import com.yice.edu.cn.common.pojo.jw.classSchedule.ClassSchedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;


@Service
public class ClassScheduleService {
    @Autowired
    private ClassScheduleFeign classScheduleFeign;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public List<List<ClassSchedule>> findList(@RequestBody ClassSchedule classSchedule) {
        List<List<ClassSchedule>> c =  classScheduleFeign.findList(classSchedule);
        removeNullTeacherOrNullCourseName(c);
        return c;
    }


    public  List<ClassSchedule> todayClassScheduleList(@RequestBody ClassSchedule classSchedule){
        return classScheduleFeign.todayClassScheduleList(classSchedule);
    }

    //课程名称为空或者教师名称为空的赋值为Null
    public void removeNullTeacherOrNullCourseName(List<List<ClassSchedule>> c){
        for(int i = c.size()-1;i>=0;i--){
            for(int j = c.get(i).size()-1;j>=0;j--){
                if(c.get(i).get(j)!=null&&(c.get(i).get(j).getTeacherName()==null||c.get(i).get(j).getCourseName()==null)){
                   c.get(i).set(j,null);
                }
            }
        }
    }
}
