package com.yice.edu.cn.common.pojo.jw.classes;

import lombok.Data;

@Data
public class ClassesInfoViewVo {
	/**
	 * 班主任
	 */
	public String homeroomTeacher;
	/**
	 * 班主任头像
	 */
	public String homeroomTeacherImg;
	/**
	 * 教授课程
	 */
	public String course;
	
	/**
	 * 班长名称
	 */
	public String classMasterName;
	
	/**
	 * 年级名称
	 */
	public String gradeName;
	
	/**
	 * 班级号
	 */
	public String classNo;
	
	/**
	 * 班级总人数
	 */
	public Long stuNum;
}
