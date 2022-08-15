package com.yice.edu.cn.common.pojo.jw.classes.app;

import lombok.Data;

@Data
public class TapTeachClassesInfoResultVo {
	/**
	 * 班级Id
	 */
	private String classesId;
	/**
	 * 年级名称
	 */
	private String gradeName;
	/**
	 * 年级id
	 */
	private String gradeId;
	/**
	 * 班级号
	 */
	private String classNum;
	/**
	 * 学生人数
	 */
	private String studentNum;
	/**
	 * 班级场地名称
	 */
	private String placeName;
	/**
	 * 班主任名称
	 */
	private String masterTeacher;
	


}
