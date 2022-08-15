package com.yice.edu.cn.common.util;

public class VideoThumbnailUtils {

	public static String QINIU_THUMBNAIL = "?vframe/jpg/offset/1";

	/**
	 * 
	 * @param type 1七牛 2
	 * @return
	 */
	public static String getVideoThumbnailUtils(int type) {

		String url="";
		switch (type) {
		case 1:
			url = QINIU_THUMBNAIL;
			break;

		default:
			break;
		}
		
		return url;

	}

}
