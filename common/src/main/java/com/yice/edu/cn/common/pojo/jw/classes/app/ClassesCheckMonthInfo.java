package com.yice.edu.cn.common.pojo.jw.classes.app;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ClassesCheckMonthInfo {
	
	/**
	 * 班级id
	 */
	@ApiModelProperty(value = "班级id",dataType = "String")
	private String classesId;
	
	/**
	 * 查询月份 yyyy-MM
	 */
	@ApiModelProperty(value = "查询月份yyyy-MM",dataType = "String")
	private String queryDate;

}
