package com.yice.edu.cn.common.pojo.jy.homework.app;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class StuTopicRecordVo {
	
	/**
	 * 学生id
	 */
	@ApiModelProperty(value = "学生id",dataType = "String")
	public String studentId;
	/**
	 * 题目sqId
	 */
	@ApiModelProperty(value = "题目sqId",dataType = "String")
	public String topicSqId;
	/**
	 * 作业sqId
	 */
	@ApiModelProperty(value = "作业sqId",dataType = "String")
	public String homeworkSqId;
	
	

}
