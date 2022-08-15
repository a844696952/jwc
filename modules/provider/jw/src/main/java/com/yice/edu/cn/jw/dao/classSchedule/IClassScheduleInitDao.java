package com.yice.edu.cn.jw.dao.classSchedule;

import java.util.List;

import com.yice.edu.cn.common.pojo.jw.classSchedule.ClassScheduleInit;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IClassScheduleInitDao {
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    List<ClassScheduleInit> findClassScheduleInitListByCondition(ClassScheduleInit classScheduleInit);

    long findClassScheduleInitCountByCondition(ClassScheduleInit classScheduleInit);

    ClassScheduleInit findOneClassScheduleInitByCondition(ClassScheduleInit classScheduleInit);

    ClassScheduleInit findClassScheduleInitById(@Param("id") String id);

    void saveClassScheduleInit(ClassScheduleInit classScheduleInit);

    void updateClassScheduleInit(ClassScheduleInit classScheduleInit);

    void updateClassScheduleInitForAll(ClassScheduleInit classScheduleInit);

    void deleteClassScheduleInit(@Param("id") String id);

    void deleteClassScheduleInitByCondition(ClassScheduleInit classScheduleInit);

    void batchSaveClassScheduleInit(List<ClassScheduleInit> classScheduleInits);
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/


   void updateSchoolScheduleTime(List<ClassScheduleInit> classScheduleInit);

    /* List<ClassScheduleInit> findSchoolScheduleTimeList(ClassScheduleInit classScheduleInit);*/
}
