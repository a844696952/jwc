package com.yice.edu.cn.bmp.service.classSchedule;

import com.yice.edu.cn.bmp.feignClient.classSchedule.ClassScheduleFeign;
import com.yice.edu.cn.bmp.feignClient.classSchedule.ClassScheduleInitFeign;
import com.yice.edu.cn.bmp.feignClient.classSchedule.ScheduleListFeign;
import com.yice.edu.cn.common.pojo.jw.classSchedule.ScheduleList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleListService {
    @Autowired
    private ScheduleListFeign scheduleListFeign;
    @Autowired
    private ClassScheduleFeign classScheduleFeign;
    @Autowired
    private ClassScheduleInitFeign classScheduleInitFeign;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    public ScheduleList findScheduleListById(String id) {
        return scheduleListFeign.findScheduleListById(id);
    }

    public ScheduleList saveScheduleList(ScheduleList scheduleList) {
        return scheduleListFeign.saveScheduleList(scheduleList);
    }

    public void batchSaveScheduleList(List<ScheduleList> scheduleLists){
        scheduleListFeign.batchSaveScheduleList(scheduleLists);
    }

    public List<ScheduleList> findScheduleListListByCondition(ScheduleList scheduleList) {
        return scheduleListFeign.findScheduleListListByCondition(scheduleList);
    }

    public ScheduleList findOneScheduleListByCondition(ScheduleList scheduleList) {
        return scheduleListFeign.findOneScheduleListByCondition(scheduleList);
    }

    public long findScheduleListCountByCondition(ScheduleList scheduleList) {
        return scheduleListFeign.findScheduleListCountByCondition(scheduleList);
    }

    public void updateScheduleList(ScheduleList scheduleList) {
        scheduleListFeign.updateScheduleList(scheduleList);
    }

    public void updateScheduleListForAll(ScheduleList scheduleList) {
        scheduleListFeign.updateScheduleListForAll(scheduleList);
    }

    public void deleteScheduleList(String id) {
        scheduleListFeign.deleteScheduleList(id);
    }

    public void deleteScheduleListByCondition(ScheduleList scheduleList) {
        scheduleListFeign.deleteScheduleListByCondition(scheduleList);
    }
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/

}
