package com.yice.edu.cn.common.pojo.jw.qusSurvey;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;

@Data
public class QusSurveyTeachers {
    @ApiModelProperty(value = "教师id",dataType = "String")
    private String teacherId;
    @ApiModelProperty(value = "教师姓名",dataType = "String")
    private String name;
    @ApiModelProperty(value = "教师工号",dataType = "String")
    private String workNumber;
    @ApiModelProperty(value = "老师头像",dataType = "String")
    private String imgUrl;
    @ApiModelProperty(value = "所有班级集")
    private ArrayList<QusSurveyClass> classList;
    @ApiModelProperty(value = "已选中班级集")
    private ArrayList<String> alreadyQusSurveyClasses;
    @ApiModelProperty(value = "已选中班级集")
    private ArrayList<QusSurveyClass> alreadyQusSurveyClassList;
}
