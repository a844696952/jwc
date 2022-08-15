package com.yice.edu.cn.common.pojo.jy.homework.app;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class StuHomeworkStatusVo {
	@ApiModelProperty(value = "作业sqId",dataType = "String")
	private String homeworkSqId;
	@ApiModelProperty(value = "学生id",dataType = "String")
	private String studentId;

	@ApiModelProperty(value = "topicsObjId",dataType = "String")
	private String topicsObjId;
}
