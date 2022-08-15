package com.yice.edu.cn.jy.service.cloudClassroom.cloudCourse;

import org.springframework.stereotype.Service;

import com.yice.edu.cn.jy.controller.cloudClassroom.config.LiveUtils;

@Service
public class LiveService {
	
	/**
	 * 
	 * @param pre 推流前缀(建议为应用名称)
	 * @param broadcastCode
	 * @return
	 */
	public String getLivePushUrl(String pre ,String broadcastCode) {
		return LiveUtils.getPushRTMPUrl(pre, broadcastCode);
	}

	/**
	 * 
	 * @param pre 推流前缀(建议为应用名称)
	 * @param broadcastCode
	 * @return
	 */
	public String getLiveM3U8Url(String pre ,String broadcastCode) {
		return LiveUtils.getPlayM3U8Url(pre, broadcastCode);
	}
}
