package com.yice.edu.cn.jw.dao.timetable;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.yice.edu.cn.common.pojo.jw.timetable.TimetableTime;

@Mapper
public interface ITimetableTimeDao {
    List<TimetableTime> findTimetableTimeListByCondition(TimetableTime timetableTime);

    long findTimetableTimeCountByCondition(TimetableTime timetableTime);

    TimetableTime findOneTimetableTimeByCondition(TimetableTime timetableTime);

    TimetableTime findTimetableTimeById(@Param("id") String id);

    void saveTimetableTime(TimetableTime timetableTime);

    void updateTimetableTime(TimetableTime timetableTime);

    void deleteTimetableTime(@Param("id") String id);

    void deleteTimetableTimeByCondition(TimetableTime timetableTime);

    void batchSaveTimetableTime(List<TimetableTime> timetableTimes);
}
