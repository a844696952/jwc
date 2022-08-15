package com.yice.edu.cn.osp.service.jw.classSchedule;

import com.yice.edu.cn.common.pojo.jw.classSchedule.ClassScheduleInit;
import com.yice.edu.cn.osp.feignClient.jw.classSchedule.ClassScheduleInitFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassScheduleInitService {
    @Autowired
    private ClassScheduleInitFeign classScheduleInitFeign;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    public ClassScheduleInit findClassScheduleInitById(String id) {
        return classScheduleInitFeign.findClassScheduleInitById(id);
    }

    public ClassScheduleInit saveClassScheduleInit(ClassScheduleInit classScheduleInit) {
        return classScheduleInitFeign.saveClassScheduleInit(classScheduleInit);
    }

    public List<ClassScheduleInit> findClassScheduleInitListByCondition(ClassScheduleInit classScheduleInit) {
        return classScheduleInitFeign.findClassScheduleInitListByCondition(classScheduleInit);
    }

    public ClassScheduleInit findOneClassScheduleInitByCondition(ClassScheduleInit classScheduleInit) {
        return classScheduleInitFeign.findOneClassScheduleInitByCondition(classScheduleInit);
    }

    public long findClassScheduleInitCountByCondition(ClassScheduleInit classScheduleInit) {
        return classScheduleInitFeign.findClassScheduleInitCountByCondition(classScheduleInit);
    }

    public void updateClassScheduleInit(ClassScheduleInit classScheduleInit) {
        classScheduleInitFeign.updateClassScheduleInit(classScheduleInit);
    }

    public void updateClassScheduleInitForAll(ClassScheduleInit classScheduleInit) {
        classScheduleInitFeign.updateClassScheduleInitForAll(classScheduleInit);
    }

    public void deleteClassScheduleInit(String id) {
        classScheduleInitFeign.deleteClassScheduleInit(id);
    }

    public void deleteClassScheduleInitByCondition(ClassScheduleInit classScheduleInit) {
        classScheduleInitFeign.deleteClassScheduleInitByCondition(classScheduleInit);
    }
    public void  batchSaveClassScheduleInit(List<ClassScheduleInit> classScheduleInits){
        classScheduleInitFeign.batchSaveClassScheduleInit(classScheduleInits);
    }
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/


    public void batchSaveClassScheduleTime(List<ClassScheduleInit> classScheduleInits,Integer number,String schoolId){
        classScheduleInitFeign.batchSaveClassScheduleTime(classScheduleInits,number,schoolId);
    }

    public List<ClassScheduleInit> findListClassScheduleInitBySchool(ClassScheduleInit classScheduleInit){
        return classScheduleInitFeign.findListClassScheduleInitBySchool(classScheduleInit);
    }
}
