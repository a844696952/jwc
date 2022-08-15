package com.yice.edu.cn.common.pojo.jw.timetable;


import javax.validation.constraints.NotNull;

import com.yice.edu.cn.common.pojo.validateClass.GroupOne;
import com.yice.edu.cn.common.pojo.validateClass.GroupTwo;

import lombok.Data;

@Data
public class TimetableAdjustCondition {
		
		@NotNull(groups=GroupTwo.class,message="单元格Id")
		private String id;
	
		@NotNull(groups= {GroupOne.class,GroupTwo.class},message="jobId不能为空")
		private String jobId;
		
		@NotNull(groups=GroupOne.class,message="teacherId不能为空")
		private String teacherId;
		
		@NotNull(groups={GroupOne.class},message="classId不能为空")
		private String classId;
		
		@NotNull(groups=GroupOne.class,message="课表位置不能为空")
		private Integer slot;
		
		//1:根据教师调整;2:根据班级调整
		@NotNull(groups={GroupOne.class},message="类型不能为空")
		private Integer type;
		
		@NotNull(groups=GroupTwo.class,message="目标单元格Id")
		private String adjustCellId;
		
}
