package com.yice.edu.cn.common.pojo.jw.timetable;

import java.util.Map;

import lombok.Data;

@Data
public class TeachInfoVo {
		
	private String gradeId;
	private String gradeName;
	private String classId;
	private String classesName;
    
	private Map<String, TeachInfo> teachInfos;
	
}
