package com.yice.edu.cn.common.pojo.jy.homework.app;
import java.util.List;

import com.yice.edu.cn.common.pojo.jy.homework.HomeworkStudent;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 
 * 学生线下作业的情况
 */
@Data
public class StuHomeworkOffViewVo {
	/**
	 * 已阅学生作业对象
	 */
	@ApiModelProperty(value = "学生作业对象集合",dataType = "List")
    private List<HomeworkStudent> hasRemarkhomeworkStudentList;
	
	/**
	 * 未阅学生作业对象
	 */
	@ApiModelProperty(value = "学生作业对象集合",dataType = "List")
    private List<HomeworkStudent> notRemarkhomeworkStudentList;
	
	/**
	 * 确认回复/未提交学生作业对象
	 */
	@ApiModelProperty(value = "确认回复/未提交学生作业对象",dataType = "List")
    private List<HomeworkStudent> homeworkStudentList;
    
    /**
     * 未提交数量
     */
	@ApiModelProperty(value = "未提交数量",dataType = "Long")
    private Long hasNotCompleteNum;
    
    /**
     * 已提交数量
     */
	@ApiModelProperty(value = "已提交数量",dataType = "Long")
    private Long hasCompleteNum;
    
    /**
     * 逾期提交数量
     */
	@ApiModelProperty(value = "逾期提交数量",dataType = "Long")
    private Long hasOutTimeCompleteNum;
}
