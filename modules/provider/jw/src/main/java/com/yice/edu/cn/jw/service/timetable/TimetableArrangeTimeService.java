package com.yice.edu.cn.jw.service.timetable;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.timetable.TimetableArrangeTime;
import com.yice.edu.cn.jw.dao.timetable.ITimetableArrangeTimeDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TimetableArrangeTimeService {
    @Autowired
    private ITimetableArrangeTimeDao timetableArrangeTimeDao;
    @Autowired
    private SequenceId sequenceId;

    @Transactional(readOnly = true)
    public TimetableArrangeTime findTimetableArrangeTimeById(String id) {
        return timetableArrangeTimeDao.findTimetableArrangeTimeById(id);
    }
    @Transactional
    public ResponseJson saveTimetableArrangeTime(List<TimetableArrangeTime> timetableArrangeTimes) {
    	if(timetableArrangeTimes.stream().anyMatch(time->time.getJobId()==null||time.getTimeSlotPos()==null||time.getType()==null||time.getAssociationId()==null||time.getAssociationName()==null)) {
    		return new ResponseJson(false, "数据参数错误");
    	}
    	
    	if(timetableArrangeTimes.size()>0) {
    		TimetableArrangeTime timeCondition=new TimetableArrangeTime();
    		timeCondition.setJobId(timetableArrangeTimes.get(0).getJobId());
    		timetableArrangeTimeDao.deleteTimetableArrangeTimeByCondition(timeCondition);
    		timetableArrangeTimes.forEach(time->{
        		time.setId(sequenceId.nextId());
        	});
    		timetableArrangeTimeDao.batchSaveTimetableArrangeTime(timetableArrangeTimes);
    		return new ResponseJson(true, "保存成功");
    	}else {
    		
    		return new ResponseJson(false, "数据为空");
    	}
    	
    	
        
    }
    @Transactional(readOnly = true)
    public List<TimetableArrangeTime> findTimetableArrangeTimeListByCondition(TimetableArrangeTime timetableArrangeTime) {
        return timetableArrangeTimeDao.findTimetableArrangeTimeListByCondition(timetableArrangeTime);
    }
    @Transactional(readOnly = true)
    public TimetableArrangeTime findOneTimetableArrangeTimeByCondition(TimetableArrangeTime timetableArrangeTime) {
        return timetableArrangeTimeDao.findOneTimetableArrangeTimeByCondition(timetableArrangeTime);
    }
    @Transactional(readOnly = true)
    public long findTimetableArrangeTimeCountByCondition(TimetableArrangeTime timetableArrangeTime) {
        return timetableArrangeTimeDao.findTimetableArrangeTimeCountByCondition(timetableArrangeTime);
    }
    @Transactional
    public void updateTimetableArrangeTime(TimetableArrangeTime timetableArrangeTime) {
        timetableArrangeTimeDao.updateTimetableArrangeTime(timetableArrangeTime);
    }
    @Transactional
    public void deleteTimetableArrangeTime(String id) {
        timetableArrangeTimeDao.deleteTimetableArrangeTime(id);
    }
    @Transactional
    public void deleteTimetableArrangeTimeByCondition(TimetableArrangeTime timetableArrangeTime) {
        timetableArrangeTimeDao.deleteTimetableArrangeTimeByCondition(timetableArrangeTime);
    }
    @Transactional
    public void batchSaveTimetableArrangeTime(List<TimetableArrangeTime> timetableArrangeTimes){
        timetableArrangeTimes.forEach(timetableArrangeTime -> timetableArrangeTime.setId(sequenceId.nextId()));
        timetableArrangeTimeDao.batchSaveTimetableArrangeTime(timetableArrangeTimes);
    }

}
