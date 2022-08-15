package com.yice.edu.cn.jy.controller.cloudClassroom.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class LiveUtils {
	
	private static int streamingService;

	@Value("${streaming.service}")
	public void setStreamingService(int streamingService) {
		LiveUtils.streamingService = streamingService;
	}
	
	/**
	 * 获取rtmp推流地址
	 * @param pre 房间前缀(必填)
	 * @param broadcastCode 直播码(必填)
	 * @return
	 */
	public static String getPushRTMPUrl(String pre,String broadcastCode) {
		Assert.notNull(broadcastCode, "broadcastCode is null");
		Assert.notNull(pre, "pre is null");
		return getPushRTMPUrl(LiveUtils.streamingService,pre,broadcastCode);
	}
	
	/**
	 * 获取m3u8播放地址
	 * @param pre 房间前缀(必填)
	 * @param broadcastCode 直播码(必填)
	 * @return
	 */
	public static String getPlayM3U8Url(String pre,String broadcastCode) {
		Assert.notNull(broadcastCode, "broadcastCode is null");
		Assert.notNull(pre, "pre is null");
		return getPlayM3U8Url(LiveUtils.streamingService,pre,broadcastCode);		
	}

	private static String getPushRTMPUrl(int serviceType,String pre,String broadcastCode) {
		String url =null ;
		switch(serviceType) {
		 case 1:
			 url = AliyunLiveUtils.getPushUrl(pre, broadcastCode);
			 break;
		 case 2:
			 url = SRSUtils.getPushUrl(pre, broadcastCode);
			 break;
	     default:
	    	 
	    	 break;
		}
		
		return url;
	}

	private static String getPlayM3U8Url(int serviceType,String pre,String broadcastCode) {
		String url=null;
		switch(serviceType) {
		 case 1:
			 url = AliyunLiveUtils.getPlayM3U8Url(pre, broadcastCode);
			 break;
		 case 2:
			 url = SRSUtils.getPlayM3U8Url(pre, broadcastCode);
			 break;
	     default:
	    	 
	    	 break;
		}
		return url;
	}
	
}
