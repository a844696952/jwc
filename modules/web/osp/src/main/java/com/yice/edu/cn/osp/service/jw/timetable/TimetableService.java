package com.yice.edu.cn.osp.service.jw.timetable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.timetable.Timetable;
import com.yice.edu.cn.common.pojo.jw.timetable.TimetableAdjustCondition;
import com.yice.edu.cn.osp.feignClient.jw.timetable.TimetableFeign;

import java.util.List;

@Service
public class TimetableService {
    @Autowired
    private TimetableFeign timetableFeign;

    public Timetable findTimetableById(String id) {
        return timetableFeign.findTimetableById(id);
    }

    public Timetable saveTimetable(Timetable timetable) {
        return timetableFeign.saveTimetable(timetable);
    }

    public List<Timetable> findTimetableListByCondition(Timetable timetable) {
        return timetableFeign.findTimetableListByCondition(timetable);
    }

    public Timetable findOneTimetableByCondition(Timetable timetable) {
        return timetableFeign.findOneTimetableByCondition(timetable);
    }

    public long findTimetableCountByCondition(Timetable timetable) {
        return timetableFeign.findTimetableCountByCondition(timetable);
    }

    public void updateTimetable(Timetable timetable) {
        timetableFeign.updateTimetable(timetable);
    }

    public void deleteTimetable(String id) {
        timetableFeign.deleteTimetable(id);
    }

    public void deleteTimetableByCondition(Timetable timetable) {
        timetableFeign.deleteTimetableByCondition(timetable);
    }
    
    public ResponseJson findAllTimetableByJobId(String jobId,Integer type){
    	
    	return timetableFeign.findAllTimetableByJobId(jobId, type);
    }


	public List<Timetable> findTeacherTimetableByJobId(String jobId, String teacherId) {
		return timetableFeign.findTeacherTimetableByJobId(jobId,teacherId);
	}

	public ResponseJson adjustTimetable(TimetableAdjustCondition condition) {
		return timetableFeign.adjustTimetable(condition);
	}
}
