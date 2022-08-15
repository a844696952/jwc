package com.yice.edu.cn.common.pojo.jw.qusSurvey;


import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.Valid;
import java.util.ArrayList;

/**
 * 确认下发保存的对象
 */

@Data
@Document
public class SendObjectQusSurvey {
    private String id;
    @ApiModelProperty(value = "问卷id",dataType = "String")
    @Indexed
    private String surveyId;
    @ApiModelProperty(value = "问卷标题",dataType = "String")
    private String surveyTitle;
    @ApiModelProperty(value = "问卷简介",dataType = "String")
    private String introduction;
    @ApiModelProperty(value = "问卷截止时间",dataType = "String")
    private String endTime;
    @ApiModelProperty(value = "老师集合",dataType = "list")
    private ArrayList<QusSurveyTeachers> teacherList;
    @ApiModelProperty(value = "问卷截止状态:1、未截止 2、已截止",dataType = "String")
    private String endState;
    @ApiModelProperty(value = "创建时间",dataType = "String")
    private String createTime;
    @ApiModelProperty(value = "教师app统计结果")
    QusSurveyTeacherSendClass qusSurveyTeacherSendClass;
    @ApiModelProperty(value = "推送状态：1、未推送 2、已推送",dataType = "String")
    private String pushState;


    @ApiModelProperty(value = "老师id",dataType = "String")
    private String teacherId;
    @ApiModelProperty("schoolId")
    private String schoolId;

    @ApiModelProperty(value = "被评老师集合",dataType = "list")
    private ArrayList<Teacher> beEvaluatedTeacherList;
    @ApiModelProperty(value = "评审老师集合",dataType = "list")
    private ArrayList<Teacher> evaluateTeacherList;

    //分页排序等
    @Transient
    @Valid
    private Pager pager;
}
