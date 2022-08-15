package com.yice.edu.cn.common.pojo.jw.qusSurvey;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 问答题pojo
 */
@Data
public class QuestionAnswer {
    @ApiModelProperty(value = "班级id",dataType = "String")
    private String classId;
    @ApiModelProperty(value = "问卷id",dataType = "String")
    private String surveyId;
    @ApiModelProperty(value = "老师id",dataType = "String")
    private String teacherId;
    @ApiModelProperty(value = "班级总人数",dataType = "String")
    private String classStuNum;
    @ApiModelProperty(value = "问答题题目",dataType = "String")
    private String question;
    @ApiModelProperty(value = "问答题题目答案",dataType = "String")
    private List<String> questionAnswer;

}
