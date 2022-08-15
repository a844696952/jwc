package com.yice.edu.cn.jw.service.timetable;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.timetable.ArrangeClass;
import com.yice.edu.cn.common.pojo.jw.timetable.ArrangeCourse;
import com.yice.edu.cn.common.pojo.jw.timetable.ArrangeTeacher;
import com.yice.edu.cn.common.pojo.jw.timetable.TeachInfo;
import com.yice.edu.cn.common.pojo.jw.timetable.TeachInfoBo;
import com.yice.edu.cn.common.pojo.jw.timetable.TeachInfoVo;
import com.yice.edu.cn.common.pojo.jw.timetable.TimetableTeachInfo;
import com.yice.edu.cn.jw.dao.timetable.ITimetableTeachInfoDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class TimetableTeachInfoService {
    @Autowired
    private ITimetableTeachInfoDao timetableTeachInfoDao;
    @Autowired
    private SequenceId sequenceId;

    @Transactional(readOnly = true)
    public TimetableTeachInfo findTimetableTeachInfoById(String id) {
        return timetableTeachInfoDao.findTimetableTeachInfoById(id);
    }
    @Transactional
    public ResponseJson saveTimetableTeachInfo(TeachInfoBo timetableTeachInfo) {
    	
    	List<TimetableTeachInfo> timetableTeachInfos=new ArrayList<>();
    	
    	String jobId = timetableTeachInfo.getJobId();
    	List<TeachInfoVo> teachInfos = timetableTeachInfo.getTeachInfos();
    	if(teachInfos!=null&&teachInfos.size()>0) {
    		
    		teachInfos.stream().forEach(teachInfo->{
    			
    			Map<String, TeachInfo> teachInfos2 = teachInfo.getTeachInfos();
    			teachInfos2.values().forEach(teachInfo2->{
    				TimetableTeachInfo timetableTeach=new TimetableTeachInfo();
    				timetableTeach.setId(sequenceId.nextId());
    				timetableTeach.setJobId(jobId);
    				timetableTeach.setGradeId(teachInfo.getGradeId());
    				timetableTeach.setGradeName(teachInfo.getGradeName());
    				timetableTeach.setClassId(teachInfo.getClassId());
    				timetableTeach.setClassesName(teachInfo.getClassesName());
    				timetableTeach.setCount(teachInfo2.getCount()==null?0:teachInfo2.getCount());
    				timetableTeach.setCourseId(teachInfo2.getCourseId());
    				timetableTeach.setCourseName(teachInfo2.getCourseName());
    				timetableTeach.setTeacherId(teachInfo2.getTeacherId());
    				timetableTeach.setTeacherName(teachInfo2.getTeacherName());
    				timetableTeachInfos.add(timetableTeach);
    			});
    			
    		});
    		TimetableTeachInfo  teachInfoCondition=new TimetableTeachInfo();
    		teachInfoCondition.setJobId(jobId);
    		timetableTeachInfoDao.deleteTimetableTeachInfoByCondition(teachInfoCondition);
    		timetableTeachInfoDao.batchSaveTimetableTeachInfo(timetableTeachInfos);
    		return new ResponseJson(true, "保存成功");
    	}else {
    		return new ResponseJson(false, "保存失败,教学信息为空");
    	}
    	
    	
    }
    @Transactional(readOnly = true)
    public List<TimetableTeachInfo> findTimetableTeachInfoListByCondition(TimetableTeachInfo timetableTeachInfo) {
        return timetableTeachInfoDao.findTimetableTeachInfoListByCondition(timetableTeachInfo);
    }
    @Transactional(readOnly = true)
    public TimetableTeachInfo findOneTimetableTeachInfoByCondition(TimetableTeachInfo timetableTeachInfo) {
        return timetableTeachInfoDao.findOneTimetableTeachInfoByCondition(timetableTeachInfo);
    }
    @Transactional(readOnly = true)
    public long findTimetableTeachInfoCountByCondition(TimetableTeachInfo timetableTeachInfo) {
        return timetableTeachInfoDao.findTimetableTeachInfoCountByCondition(timetableTeachInfo);
    }
    @Transactional
    public void updateTimetableTeachInfo(TimetableTeachInfo timetableTeachInfo) {
        timetableTeachInfoDao.updateTimetableTeachInfo(timetableTeachInfo);
    }
    @Transactional
    public void deleteTimetableTeachInfo(String id) {
        timetableTeachInfoDao.deleteTimetableTeachInfo(id);
    }
    @Transactional
    public void deleteTimetableTeachInfoByCondition(TimetableTeachInfo timetableTeachInfo) {
        timetableTeachInfoDao.deleteTimetableTeachInfoByCondition(timetableTeachInfo);
    }
    @Transactional
    public void batchSaveTimetableTeachInfo(List<TimetableTeachInfo> timetableTeachInfos){
        timetableTeachInfos.forEach(timetableTeachInfo -> timetableTeachInfo.setId(sequenceId.nextId()));
        timetableTeachInfoDao.batchSaveTimetableTeachInfo(timetableTeachInfos);
    }
    
    public List<ArrangeCourse> findTeachInfoCourseByJobId(String jobId){
    	return timetableTeachInfoDao.findTeachInfoCourseByJobId(jobId);
    }
    
    public List<ArrangeTeacher> findTeacherByJobIdAndCourseId(String jobId ,String courseId){
    	List<ArrangeTeacher> arrayTeachers=new ArrayList<>();
    	List<ArrangeTeacher> array=timetableTeachInfoDao.findTeacherByJobIdAndCourseId(jobId, courseId);
    	if(array!=null) {
    		array.forEach(c->{
    			
    			if(c.getId().contains(",")) {
    				String[] split = c.getId().split(",");
    				String[] split2 = c.getName().split(",");
    				for(int i=0;i<split.length;i++) {
    					ArrangeTeacher arra=new ArrangeTeacher();
    					arra.setId(split[i]);
    					arra.setName(split2[i]);
    					arrayTeachers.add(arra);
    				}
    				
    			}else {
    				arrayTeachers.add(c);
    			}
    		});
    	}
    	
    	return arrayTeachers;
    }
	public List<ArrangeClass> findArrangeClassByJobId(String jobId) {
		// TODO Auto-generated method stub
		return timetableTeachInfoDao.findArrangeClassByJobId(jobId);
	}

}
