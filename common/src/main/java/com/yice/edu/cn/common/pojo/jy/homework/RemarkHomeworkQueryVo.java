package com.yice.edu.cn.common.pojo.jy.homework;

import lombok.Data;

/**
 * 
 * 查询要点评作业的情况
 */
@Data
public class RemarkHomeworkQueryVo {
	/**
	 * 作业id
	 */
    private String id;
    
    /**
     * 类型 1.未点评 2.已点评 3.未提交 4.已提交
     */
    private Integer type;
}
