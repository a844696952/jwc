package com.yice.edu.cn.common.pojo.jw.classes;

import lombok.Data;

@Data
public class ReturnMsgVo {
	
	/**
	 * 是
	 */
	private boolean sign;
	
	/**
	 * 返回消息
	 */
	private String returnMsg;
}
