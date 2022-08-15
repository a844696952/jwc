package com.yice.edu.cn.common.pojo.jw.SchoolDateCenter.schoolDateAssist;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Api("学生考勤")
@Data
public class StudentThisKq {
    @ApiModelProperty("学生考勤")
    private Long studentKq;
    @ApiModelProperty("学生考勤浮动值")
    private Long floatValue;
    @ApiModelProperty("学生考勤比例")
    private String studentKqRatio;
}
