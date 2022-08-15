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
*
*
*
*/
@Data
@Document
public class QusSurveySendObject{
    private String id;
    @ApiModelProperty(value = "问卷id",dataType = "String")
    @Indexed
    private String surveyId;
    @ApiModelProperty(value = "问卷标题",dataType = "String")
    private String surveyTitle;
    @ApiModelProperty(value = "老师集合",dataType = "list")
    private ArrayList<QusSurveyTeachers> teacherList;
    @ApiModelProperty("发布时间")
    private String createTime;
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
