package com.yice.edu.cn.common.pojo.cc.cloudCourse;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CcUploadParams {
	@ApiModelProperty("文件名称")
	private String name;
	@ApiModelProperty("上传文件的类型 1.共享文件 2.录播文件")
	private Integer type;
	
}
