package com.yice.edu.cn.osp.service.jw.timetable;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.timetable.TimetableJob;
import com.yice.edu.cn.osp.feignClient.jw.timetable.TimetableJobFeign;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;


@Service
public class TimetableJobService {
    @Autowired
    private TimetableJobFeign timetableJobFeign;
    
    
    public TimetableJob findTimetableJobById(String id){
        return timetableJobFeign.findTimetableJobById(id);
    }
    
    public TimetableJob saveTimetableJob(TimetableJob timetableJob) {
        return timetableJobFeign.saveTimetableJob(timetableJob);
    }
    public ResponseJson findTimetableJobListByCondition(TimetableJob timetableJob) {
        return timetableJobFeign.findTimetableJobListByCondition(timetableJob);
    }
    public void updateTimetableJob(TimetableJob timetableJob) {
        timetableJobFeign.updateTimetableJob(timetableJob);
    }
    public void deleteTimetableJob(String id) {
        timetableJobFeign.deleteTimetableJob(id);
    }
    
    public ResponseJson findTeacherInfoByGradeId(String gradeId,String schoolId, String jobId) {
    	ResponseJson teachInfos = timetableJobFeign.findTeacherInfoByGradeId(gradeId, schoolId,jobId);
    	
    	return teachInfos;
    }
    
    public ResponseJson startScheduling(@PathVariable("jobId") String jobId) {
    	
    	return timetableJobFeign.startScheduling(jobId);
    }
    
    public ResponseJson findTeacherAndCourse(TimetableJob timetableJob) {
    	
    	return timetableJobFeign.findTeacherAndCourse(timetableJob);
    	
    }
    
    public ResponseJson copyJob(String jobId, String rename) {
    	
    	return timetableJobFeign.copyJob(jobId,rename);
    }
}
