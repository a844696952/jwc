package com.yice.edu.cn.common.pojo.jw.timetable.GA;

import java.util.Set;

import com.yice.edu.cn.common.pojo.jw.timetable.TimetableTeachInfo;

import lombok.Data;

@Data
public class TeachInfoSplit {
		
	
	private int id;
	
	private String classId;
	
	private String className;
	
	private String teacherId;
	
	private String teacherName;
	
	//预留字段。公共必修,学科必修,学科选修...
	private String nature ;
	
	//预留字段
	private int roomType;
	
	private boolean isMutiTeacher=false;
	
	private Integer level;
	
	private String name;
	
	private String jwCourseId;
	
	//不安排时间数大小
	private int noArrangeSlotlen=0;
	
	private Set<String> noArrangeSlot;
	
	//一次课程教学长度，预留字段
	private Integer len=1;
	
	public TeachInfoSplit() {
		// TODO Auto-generated constructor stub
	}
	
	public TeachInfoSplit(TimetableTeachInfo timetableTeachInfo,int id) {
		// TODO Auto-generated constructor stub
		this.id = id;
		this.classId = timetableTeachInfo.getClassId();
		this.className=timetableTeachInfo.getClassesName();
		this.teacherId = timetableTeachInfo.getTeacherId();
		this.teacherName=timetableTeachInfo.getTeacherName();
		this.nature = "公共必修";
		this.name = timetableTeachInfo.getCourseName();
		this.isMutiTeacher = timetableTeachInfo.getTeacherId().contains(",");
		this.jwCourseId = timetableTeachInfo.getCourseId();
		this.level = 1;
		this.roomType = 1;
		this.len = 1;
		
	}
	

	
}
