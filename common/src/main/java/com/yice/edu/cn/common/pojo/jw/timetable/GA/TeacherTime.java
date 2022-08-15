package com.yice.edu.cn.common.pojo.jw.timetable.GA;

import lombok.Data;

@Data
public class TeacherTime {
		
	private int id;
	
	private int weeks=1;
	
	private int timeSlot=1;
	
	public TeacherTime() {
		// TODO Auto-generated constructor stub
	}

	public TeacherTime(int id, int weeks, int timeSlot) {
		this.id = id;
		this.weeks = weeks;
		this.timeSlot = timeSlot;
	}

}
