package com.yice.edu.cn.common.pojo.jw.classes.rise;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 年段长
 *
 */
@ApiModel(value = "年段长",description="")
@Data
public class GradeMaster {
	 @ApiModelProperty(value = "年级id",dataType = "String")
	 private String gradeId;
	 @ApiModelProperty(value = "老师Id",dataType = "String")
	 private String userId;
	 @ApiModelProperty(value = "老师名称",dataType = "String")
	 private String userName;
}
