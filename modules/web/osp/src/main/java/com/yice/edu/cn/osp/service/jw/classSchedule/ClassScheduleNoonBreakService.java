package com.yice.edu.cn.osp.service.jw.classSchedule;

import com.yice.edu.cn.common.pojo.jw.classSchedule.ClassScheduleNoonBreak;
import com.yice.edu.cn.osp.feignClient.jw.classSchedule.ClassScheduleNoonBreakFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassScheduleNoonBreakService {
    @Autowired
    private ClassScheduleNoonBreakFeign classScheduleNoonBreakFeign;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    public ClassScheduleNoonBreak findClassScheduleNoonBreakById(String id) {
        return classScheduleNoonBreakFeign.findClassScheduleNoonBreakById(id);
    }

    public ClassScheduleNoonBreak saveClassScheduleNoonBreak(ClassScheduleNoonBreak classScheduleNoonBreak) {
        return classScheduleNoonBreakFeign.saveClassScheduleNoonBreak(classScheduleNoonBreak);
    }

    public List<ClassScheduleNoonBreak> findClassScheduleNoonBreakListByCondition(ClassScheduleNoonBreak classScheduleNoonBreak) {
        return classScheduleNoonBreakFeign.findClassScheduleNoonBreakListByCondition(classScheduleNoonBreak);
    }

    public ClassScheduleNoonBreak findOneClassScheduleNoonBreakByCondition(ClassScheduleNoonBreak classScheduleNoonBreak) {
        return classScheduleNoonBreakFeign.findOneClassScheduleNoonBreakByCondition(classScheduleNoonBreak);
    }

    public long findClassScheduleNoonBreakCountByCondition(ClassScheduleNoonBreak classScheduleNoonBreak) {
        return classScheduleNoonBreakFeign.findClassScheduleNoonBreakCountByCondition(classScheduleNoonBreak);
    }

    public void updateClassScheduleNoonBreak(ClassScheduleNoonBreak classScheduleNoonBreak) {
        classScheduleNoonBreakFeign.updateClassScheduleNoonBreak(classScheduleNoonBreak);
    }

    public void updateClassScheduleNoonBreakForAll(ClassScheduleNoonBreak classScheduleNoonBreak) {
        classScheduleNoonBreakFeign.updateClassScheduleNoonBreakForAll(classScheduleNoonBreak);
    }

    public void deleteClassScheduleNoonBreak(String id) {
        classScheduleNoonBreakFeign.deleteClassScheduleNoonBreak(id);
    }

    public void deleteClassScheduleNoonBreakByCondition(ClassScheduleNoonBreak classScheduleNoonBreak) {
        classScheduleNoonBreakFeign.deleteClassScheduleNoonBreakByCondition(classScheduleNoonBreak);
    }
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
    public void updateClassScheduleNoobBreakNumber(ClassScheduleNoonBreak classScheduleNoonBreak){
        classScheduleNoonBreakFeign.updateClassScheduleNoobBreakNumber(classScheduleNoonBreak);
    }
}
