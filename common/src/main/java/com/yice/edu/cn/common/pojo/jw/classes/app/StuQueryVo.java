package com.yice.edu.cn.common.pojo.jw.classes.app;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
@Data
public class StuQueryVo {
	@ApiModelProperty(value = "班级id",dataType = "String")
	private String classesId;
	@ApiModelProperty(value = "查询条件",dataType = "String")
	private String queryCondition;

}
