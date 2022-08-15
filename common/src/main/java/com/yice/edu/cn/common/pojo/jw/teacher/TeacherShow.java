package com.yice.edu.cn.common.pojo.jw.teacher;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TeacherShow extends Teacher {
    @ApiModelProperty(value = "教师授课信息")
    private String teaching;
}
