package com.yice.edu.cn.jw.dao.timetable;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.yice.edu.cn.common.pojo.jw.timetable.TimetableArrangeTime;

@Mapper
public interface ITimetableArrangeTimeDao {
    List<TimetableArrangeTime> findTimetableArrangeTimeListByCondition(TimetableArrangeTime timetableArrangeTime);

    long findTimetableArrangeTimeCountByCondition(TimetableArrangeTime timetableArrangeTime);

    TimetableArrangeTime findOneTimetableArrangeTimeByCondition(TimetableArrangeTime timetableArrangeTime);

    TimetableArrangeTime findTimetableArrangeTimeById(@Param("id") String id);

    void saveTimetableArrangeTime(TimetableArrangeTime timetableArrangeTime);

    void updateTimetableArrangeTime(TimetableArrangeTime timetableArrangeTime);

    void deleteTimetableArrangeTime(@Param("id") String id);

    void deleteTimetableArrangeTimeByCondition(TimetableArrangeTime timetableArrangeTime);

    void batchSaveTimetableArrangeTime(List<TimetableArrangeTime> timetableArrangeTimes);
}
