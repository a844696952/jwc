package com.yice.edu.cn.jw.dao.timetable;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.yice.edu.cn.common.pojo.jw.timetable.ArrangeClass;
import com.yice.edu.cn.common.pojo.jw.timetable.ArrangeCourse;
import com.yice.edu.cn.common.pojo.jw.timetable.ArrangeTeacher;
import com.yice.edu.cn.common.pojo.jw.timetable.TimetableTeachInfo;

@Mapper
public interface ITimetableTeachInfoDao {
    List<TimetableTeachInfo> findTimetableTeachInfoListByCondition(TimetableTeachInfo timetableTeachInfo);

    long findTimetableTeachInfoCountByCondition(TimetableTeachInfo timetableTeachInfo);

    TimetableTeachInfo findOneTimetableTeachInfoByCondition(TimetableTeachInfo timetableTeachInfo);

    TimetableTeachInfo findTimetableTeachInfoById(@Param("id") String id);

    void saveTimetableTeachInfo(TimetableTeachInfo timetableTeachInfo);

    void updateTimetableTeachInfo(TimetableTeachInfo timetableTeachInfo);

    void deleteTimetableTeachInfo(@Param("id") String id);

    void deleteTimetableTeachInfoByCondition(TimetableTeachInfo timetableTeachInfo);

    void batchSaveTimetableTeachInfo(List<TimetableTeachInfo> timetableTeachInfos);
    
    List<ArrangeCourse> findTeachInfoCourseByJobId(@Param("jobId") String jobId);
    
    List<ArrangeTeacher> findTeacherByJobIdAndCourseId(@Param("jobId") String jobId ,@Param("courseId") String courseId);

	List<ArrangeClass> findArrangeClassByJobId(String jobId);
}
