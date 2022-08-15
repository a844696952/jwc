package com.yice.edu.cn.osp.service.jw.timetable;
import com.yice.edu.cn.common.pojo.jw.timetable.TimetableTime;
import com.yice.edu.cn.osp.feignClient.jw.timetable.TimetableTimeFeign;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TimetableTimeService {
    @Autowired
    private TimetableTimeFeign timetableTimeFeign;

    public TimetableTime findTimetableTimeById(String id) {
        return timetableTimeFeign.findTimetableTimeById(id);
    }
    public TimetableTime saveTimetableTime(TimetableTime timetableTime) {
        return timetableTimeFeign.saveTimetableTime(timetableTime);
    }
    public List<TimetableTime> findTimetableTimeListByCondition(TimetableTime timetableTime) {
        return timetableTimeFeign.findTimetableTimeListByCondition(timetableTime);
    }
    public TimetableTime findOneTimetableTimeByCondition(TimetableTime timetableTime) {
        return timetableTimeFeign.findOneTimetableTimeByCondition(timetableTime);
    }
    public long findTimetableTimeCountByCondition(TimetableTime timetableTime) {
        return timetableTimeFeign.findTimetableTimeCountByCondition(timetableTime);
    }
    public void updateTimetableTime(TimetableTime timetableTime) {
        timetableTimeFeign.updateTimetableTime(timetableTime);
    }
    public void deleteTimetableTime(String id) {
        timetableTimeFeign.deleteTimetableTime(id);
    }
    public void deleteTimetableTimeByCondition(TimetableTime timetableTime) {
        timetableTimeFeign.deleteTimetableTimeByCondition(timetableTime);
    }

}
