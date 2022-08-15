package com.yice.edu.cn.common.pojo.jw.classes.app;

import javax.validation.constraints.NotBlank;

import org.springframework.validation.annotation.Validated;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CheckInStatusQueryVo {

	/**
	 * 日期 
	 */
	@ApiModelProperty(value = "日期",dataType = "String")
//	@NotBlank(message="日期不能为空!")
	private String queryDate;
	/**
	 * 班级id
	 */
//	@NotBlank(message="班级id不能为空!")
	@ApiModelProperty(value = "班级id",dataType = "String")
	private String classesId;
	/**
	 * 考勤状态  ()
	 */
//	@NotBlank(message="考勤状态不能为空!")
	@ApiModelProperty(value = "考勤状态: NORMAL 正常 ,LATE 迟到 ,EARLY 早退,MISS 缺卡  ",dataType = "String")
	private String status;
}
