package com.yice.edu.cn.jy.controller.cloudClassroom.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SRSUtils {
	
	public static String pushUrl;
	
	public static String m3u8Url;

	@Value("${srs.push.host}")
	public  void setPushUrl(String pushUrl) {
		SRSUtils.pushUrl = pushUrl;
	}
	@Value("${srs.m3u8.host}")
	public  void setM3u8Url(String m3u8Url) {
		SRSUtils.m3u8Url = m3u8Url;
	}
	
	public static String getPushUrl(String pre,String broadcastCode) {
		return "rtmp://"+pushUrl+"/"+pre+"/"+broadcastCode;
	}
   
	public static String getPlayM3U8Url(String pre,String broadcastCode) {
		return "http://"+m3u8Url+"/"+pre+"/"+broadcastCode+".m3u8";
	}
}
