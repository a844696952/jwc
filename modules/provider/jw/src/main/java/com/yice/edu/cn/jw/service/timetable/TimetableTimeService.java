package com.yice.edu.cn.jw.service.timetable;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.jw.timetable.TimetableTime;
import com.yice.edu.cn.jw.dao.timetable.ITimetableTimeDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TimetableTimeService {
    @Autowired
    private ITimetableTimeDao timetableTimeDao;
    @Autowired
    private SequenceId sequenceId;

    @Transactional(readOnly = true)
    public TimetableTime findTimetableTimeById(String id) {
        return timetableTimeDao.findTimetableTimeById(id);
    }
    @Transactional
    public TimetableTime saveTimetableTime(TimetableTime timetableTime) {
    	TimetableTime timetableCon=new TimetableTime();
    	timetableCon.setJobId(timetableTime.getJobId());
    	TimetableTime time = findOneTimetableTimeByCondition(timetableCon);
    	if(time==null) {
    		timetableTime.setId(sequenceId.nextId());
            timetableTimeDao.saveTimetableTime(timetableTime);
    	}else {
    		timetableTime.setId(time.getId());
    		timetableTimeDao.updateTimetableTime(timetableTime);
    	}
    	return timetableTime;
    }
    @Transactional(readOnly = true)
    public List<TimetableTime> findTimetableTimeListByCondition(TimetableTime timetableTime) {
        return timetableTimeDao.findTimetableTimeListByCondition(timetableTime);
    }
    @Transactional(readOnly = true)
    public TimetableTime findOneTimetableTimeByCondition(TimetableTime timetableTime) {
        return timetableTimeDao.findOneTimetableTimeByCondition(timetableTime);
    }
    @Transactional(readOnly = true)
    public long findTimetableTimeCountByCondition(TimetableTime timetableTime) {
        return timetableTimeDao.findTimetableTimeCountByCondition(timetableTime);
    }
    @Transactional
    public void updateTimetableTime(TimetableTime timetableTime) {
        timetableTimeDao.updateTimetableTime(timetableTime);
    }
    @Transactional
    public void deleteTimetableTime(String id) {
        timetableTimeDao.deleteTimetableTime(id);
    }
    @Transactional
    public void deleteTimetableTimeByCondition(TimetableTime timetableTime) {
        timetableTimeDao.deleteTimetableTimeByCondition(timetableTime);
    }
    @Transactional
    public void batchSaveTimetableTime(List<TimetableTime> timetableTimes){
        timetableTimes.forEach(timetableTime -> timetableTime.setId(sequenceId.nextId()));
        timetableTimeDao.batchSaveTimetableTime(timetableTimes);
    }

}
