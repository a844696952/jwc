package com.yice.edu.cn.common.pojo.jy.homework;

import java.util.List;

import lombok.Data;

/**
 * 
 * 查询要点评作业的情况
 */
@Data
public class RemarkHomeworkViewVo {
	/**
	 * 学生作业对象
	 */
    private List<HomeworkStudent> homeworkStudentList;
    
    /**
     * 已点评数量
     */
    private Long hasRemarkNum;
    /**
     * 未点评数量
     */
    private Long hasNotRemarkNum;
    /**
     * 未提交数量
     */
    private Long hasNotComplete;
    
    /**
     * 已提交数量
     */
    private Long hasComplete;
}
