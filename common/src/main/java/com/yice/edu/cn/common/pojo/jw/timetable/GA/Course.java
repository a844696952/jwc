package com.yice.edu.cn.common.pojo.jw.timetable.GA;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Course {
		
	private String id;
	
	private Integer level;
	
	private String name;
	
	//130-195
	private String ddId;
	
	//一次课程教学长度
	private Integer len;
	
	public Course() {
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	
}
