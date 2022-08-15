package com.yice.edu.cn.common.pojo.jw.classes;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 该对象提供给问卷模块获取任课老师列表
 */
@Data
public class ClassesForQusSurvey {
    @ApiModelProperty(value = "所在年级",dataType = "String")
    private String gradeName;
    @ApiModelProperty(value = "所在年级id",dataType = "String")
    private String gradeId;
    @ApiModelProperty(value = "所教班级（班号）",dataType = "String")
    private String classesId;
    @ApiModelProperty(value = "所教班级名称",dataType = "String")
    private String classesName;
    @ApiModelProperty(value = "班级应届年份")
    private String enrollYear;
}
