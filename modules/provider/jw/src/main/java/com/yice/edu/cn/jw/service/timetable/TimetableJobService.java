package com.yice.edu.cn.jw.service.timetable;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.JwCourse.JwCourse;
import com.yice.edu.cn.common.pojo.jw.timetable.TeachInfo;
import com.yice.edu.cn.common.pojo.jw.timetable.TeachInfoVo;
import com.yice.edu.cn.common.pojo.jw.timetable.Timetable;
import com.yice.edu.cn.common.pojo.jw.timetable.TimetableArrangeTime;
import com.yice.edu.cn.common.pojo.jw.timetable.TimetableJob;
import com.yice.edu.cn.common.pojo.jw.timetable.TimetableTeachInfo;
import com.yice.edu.cn.common.pojo.jw.timetable.TimetableTime;
import com.yice.edu.cn.common.pojo.jw.timetable.GA.Course;
import com.yice.edu.cn.common.pojo.jw.timetable.GA.TeacherBo;
import com.yice.edu.cn.jw.dao.jwCourse.JwCourseDao;
import com.yice.edu.cn.jw.dao.timetable.ITimetableArrangeTimeDao;
import com.yice.edu.cn.jw.dao.timetable.ITimetableDao;
import com.yice.edu.cn.jw.dao.timetable.ITimetableJobDao;
import com.yice.edu.cn.jw.dao.timetable.ITimetableTeachInfoDao;
import com.yice.edu.cn.jw.dao.timetable.ITimetableTimeDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TimetableJobService {
    @Autowired
    private ITimetableJobDao timetableJobDao;
    
    @Autowired
    private ITimetableTeachInfoDao timetableTeachInfoDao;
    
    @Autowired
    private ITimetableArrangeTimeDao timetableArrangeTimeDao;
    
    @Autowired
    private ITimetableTimeDao timetableTimeDao;
    
    @Autowired
    private ITimetableDao timetableDao;
    
    @Autowired
    private SequenceId sequenceId;
    
    @Autowired
    private JwCourseDao jwCourseDao;

    @Transactional(readOnly = true)
    public TimetableJob findTimetableJobById(String id) {
        return timetableJobDao.findTimetableJobById(id);
    }
    @Transactional
    public TimetableJob saveTimetableJob(TimetableJob timetableJob) {
        timetableJob.setId(sequenceId.nextId());
        timetableJobDao.saveTimetableJob(timetableJob);
        return timetableJob;
    }
    @Transactional(readOnly = true)
    public ResponseJson findTimetableJobListByCondition(TimetableJob timetableJob) {
    	long count = timetableJobDao.findTimetableJobCountByCondition(timetableJob);
    	List<TimetableJob> timetableJobList = timetableJobDao.findTimetableJobListByCondition(timetableJob);
    	return new ResponseJson(timetableJobList, count);
    }
    @Transactional(readOnly = true)
    public TimetableJob findOneTimetableJobByCondition(TimetableJob timetableJob) {
        return timetableJobDao.findOneTimetableJobByCondition(timetableJob);
    }
    @Transactional(readOnly = true)
    public long findTimetableJobCountByCondition(TimetableJob timetableJob) {
        return timetableJobDao.findTimetableJobCountByCondition(timetableJob);
    }
    @Transactional
    public void updateTimetableJob(TimetableJob timetableJob) {
        timetableJobDao.updateTimetableJob(timetableJob);
    }
    @Transactional
    public void deleteTimetableJob(String id) {
    	
        timetableJobDao.deleteAllTimetableData(id);
        
    }
    @Transactional
    public void deleteTimetableJobByCondition(TimetableJob timetableJob) {
        timetableJobDao.deleteTimetableJobByCondition(timetableJob);
    }
    @Transactional
    public void batchSaveTimetableJob(List<TimetableJob> timetableJobs){
        timetableJobs.forEach(timetableJob -> timetableJob.setId(sequenceId.nextId()));
        timetableJobDao.batchSaveTimetableJob(timetableJobs);
    }
    
    
    public ResponseJson findTeacherInfoByGradeId(String gradeId,String schoolId, String jobId) {
    	List<TimetableTeachInfo> teachInfos=null;
    	TimetableTeachInfo teachInfoCondition=new TimetableTeachInfo();
    	teachInfoCondition.setJobId(jobId);
    	teachInfos=timetableTeachInfoDao.findTimetableTeachInfoListByCondition(teachInfoCondition);
    	if(teachInfos==null||teachInfos.size()<1) {
        	teachInfos = timetableJobDao.findTeacherClassAndCourseByGradeId(gradeId, schoolId);
    	}
    	List<Course> courses=timetableJobDao.findCoursingByGradeId(gradeId, schoolId);
    	if(courses!=null) courses=courses.stream().map(cou->{
    		cou.setId("c"+cou.getId());
    		return cou;
    		}).collect(Collectors.toList());
    	
    	List<TeachInfoVo> teaVo=null;
    	if(teachInfos!=null&&teachInfos.size()>0) {
    		//目前暂时产品只考虑年级课表
    		Map<String, List<TimetableTeachInfo>> collect = teachInfos.stream().collect(Collectors.groupingBy(TimetableTeachInfo::getClassId));
    		teaVo=new ArrayList<>();
    		for (List<TimetableTeachInfo> timetableTeachInfos : collect.values()) {
    			TeachInfoVo vo=new TeachInfoVo();
    			TimetableTeachInfo timetableTeachInfo = timetableTeachInfos.get(0);
    			Map<String, TeachInfo> collect2 = timetableTeachInfos.stream().collect(Collectors.toMap(t->"c"+t.getCourseId(), t->{
    				TeachInfo tea=new TeachInfo(t.getCourseId(), t.getCourseName(), t.getCount()==null?0:t.getCount(), t.getTeacherId(), t.getTeacherName());
    				return tea;
    			}, (m1,m2)->{
    				m1.setTeacherId(m1.getTeacherId()+","+m2.getTeacherId());
    				m1.setTeacherName(m1.getTeacherName()+","+m2.getTeacherName());
    				return m1;
    			}));
    			vo.setTeachInfos(collect2);
    			vo.setGradeId(timetableTeachInfo.getGradeId());
    			vo.setGradeName(timetableTeachInfo.getGradeName());
    			vo.setClassId(timetableTeachInfo.getClassId());
    			vo.setClassesName(timetableTeachInfo.getClassesName());
    			teaVo.add(vo);
			}
    		
    	}
    	return teaVo==null||courses==null?new ResponseJson(false, "未查询到数据"):new ResponseJson(teaVo, courses);
    	
    	
    }
    
    public ResponseJson findTeacherAndCourse(TimetableJob timetableJob) {
    	
    	JwCourse couCondition=new JwCourse();
		couCondition.setGradeId(timetableJob.getGradeIds());
		couCondition.setSchoolId(timetableJob.getSchoolId());
		List<JwCourse> jwCourses = jwCourseDao.findJwCourseListByCondition(couCondition);
		
		List<TeacherBo> gradeTeacher = timetableJobDao.findGradeTeacherByGradeId(timetableJob.getGradeIds(), timetableJob.getSchoolId());
		
		Collection<TeacherBo> gradeTeachers = gradeTeacher.stream().collect(Collectors.toMap(TeacherBo::getId, t->t,(t1,t2)->{
			if(t1.getCourseName().contains(t2.getCourseName())) {
				return t1;
			}else {
				t1.setCourseName(t1.getCourseName()+","+t2.getCourseName());
				return t1;
			}
			
			
		})).values();
		
		return new ResponseJson(jwCourses, gradeTeachers);
    }
    
    @Transactional
	public ResponseJson copyJob(String jobId, String rename) {
		// TODO Auto-generated method stub
    	String copy2JobId=sequenceId.nextId();
    	TimetableJob job = timetableJobDao.findTimetableJobById(jobId);
    	if(job!=null) {
    		job.setName(rename);
        	job.setId(copy2JobId);
        	timetableJobDao.saveTimetableJob(job);
    	}
    	
    	
    	TimetableArrangeTime arrangeCondition=new TimetableArrangeTime();
    	arrangeCondition.setJobId(jobId);
    	List<TimetableArrangeTime> arrangeTimes = timetableArrangeTimeDao.findTimetableArrangeTimeListByCondition(arrangeCondition);
    	if(arrangeTimes!=null&&arrangeTimes.size()>0) {
    		arrangeTimes.forEach(ti->{
    			ti.setJobId(copy2JobId);
    			ti.setId(sequenceId.nextId());
    		});
    		timetableArrangeTimeDao.batchSaveTimetableArrangeTime(arrangeTimes);
    	}
    	
    	TimetableTeachInfo teachInfoCondition=new TimetableTeachInfo();
    	teachInfoCondition.setJobId(jobId);
    	List<TimetableTeachInfo> TeachInfos = timetableTeachInfoDao.findTimetableTeachInfoListByCondition(teachInfoCondition);
    	if(TeachInfos!=null&&TeachInfos.size()>0) {
    		TeachInfos.forEach(tea->{
    			tea.setJobId(copy2JobId);
    			tea.setId(sequenceId.nextId());
    		});
    		timetableTeachInfoDao.batchSaveTimetableTeachInfo(TeachInfos);
    	}
    	
    	TimetableTime timeCondition=new TimetableTime();
    	timeCondition.setJobId(jobId);
    	TimetableTime timetableTime = timetableTimeDao.findOneTimetableTimeByCondition(timeCondition);
    	if(timetableTime!=null) {
    		timetableTime.setJobId(copy2JobId);
    		timetableTime.setId(sequenceId.nextId());
    		timetableTimeDao.saveTimetableTime(timetableTime);
    	}
    	
    	Timetable timetableCondition=new Timetable();
    	timetableCondition.setJobId(jobId);
    	List<Timetable> timetableList = timetableDao.findTimetableListByCondition(timetableCondition);
    	if(timetableList!=null&&timetableList.size()>0) {
    		timetableList.forEach(l->{
    			l.setJobId(copy2JobId);
    			l.setId(sequenceId.nextId());
    		});
    		timetableDao.batchSaveTimetable(timetableList);
    	}
    	
		return new ResponseJson(true, "操作成功");
	}
    
}
