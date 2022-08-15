package com.yice.edu.cn.common.pojo.jy.homework;

import lombok.Data;

/**
 * 
 * 点评作业
 */
@Data
public class RemarkHomeworkVo {
	/**
	 * 学生作业id
	 */
    private String homewordStudentId;
    /**
     * 点评内容
     */
    private String remarkNote;
}
