package com.yice.edu.cn.common.pojo.jy.homework.app;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 
 * 点评作业
 */
@Data
public class RemarkOfflineHomeworkVo {
	/**
	 * 学生作业id
	 */
	@ApiModelProperty(value = "学生作业id",dataType = "String")
    private String homewordStudentId;
    /**
     * 点评内容
     */
	@ApiModelProperty(value = "点评内容",dataType = "String")
    private String remarkNote;
}
