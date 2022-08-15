package com.yice.edu.cn.common.pojo.jw.timetable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeachInfo {
		
    private String courseId;
    private String courseName;
    private Integer count;
    private String teacherId;
    private String teacherName;
	
	
}
