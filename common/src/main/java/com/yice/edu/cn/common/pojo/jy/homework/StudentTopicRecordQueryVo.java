package com.yice.edu.cn.common.pojo.jy.homework;

import lombok.Data;

@Data
public class StudentTopicRecordQueryVo {
	
	/**
	 * 作业sqId
	 */
	private String homeworkSqId;

	/**
	 * 题目id
	 */
	private String topicId;
	
	/**
	 * 1.做对 2.做错 3.未提交
	 */
	private String type;
}
