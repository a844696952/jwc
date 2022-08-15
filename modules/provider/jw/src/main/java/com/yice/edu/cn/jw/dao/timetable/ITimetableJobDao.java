package com.yice.edu.cn.jw.dao.timetable;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.yice.edu.cn.common.pojo.jw.timetable.TimetableJob;
import com.yice.edu.cn.common.pojo.jw.timetable.TimetableTeachInfo;
import com.yice.edu.cn.common.pojo.jw.timetable.GA.Course;
import com.yice.edu.cn.common.pojo.jw.timetable.GA.TeacherBo;

@Mapper
public interface ITimetableJobDao {
    List<TimetableJob> findTimetableJobListByCondition(TimetableJob timetableJob);

    long findTimetableJobCountByCondition(TimetableJob timetableJob);

    TimetableJob findOneTimetableJobByCondition(TimetableJob timetableJob);

    TimetableJob findTimetableJobById(@Param("id") String id);

    void saveTimetableJob(TimetableJob timetableJob);

    void updateTimetableJob(TimetableJob timetableJob);

    void deleteTimetableJob(@Param("id") String id);

    void deleteTimetableJobByCondition(TimetableJob timetableJob);

    void batchSaveTimetableJob(List<TimetableJob> timetableJobs);
    
    List<TimetableTeachInfo> findTeacherClassAndCourseByGradeId(@Param("gradeId") String gradeId, @Param("schoolId") String schoolId);
    
    List<TeacherBo> findGradeTeacherByGradeId(@Param("gradeId") String gradeId, @Param("schoolId") String schoolId);
    
    List<Course> findCoursingByGradeId(@Param("gradeId") String gradeId, @Param("schoolId") String schoolId);
    
    void deleteAllTimetableData(@Param("id") String id);
}
