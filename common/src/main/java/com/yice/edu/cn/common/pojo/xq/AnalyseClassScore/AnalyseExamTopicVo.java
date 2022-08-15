package com.yice.edu.cn.common.pojo.xq.AnalyseClassScore;

import lombok.Data;

@Data
public class AnalyseExamTopicVo {
	
	/**
	 * 课程id
	 */
	private String courseId;

	/**
	 * 题目序号
	 */
	private Integer topicNum;
	
	/**
	 * 年段平均分
	 */
	private Double gradeAvgMarkers;
	
	/**
	 * 年段得分率
	 */
	private Double gradeScoreRate;

}
