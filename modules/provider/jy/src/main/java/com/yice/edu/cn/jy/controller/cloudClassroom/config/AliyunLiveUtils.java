package com.yice.edu.cn.jy.controller.cloudClassroom.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;

@Component
public class AliyunLiveUtils {
	//推流域名
	public static String prePushUrl;
	//推流鉴权key
	private static String pushKey;
	//推流鉴权有效时间
	private static int pushTimeOut;
	//播流域名
	public static String prePlayUrl;
	//播流鉴权key
	private static String playKey;
	//播流鉴权有效时间
	private static int playTimeOut;

	@Value("${aliyun.live.push.host}")
	public  void setPrePushUrl(String prePushUrl) {
		AliyunLiveUtils.prePushUrl = prePushUrl;
	}
	@Value("${aliyun.live.push.key}")
	public  void setPushKey(String pushKey) {
		AliyunLiveUtils.pushKey = pushKey;
	}
	@Value("${aliyun.live.push.timeOut}")
	public  void setPushTimeOut(int pushTimeOut) {
		AliyunLiveUtils.pushTimeOut = pushTimeOut;
	}
	@Value("${aliyun.live.play.host}")
	public  void setPrePlayUrl(String prePlayUrl) {
		AliyunLiveUtils.prePlayUrl = prePlayUrl;
	}
	@Value("${aliyun.live.play.key}")
	public void setPlayKey(String playKey) {
		AliyunLiveUtils.playKey = playKey;
	}
	@Value("${aliyun.live.play.timeOut}")
	public  void setPlayTimeOut(int playTimeOut) {
		AliyunLiveUtils.playTimeOut = playTimeOut;
	}
	/**
	 * 
	 * @param pre 推流前缀(推荐应用名称)
	 * @param broadcastCode 房间码
	 * @return
	 */
	public static String getPushUrl(String pre,String broadcastCode) {
		
        // 计算过期时间
        String timestamp = String.valueOf((System.currentTimeMillis() / 1000) + pushTimeOut);
        // 组合推流域名前缀
        String rtmpUrl = StrUtil.format("rtmp://{}/{}/{}", prePushUrl, pre, broadcastCode);
        // 组合md5加密串
        String md5Url = StrUtil.format("/{}/{}-{}-0-0-{}", pre, broadcastCode, timestamp, pushKey);
        // md5加密
        String md5Str = DigestUtil.md5Hex(md5Url);
        // 组合最终鉴权过的推流域名
        String finallyPushUrl = StrUtil.format("{}?auth_key={}-0-0-{}", rtmpUrl, timestamp, md5Str);
		
        return finallyPushUrl;
	}
   
	public static String getPlayM3U8Url(String pre,String broadcastCode) {
        // 计算过期时间
        String timestamp = String.valueOf((System.currentTimeMillis() / 1000) + playTimeOut);
        // 组合通用域名
        String pullUrl = StrUtil.format("{}/{}/{}", prePlayUrl, pre, broadcastCode);
        // 组合md5加密串
        String md5M3u8Url = StrUtil.format("/{}/{}.m3u8-{}-0-0-{}", pre, broadcastCode, timestamp, playKey);
        // md5加密
        String md5M3u8Str = DigestUtil.md5Hex(md5M3u8Url);
        String m3u8Url = StrUtil.format("http://{}.m3u8?auth_key={}-0-0-{}", pullUrl, timestamp, md5M3u8Str);
		
		return m3u8Url;
	}
    
}
