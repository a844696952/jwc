package com.yice.edu.cn.jw.dao.classSchedule;

import java.util.List;

import com.yice.edu.cn.common.pojo.jw.classSchedule.ScheduleList;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ScheduleListDao {
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    List<ScheduleList> findScheduleListListByCondition(ScheduleList scheduleList);

    long findScheduleListCountByCondition(ScheduleList scheduleList);

    ScheduleList findOneScheduleListByCondition(ScheduleList scheduleList);

    ScheduleList findScheduleListById(@Param("id") String id);

    void saveScheduleList(ScheduleList scheduleList);

    void updateScheduleList(ScheduleList scheduleList);

    void updateScheduleListForAll(ScheduleList scheduleList);

    void deleteScheduleList(@Param("id") String id);

    void deleteScheduleListByCondition(ScheduleList scheduleList);

    void batchSaveScheduleList(List<ScheduleList> scheduleLists);
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/

    void batchUpdateScheduleList(List<ScheduleList> scheduleLists);
}
