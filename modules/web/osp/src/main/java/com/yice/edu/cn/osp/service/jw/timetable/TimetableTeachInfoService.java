package com.yice.edu.cn.osp.service.jw.timetable;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.timetable.ArrangeClass;
import com.yice.edu.cn.common.pojo.jw.timetable.ArrangeCourse;
import com.yice.edu.cn.common.pojo.jw.timetable.ArrangeTeacher;
import com.yice.edu.cn.common.pojo.jw.timetable.TeachInfoBo;
import com.yice.edu.cn.common.pojo.jw.timetable.TimetableTeachInfo;
import com.yice.edu.cn.osp.feignClient.jw.timetable.TimetableTeachInfoFeign;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TimetableTeachInfoService {
    @Autowired
    private TimetableTeachInfoFeign timetableTeachInfoFeign;

    public TimetableTeachInfo findTimetableTeachInfoById(String id) {
        return timetableTeachInfoFeign.findTimetableTeachInfoById(id);
    }
    public ResponseJson saveTimetableTeachInfo(TeachInfoBo teachInfos) {
        return timetableTeachInfoFeign.saveTimetableTeachInfo(teachInfos);
    }
    public List<TimetableTeachInfo> findTimetableTeachInfoListByCondition(TimetableTeachInfo timetableTeachInfo) {
        return timetableTeachInfoFeign.findTimetableTeachInfoListByCondition(timetableTeachInfo);
    }
    public TimetableTeachInfo findOneTimetableTeachInfoByCondition(TimetableTeachInfo timetableTeachInfo) {
        return timetableTeachInfoFeign.findOneTimetableTeachInfoByCondition(timetableTeachInfo);
    }
    public long findTimetableTeachInfoCountByCondition(TimetableTeachInfo timetableTeachInfo) {
        return timetableTeachInfoFeign.findTimetableTeachInfoCountByCondition(timetableTeachInfo);
    }
    public void updateTimetableTeachInfo(TimetableTeachInfo timetableTeachInfo) {
        timetableTeachInfoFeign.updateTimetableTeachInfo(timetableTeachInfo);
    }
    public void deleteTimetableTeachInfo(String id) {
        timetableTeachInfoFeign.deleteTimetableTeachInfo(id);
    }
    public void deleteTimetableTeachInfoByCondition(TimetableTeachInfo timetableTeachInfo) {
        timetableTeachInfoFeign.deleteTimetableTeachInfoByCondition(timetableTeachInfo);
    }
    
    public List<ArrangeCourse> findTeachInfoCourseByJobId( String jobId){
    	return timetableTeachInfoFeign.findTeachInfoCourseByJobId(jobId);
    }
    
    public List<ArrangeTeacher> findTeacherByJobIdAndCourseId(String jobId , String courseId){
    	
    	return timetableTeachInfoFeign.findTeacherByJobIdAndCourseId(jobId, courseId);
    }
	public List<ArrangeClass> findArrangeClassByJobId(String jobId) {
		return timetableTeachInfoFeign.findArrangeClassByJobId(jobId);
	}

}
