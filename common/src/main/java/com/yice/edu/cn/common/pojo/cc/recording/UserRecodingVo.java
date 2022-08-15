package com.yice.edu.cn.common.pojo.cc.recording;

import java.util.List;

import lombok.Data;

@Data
public class UserRecodingVo {
	
	private String courseId;//课堂id
	
	private List<UserInfo> usrInfoList;//用户list
	
	

}
