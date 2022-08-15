package com.yice.edu.cn.common.pojo.jy.homework;

import lombok.Data;

@Data
public class HomeworkCountQueryVo {
	
	/**
	 * 开始时间 yyyy-MM-dd HH:mm:ss
	 */
	private String beginDate;
	/**
	 * 结束时间 yyyy-MM-dd HH:mm:ss
	 */
	private String endDate;
	/**
	 * 状态 1.准时已提交 2.未提交 3.逾期提交 4.全部
	 */
	private Integer status;

}
