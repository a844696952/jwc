package com.yice.edu.cn.common.pojo.jw.classes.app;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CheckInStuQueryVo {
	
	@ApiModelProperty(value = "学生id",dataType = "String")
	private String studentId;
	@ApiModelProperty(value = "查询的日期",dataType = "String")
	private String selDate;
	@ApiModelProperty(value = "考勤状态 1.异常 2.正常 3.全部 4.迟到 5.早退 6.缺卡",dataType = "String")
	private Integer type;

}
