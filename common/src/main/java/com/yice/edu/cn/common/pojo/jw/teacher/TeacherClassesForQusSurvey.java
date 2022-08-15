package com.yice.edu.cn.common.pojo.jw.teacher;

import com.yice.edu.cn.common.pojo.jw.classes.ClassesForQusSurvey;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.ArrayList;

/**
 * 该对象提供给问卷模块获取任课老师列表
 */
@Data
public class TeacherClassesForQusSurvey {
    @ApiModelProperty(value = "查询条件",dataType = "String")
    private String searchContent;
    @ApiModelProperty(value = "老师id",dataType = "String")
    private String teacherId;
    @ApiModelProperty(value = "学校id",dataType = "String")
    private String schoolId;
    @ApiModelProperty(value = "姓名",dataType = "String")
    private String name;
    @ApiModelProperty(value = "工号",dataType = "String")
    private String workNumber;
    @ApiModelProperty(value = "头像",dataType = "String")
    private String imgUrl;
    @ApiModelProperty(value = "所教班级（班号）",dataType = "String")
    private String classesId;
    @ApiModelProperty(value = "所教班级名称",dataType = "String")
    private String classesName;
    @ApiModelProperty(value = "班级应届年份")
    private String enrollYear;
    @ApiModelProperty(value = "所在年级",dataType = "String")
    private String gradeName;
    @ApiModelProperty(value = "所在年级id",dataType = "String")
    private String gradeId;
    @ApiModelProperty(value = "班级集合")
    private ArrayList<ClassesForQusSurvey> classList;
}
