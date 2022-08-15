package com.yice.edu.cn.common.pojo.jw.timetable.GA;


import lombok.Data;

@Data
public class TeacherBo {
		
	private String id;
	
	private String name;
	
//	private String courseId;
	
	private String courseName;
	
//	List<Course> couses;
	
	public TeacherBo() {
		// TODO Auto-generated constructor stub
	}

	public TeacherBo(String id, String name) {
		this.id = id;
		this.name = name;
	}
	
}
