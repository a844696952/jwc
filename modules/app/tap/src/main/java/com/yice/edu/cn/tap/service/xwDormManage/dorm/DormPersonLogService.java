package com.yice.edu.cn.tap.service.xwDormManage.dorm;

import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import com.yice.edu.cn.common.pojo.jw.teacher.TeacherClasses;
import com.yice.edu.cn.common.pojo.xw.dormManage.dorm.DormPersonLog;
import com.yice.edu.cn.tap.feignClient.teacher.TeacherClassesFeign;
import com.yice.edu.cn.tap.feignClient.xwDormManage.dorm.DormPersonLogFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DormPersonLogService {
    @Autowired
    private DormPersonLogFeign dormPersonLogFeign;
    @Autowired
    private TeacherClassesFeign teacherClassesFeign;


    public List<DormPersonLog> findDormPersonLogListByCondition(DormPersonLog dormPersonLog) {
        return dormPersonLogFeign.findDormPersonLogListByCondition(dormPersonLog);
    }

    public List<DormPersonLog> findDormPersonOutListByConditionWithStudent(DormPersonLog dormPersonLog){
        List<DormPersonLog> list = dormPersonLogFeign.findDormPersonOutListByConditionWithStudent(dormPersonLog);
        list.forEach(s->{
            //当前孩子班主任
            TeacherClasses teacherClasses = new TeacherClasses();
            teacherClasses.setClassesId(s.getClassesId());
            Teacher teacher = teacherClassesFeign.findHeadmasterByClasses(teacherClasses);
            if (teacher != null) {
                s.setTeacherName(teacher.getName());
                s.setTeacherTel(teacher.getTel());
            }
        });
        return list;
    }

    public long findDormPersonOutCountByConditionWithStudent(DormPersonLog dormPersonLog){
        return dormPersonLogFeign.findDormPersonOutCountByConditionWithStudent(dormPersonLog);
    }


    public List<DormPersonLog> findDormPersonOutListByConditionWithTeacher(DormPersonLog dormPersonLog){
        return dormPersonLogFeign.findDormPersonOutListByConditionWithTeacher(dormPersonLog);
    }

    public long findDormPersonOutCountByConditionWithTeacher(DormPersonLog dormPersonLog){
        return dormPersonLogFeign.findDormPersonOutCountByConditionWithTeacher(dormPersonLog);
    }


}
