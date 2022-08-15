package com.yice.edu.cn.common.pojo.jw.teacher;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 该对象提供给学生评价获取学校班级及班主任
 */
@Data
public class TeacherClassesForStuEvaluate {
    @ApiModelProperty(value = "年级名称",dataType = "String")
    private String gradeName;
    @ApiModelProperty(value = "年级id",dataType = "String")
    private String gradeId;
    @ApiModelProperty(value = "班号",dataType = "String")
    private String classNumber;
    @ApiModelProperty(value = "班级id",dataType = "String")
    private String classId;
    @ApiModelProperty(value = "班主任名字",dataType = "String")
    private String teacherName;
    @ApiModelProperty(value = "教师id",dataType = "String")
    private String teacherId;
    @ApiModelProperty(value = "班级总人数",dataType = "Integer")
    private Integer classPeoNum;

}
