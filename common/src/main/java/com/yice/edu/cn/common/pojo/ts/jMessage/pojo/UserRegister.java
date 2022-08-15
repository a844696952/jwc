package com.yice.edu.cn.common.pojo.ts.jMessage.pojo;

import lombok.Data;

@Data
public class UserRegister {
	
	/**
	 * 登陆用户名
	 */
	private String userName;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 聊天显示姓名
	 */
	private String showName;
	/**
	 * 性别  1.男 2.女
	 */
	private Integer sex;
	
	/**
	 * 用户类型  1.老师 2.学生/家长
	 */
    private String userType;
    
    /**
     * 默认的头像设置
     */
    private String defaultHeadImage;

}
