package com.yice.edu.cn.common.pojo.jw.electiveCourse;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class GradeClassesPojo {
    @ApiModelProperty("年级id")
    private Integer gradeId;
    @ApiModelProperty("年级名称")
    private String gradeName;
    @ApiModelProperty("班级id")
    private String classesId;
    @ApiModelProperty("班级名称")
    private String classesName;
}
