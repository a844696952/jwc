package com.yice.edu.cn.common.pojo.jy.homework.app;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 
 * 作业查询
 */
@Data
public class CompleteHomeworkQueryVo {
	/**
	 * id
	 */
	@ApiModelProperty(value = "id",dataType = "String")
    private String id;
    
    /**
     * 类型 1.准时提交 2.未提交  3.已逾期 
     */
	@ApiModelProperty(value = "类型 1.准时提交 2.未提交  3.已逾期 ",dataType = "Integer")
    private Integer type;
	
	/**
	 * 回复类型 1.拍照 2.确认回复
	 */
	/*private Integer replyWay;*/
	
}
