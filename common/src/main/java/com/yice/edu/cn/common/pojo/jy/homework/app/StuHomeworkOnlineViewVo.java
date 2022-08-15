package com.yice.edu.cn.common.pojo.jy.homework.app;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 
 * 学生作业的情况
 */
@Data
public class StuHomeworkOnlineViewVo {
	/**
	 * 学生作业List对象
	 */
	@ApiModelProperty(value = "学生作业对象",dataType = "List")
    private List<StuHomeworkOnlineObjViewVo> stuHomeworkOnlineObjViewVo;
    
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
