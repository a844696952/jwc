package com.yice.edu.cn.osp.service.jw.timetable;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.timetable.TimetableArrangeTime;
import com.yice.edu.cn.osp.feignClient.jw.timetable.TimetableArrangeTimeFeign;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TimetableArrangeTimeService {
	
    @Autowired
    private TimetableArrangeTimeFeign timetableArrangeTimeFeign;

    
    public ResponseJson saveTimetableArrangeTime(List<TimetableArrangeTime> timetableArrangeTime) {
        return timetableArrangeTimeFeign.saveTimetableArrangeTime(timetableArrangeTime);
    }
    public List<TimetableArrangeTime> findTimetableArrangeTimeListByCondition(TimetableArrangeTime timetableArrangeTime) {
        return timetableArrangeTimeFeign.findTimetableArrangeTimeListByCondition(timetableArrangeTime);
    }
    public TimetableArrangeTime findOneTimetableArrangeTimeByCondition(TimetableArrangeTime timetableArrangeTime) {
        return timetableArrangeTimeFeign.findOneTimetableArrangeTimeByCondition(timetableArrangeTime);
    }
    public long findTimetableArrangeTimeCountByCondition(TimetableArrangeTime timetableArrangeTime) {
        return timetableArrangeTimeFeign.findTimetableArrangeTimeCountByCondition(timetableArrangeTime);
    }
    public void updateTimetableArrangeTime(TimetableArrangeTime timetableArrangeTime) {
        timetableArrangeTimeFeign.updateTimetableArrangeTime(timetableArrangeTime);
    }
    public void deleteTimetableArrangeTime(String id) {
        timetableArrangeTimeFeign.deleteTimetableArrangeTime(id);
    }
    public void deleteTimetableArrangeTimeByCondition(TimetableArrangeTime timetableArrangeTime) {
        timetableArrangeTimeFeign.deleteTimetableArrangeTimeByCondition(timetableArrangeTime);
    }

}
