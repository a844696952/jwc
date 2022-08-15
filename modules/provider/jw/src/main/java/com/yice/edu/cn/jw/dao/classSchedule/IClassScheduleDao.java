package com.yice.edu.cn.jw.dao.classSchedule;

import com.yice.edu.cn.common.pojo.jw.classSchedule.ClassSchedule;
import com.yice.edu.cn.common.pojo.jw.classSchedule.ScheduleDel;
import com.yice.edu.cn.common.pojo.jw.exam.buildPaper.paper.structure.LikeStatic;
import com.yice.edu.cn.common.pojo.jw.teacher.TeacherClasses;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface IClassScheduleDao {
    List<ClassSchedule> findClassScheduleListByCondition(ClassSchedule classSchedule);

    ClassSchedule findOneClassScheduleByCondition(ClassSchedule classSchedule);

    long findClassScheduleCountByCondition(ClassSchedule classSchedule);

    ClassSchedule findClassScheduleById(String id);

    void saveClassSchedule(ClassSchedule classSchedule);

    void updateClassSchedule(ClassSchedule classSchedule);

    void deleteClassSchedule(String id);

    void deleteClassScheduleByCondition(ClassSchedule classSchedule);

    void batchSaveClassSchedule(List<ClassSchedule> classSchedules);

    List<ClassSchedule> getTeacherNameAndCourseAndCount(ClassSchedule classSchedule);

    List<ClassSchedule> findClassScheduleListByConditions(ClassSchedule classSchedule);

    void spaceIdNull(List<ClassSchedule> classSchedule);

    void courseIdNull(List<ClassSchedule> classSchedules);

    long findClassScheduleListByConditionlong(ClassSchedule classSchedule);

    List<ClassSchedule> verifyImport(@Param("classesId") String classesId);

    String findClassIdByUserName(String userName);

    void deleteSchoolScheduleInClassId(List<String> classesId);

    List<ClassSchedule> findClassScheduleGroupClassId(ClassSchedule classSchedule);

    void deleteSchoolScheduleInClassIdAndScheduleId(ScheduleDel scheduleDel);

    void batchDeleteClassScheduleInScheduleId(List<String> scheduleIds);

    List<ClassSchedule> batchSeleteClassScheduleInScheduleId(List<String> scheduleIds);
}
