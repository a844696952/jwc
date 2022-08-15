package com.yice.edu.cn.jw.service.timetable;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.timetable.Timetable;
import com.yice.edu.cn.common.pojo.jw.timetable.TimetableAdjustCondition;
import com.yice.edu.cn.common.pojo.jw.timetable.TimetableTime;
import com.yice.edu.cn.jw.dao.timetable.ITimetableDao;
import com.yice.edu.cn.jw.dao.timetable.ITimetableTimeDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class TimetableService {
    @Autowired
    private ITimetableDao timetableDao;
    
    @Autowired
	private ITimetableTimeDao timeDao;
    
    @Autowired
    private SequenceId sequenceId;

    @Transactional(readOnly = true)
    public Timetable findTimetableById(String id) {
        return timetableDao.findTimetableById(id);
    }
    @Transactional
    public void saveTimetable(Timetable timetable) {
        timetable.setId(sequenceId.nextId());
        timetableDao.saveTimetable(timetable);
    }
    @Transactional(readOnly = true)
    public List<Timetable> findTimetableListByCondition(Timetable timetable) {
        return timetableDao.findTimetableListByCondition(timetable);
    }
    @Transactional(readOnly = true)
    public Timetable findOneTimetableByCondition(Timetable timetable) {
        return timetableDao.findOneTimetableByCondition(timetable);
    }
    @Transactional(readOnly = true)
    public long findTimetableCountByCondition(Timetable timetable) {
        return timetableDao.findTimetableCountByCondition(timetable);
    }
    @Transactional
    public void updateTimetable(Timetable timetable) {
        timetableDao.updateTimetable(timetable);
    }
    @Transactional
    public void deleteTimetable(String id) {
        timetableDao.deleteTimetable(id);
    }
    @Transactional
    public void deleteTimetableByCondition(Timetable timetable) {
        timetableDao.deleteTimetableByCondition(timetable);
    }
    @Transactional
    public void batchSaveTimetable(List<Timetable> timetables){
        timetables.forEach(timetable -> timetable.setId(sequenceId.nextId()));
        timetableDao.batchSaveTimetable(timetables);
    }
    
    
    @Transactional
    public ResponseJson adjustTimetable(TimetableAdjustCondition condition) {
    	
    	Timetable timetableCondition=new Timetable();
    	timetableCondition.setJobId(condition.getJobId());
    	List<Timetable> timetableList = timetableDao.findTimetableListByCondition(timetableCondition);
    	
    	// TODO
    	if(timetableList==null) {
    		return new ResponseJson(false, "任务Id出错");
    	}else {
    		//获取选择单元格
    		Optional<Timetable> table1 = timetableList.stream().filter(table->Objects.equals(table.getId(),condition.getId())).findFirst();
    		//获取交换单元格
    		Optional<Timetable> table2 = timetableList.stream().filter(table->Objects.equals(table.getId(),condition.getAdjustCellId())).findFirst();
    		if(!table1.isPresent()||!table2.isPresent()) return new ResponseJson(false, "单元格Id不存在");
    		if(table1.get().getTeacherId()==null) return new ResponseJson(false, "当前未选择教师课程");
    		if(table1.get().getTeacherId().contains(",")||table2.get().getTeacherId()!=null&&table2.get().getTeacherId().contains(",")) {
    			
    			String[] split1 = table1.get().getTeacherId().split(",");
    			String[] split2 = table2.get().getTeacherId().split(",");
    			
    			boolean conflictStatus=IntStream.range(0, split1.length).anyMatch(i->{
    				
    				return IntStream.range(0, split2.length).anyMatch(j->{
    					
    					return timetableList.stream().anyMatch(table->Objects.equals(table.getTeacherId(),split1[i])&&Objects.equals(table.getSlot(),table2.get().getSlot())) 
    							|| timetableList.stream().anyMatch(table->Objects.equals(table.getTeacherId(),split2[j])&&Objects.equals(table.getSlot(),table1.get().getSlot()));
    				});
    				
    			});
    			if(conflictStatus) {
    				return new ResponseJson(false, "课程冲突");
    			}
    		}else {
    			if(table2.get().getTeacherId()!=null) {
    				if(timetableList.stream().anyMatch(table->Objects.equals(table.getTeacherId(),table1.get().getTeacherId())&&Objects.equals(table.getSlot(),table2.get().getSlot())) ||timetableList.stream().anyMatch(table->Objects.equals(table.getTeacherId(),table2.get().getTeacherId())&&Objects.equals(table.getSlot(),table1.get().getSlot()))) return new ResponseJson(false, "课程冲突");
    			}else {
    				
    				if(timetableList.stream().anyMatch(table->Objects.equals(table.getTeacherId(),table1.get().getTeacherId())&&Objects.equals(table.getSlot(),table2.get().getSlot()))) return new ResponseJson(false, "课程冲突");
    				
    			}
    			
    		}
    		Timetable timeUpdate=new Timetable(table1.get());
    		timeUpdate.setSlot(table2.get().getSlot());
    		timeUpdate.setTimeSlot(table2.get().getSlot()%7==0?7:table2.get().getSlot()%7);
    		timeUpdate.setWeekdays(table2.get().getSlot()%7==0?table2.get().getSlot()/7:table2.get().getSlot()/7+1);
    		timetableDao.updateTimetable(timeUpdate);
    		
    		Timetable timeUpdate2=new Timetable(table2.get());
    		timeUpdate2.setSlot(table1.get().getSlot());
    		timeUpdate2.setTimeSlot(table1.get().getSlot()%7==0?7:table1.get().getSlot()%7);
    		timeUpdate2.setWeekdays(table1.get().getSlot()%7==0?table1.get().getSlot()/7:table1.get().getSlot()/7+1);
    		timetableDao.updateTimetable(timeUpdate2);
    	}
    	return new ResponseJson(true, "更新成功");
    }
   
    
    /**
     * 
     * @Description: 查询该任务下所有课表  
     * @param jobId 任务Id
     * @param type 1:教师课表; 2:班级课表
     * @return    响应对象    
     * @throws
     */
	public ResponseJson findAllTimetableByJobId(String jobId,Integer type) {
		Map<String,List<Timetable>> timetables=null;
		Timetable timetableCondition=new Timetable();
    	timetableCondition.setJobId(jobId);
    	List<Timetable> timetableList = timetableDao.findTimetableListByCondition(timetableCondition);
		
    	if(timetableList==null||timetableList.size()<1||type>2) {
    		
    		return new ResponseJson(false, "无课表或课表类型错误");
    		
    	}else if(type==1) {
    		timetables=buildTeacherTables(timetableList);
    	}else if(type==2) {
    		//班级课表
    		timetables = timetableList.stream().collect(Collectors.groupingBy(Timetable::getClassId));
    	}else if(type==0) {
    		return new ResponseJson(buildTeacherTables(timetableList), timetableList.stream().collect(Collectors.groupingBy(Timetable::getClassId)));
    	}
    	
		
    	return new ResponseJson(timetables);
	}
	
	
	public boolean judgeTeacherSlot(Map<String, List<Timetable>> teacherTimetable,String teacherId,Integer judgeSlot) {
		
		 Timetable timetableTeacher1 = teacherTimetable.get(teacherId).stream().filter(t->Objects.equals(t.getSlot(), judgeSlot)).findFirst().orElse(null);
		 if(timetableTeacher1==null) {
			 return true;
		 }
		 return false;
	}
	public List<Timetable> findTeacherTimetableByJobId(String jobId, String teacherId) {
		// TODO Auto-generated method stub
		Timetable timetableCondition=new Timetable();
    	timetableCondition.setJobId(jobId);
    	List<Timetable> timetableList = timetableDao.findTimetableListByCondition(timetableCondition);
		
		return timetableList.stream().filter(t->t.getTeacherId()!=null&&t.getTeacherId().contains(teacherId)).collect(Collectors.toList());
	}
	
	public Map<String, List<Timetable>> buildTeacherTables(List<Timetable> timetableList){
		Map<String,List<Timetable>> timetables=new HashMap<>();
		
		//教师课表
		for(Timetable ta:timetableList) {
			String teacherId = ta.getTeacherId();
			if(teacherId!=null) {
				
				//一堂课多老师情况
				if(!teacherId.contains(",")) {
    				if(timetables.get(teacherId) == null) {
    					timetables.put(teacherId, new ArrayList<>());
    				}
    				timetables.get(teacherId).add(ta);
    			}else {
    				String[] split = teacherId.split(",");
    				for(int i=0;i<split.length;i++) {
    					if(timetables.get(split[i]) == null) {
        					timetables.put(split[i], new ArrayList<>());
        				}
    					timetables.get(split[i]).add(ta);
    				}
    			}
			}
			
		}
		
		return timetables;
	}
}
