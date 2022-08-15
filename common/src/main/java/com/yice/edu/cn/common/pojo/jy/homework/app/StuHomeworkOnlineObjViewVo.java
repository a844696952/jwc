package com.yice.edu.cn.common.pojo.jy.homework.app;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class StuHomeworkOnlineObjViewVo implements Comparable<StuHomeworkOnlineObjViewVo>{
	/**
	 * 学生Id
	 */
	@ApiModelProperty(value = "学生Id",dataType = "String")
	private String studentId;
	/**
	 * 学生姓名
	 */
	@ApiModelProperty(value = "学生姓名",dataType = "String")
	private String studentName;
	/**
	 * 提交时间
	 */
	@ApiModelProperty(value = "提交时间",dataType = "String")
	private String completeTime;
	/**
	 * 正确率
	 */
	@ApiModelProperty(value = "正确率",dataType = "Integer")
	private Integer correctRate;
	
	@ApiModelProperty(value = "学生头像",dataType = "String")
	private String stuImage;
	
	@ApiModelProperty(value = "学生性别 4.男 5.女",dataType = "String")
	private String sex;
	
	@Override
	public int compareTo(StuHomeworkOnlineObjViewVo o) {
		return this.correctRate.intValue()-o.getCorrectRate().intValue();
	}
}
