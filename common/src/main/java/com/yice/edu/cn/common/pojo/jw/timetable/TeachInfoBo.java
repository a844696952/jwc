package com.yice.edu.cn.common.pojo.jw.timetable;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.yice.edu.cn.common.pojo.validateClass.GroupOne;

import lombok.Data;

@Data
public class TeachInfoBo {
	
	@NotNull(groups= {GroupOne.class},message="任务Id不能为空")
	private String jobId;
	
	@NotNull(groups= {GroupOne.class},message="任课信息不能为空")
	private List<TeachInfoVo> teachInfos;
	
}
