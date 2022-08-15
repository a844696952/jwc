package com.yice.edu.cn.jw.dao.classSchedule;

import java.util.List;

import com.yice.edu.cn.common.pojo.jw.classSchedule.ClassScheduleNoonBreak;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IClassScheduleNoonBreakDao {
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    List<ClassScheduleNoonBreak> findClassScheduleNoonBreakListByCondition(ClassScheduleNoonBreak classScheduleNoonBreak);

    long findClassScheduleNoonBreakCountByCondition(ClassScheduleNoonBreak classScheduleNoonBreak);

    ClassScheduleNoonBreak findOneClassScheduleNoonBreakByCondition(ClassScheduleNoonBreak classScheduleNoonBreak);

    ClassScheduleNoonBreak findClassScheduleNoonBreakById(@Param("id") String id);

    void saveClassScheduleNoonBreak(ClassScheduleNoonBreak classScheduleNoonBreak);

    void updateClassScheduleNoonBreak(ClassScheduleNoonBreak classScheduleNoonBreak);

    void updateClassScheduleNoonBreakForAll(ClassScheduleNoonBreak classScheduleNoonBreak);

    void deleteClassScheduleNoonBreak(@Param("id") String id);

    void deleteClassScheduleNoonBreakByCondition(ClassScheduleNoonBreak classScheduleNoonBreak);

    void batchSaveClassScheduleNoonBreak(List<ClassScheduleNoonBreak> classScheduleNoonBreaks);
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/

    void updateClassScheduleNoobBreakNumber(ClassScheduleNoonBreak classScheduleNoonBreak);


}
