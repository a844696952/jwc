package com.yice.edu.cn.common.pojo.cc.cloudCourse;

import lombok.Data;

@Data
public class SrsQrCodeVo {
	/**
	 * 二维码图片二进制数组
	 */
	private byte[] qrCodeByte;
	/**
	 * 直播拉流地址
	 */
	private String pullUrl;
	

}
