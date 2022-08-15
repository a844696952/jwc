package com.yice.edu.cn.common.pojo.jw.exam.examManage.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ExamTopicQueryVo {
	@ApiModelProperty(value = "考试id",dataType = "String")
	private String schoolExamId;
	@ApiModelProperty(value = "科目id",dataType = "String")
	private String courseId;
	@ApiModelProperty(value = "班级id",dataType = "String")
	private String clazzId;
	@ApiModelProperty(value = "小题序号",dataType = "Integer")
	private Integer number;
}
