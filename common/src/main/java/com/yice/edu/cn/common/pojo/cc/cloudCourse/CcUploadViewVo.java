package com.yice.edu.cn.common.pojo.cc.cloudCourse;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CcUploadViewVo {
	@ApiModelProperty("token")
	private String token;
	@ApiModelProperty("文件上传的路径七牛算key")
	private String path;
	
}
