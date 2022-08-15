package com.yice.edu.cn.common.pojo.cc.cloudCourse;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CCWebSocketMsgVo {
	@ApiModelProperty(value = "消息",dataType = "String")
	private String msg;
	@ApiModelProperty(value = "房间码",dataType = "String")
	private String broadcastCode;
	@ApiModelProperty(value = "来源",dataType = "String")
	private String sourceToken;

}
