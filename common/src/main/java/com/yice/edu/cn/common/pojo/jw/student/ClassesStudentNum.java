package com.yice.edu.cn.common.pojo.jw.student;

/**
 * 班级学生人数
 * @author Administrator
 *
 */
public class ClassesStudentNum {
	
	/**
	 * 班级id
	 */
	private String classesId;
	
	/**
	 * 班级拥有的学生人数
	 */
	private String studentNum;

	public String getClassesId() {
		return classesId;
	}

	public void setClassesId(String classesId) {
		this.classesId = classesId;
	}

	public String getStudentNum() {
		return studentNum;
	}

	public void setStudentNum(String studentNum) {
		this.studentNum = studentNum;
	}

}
