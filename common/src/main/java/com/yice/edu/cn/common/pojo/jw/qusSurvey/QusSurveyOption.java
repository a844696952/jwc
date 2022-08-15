package com.yice.edu.cn.common.pojo.jw.qusSurvey;

import com.yice.edu.cn.common.pojo.Pager;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.Valid;

/**
*
*学生提交选项保存的记录的表
*
*/
@Data
@Document
public class QusSurveyOption{
    private String id;
    @ApiModelProperty(value = "问卷id",dataType = "String")
    @Indexed
    private String surveyId;
    @ApiModelProperty(value = "老师id",dataType = "String")
    @Indexed
    private String teacherId;
    @ApiModelProperty(value = "问题Id",dataType = "String")
    private String questionId;
    @ApiModelProperty(value = "学生id",dataType = "String")
    private String studentId;
    @ApiModelProperty(value = "选项id",dataType = "String")
    private String optionId;
    @ApiModelProperty(value = "选项分数",dataType = "String")
    private Integer optionScore;
    @ApiModelProperty(value = "选项次数",dataType = "String")
    private Integer optionCount;
    @ApiModelProperty(value = "被评老师id",dataType = "String")
    private String beEvaluatedTeacherId;
    @ApiModelProperty(value = "评审老师id",dataType = "String")
    private String evaluateTeacherId;
    @ApiModelProperty("发布时间")
    private String createTime;
    @ApiModelProperty("schoolId")
    private String schoolId;
    //分页排序等
    @Transient
    @Valid
    private Pager pager;
}
