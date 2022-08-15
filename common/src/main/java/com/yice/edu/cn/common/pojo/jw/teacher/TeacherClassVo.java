package com.yice.edu.cn.common.pojo.jw.teacher;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class TeacherClassVo {
    @ApiModelProperty(value = "年级id",dataType = "String")
    @NotNull
    private String gradeId;
    @ApiModelProperty(value = "课程id",dataType = "String")
    @NotNull
    private String courseId;
    @ApiModelProperty(value = "教师id-教师端不用传该值",dataType = "String")
    private String teacherId;
}
