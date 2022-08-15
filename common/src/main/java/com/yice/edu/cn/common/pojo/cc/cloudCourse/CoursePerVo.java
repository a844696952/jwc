package com.yice.edu.cn.common.pojo.cc.cloudCourse;

import lombok.Data;

@Data
public class CoursePerVo {
	
	/**
	 * 用户的id
	 */
	private String userId;
	/**
	 * 用户登陆名称
	 */
	private String userName;
	/**
	 * 用户类型 
	 */
	private Integer userType;
	
	/**
	 * 房间码
	 */
	private String broadcastCode;
	
	/**
	 * 状态 1.在线 2.离线
	 */
	private Integer status;
	
	/**
	 * 创建人
	 */
	private Boolean creator;
	
	/**
	 * 是否是主讲人
	 */
	private Boolean lecturer;
	
	/**
	 * 客户端互动房间唯一标识
	 */
	private String uuId;
	
	/**
	 * 客户端的mac地址
	 */
	private String mac;

}
