package com.yice.edu.cn.common.pojo.jy.topics.app;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class WrongTopicsVo {
    @ApiModelProperty(value = "科目名称",dataType = "String")
    private String subjectId;
    @ApiModelProperty(value = "题目号码（第几道题）",dataType = "studentId")
    private Integer topicsNum;
    @ApiModelProperty(value = "学生id",dataType = "studentId")
    private String studentId;
    @ApiModelProperty(value = "班级id",dataType = "studentId")
    private String classesId;
    @ApiModelProperty(value = "学校id",dataType = "studentId")
    private String schoolId;
}
