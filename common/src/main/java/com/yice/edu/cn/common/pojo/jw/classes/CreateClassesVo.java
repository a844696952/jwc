package com.yice.edu.cn.common.pojo.jw.classes;

import lombok.Data;

@Data
public class CreateClassesVo {

	/**
	 * 班级数量
	 */
	private Integer classesNum;
	
	/**
	 * 年级id
	 */
    private String gradeId;
    
    /**
     * 年级名称
     */
    private String gradeName;
    
    /**
     * 备注
     */
    private String note;
    
    /**
     * 学校id
     */
    private String schoolId;
    
    /**
     * 入学年份
     */
    private String enrollYear;

}
