package com.yice.edu.cn.common.pojo.jy.homework.app;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class StuHomeworkQueryInfoQueryVo {
	@ApiModelProperty(value = "学生id",dataType = "String")
	private String studentSqId;
	@ApiModelProperty(value = "家庭作业sqId",dataType = "String")
	private String homeworkSqId;

}
