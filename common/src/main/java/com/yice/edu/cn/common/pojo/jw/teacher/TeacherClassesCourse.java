package com.yice.edu.cn.common.pojo.jw.teacher;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
/**
*
*教师在班级教学的课程信息
*
*/
@Data
public class TeacherClassesCourse{
    private String courseId;
    @ApiModelProperty(value = "课程名称",dataType = "String")
    private String courseName;
    @ApiModelProperty(value = "系统课程名称",dataType = "String")
    private String subjectName;
}
