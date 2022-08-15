package com.yice.edu.cn.common.pojo.jw.qusSurvey;

import com.yice.edu.cn.common.pojo.Pager;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.Valid;
import java.util.ArrayList;

/**
*
*问卷对象
*
*/
@Data
@Document
public class QusSurvey{
    @Indexed
    @ApiModelProperty(value = "问卷id",dataType = "String")
    private String id;
    @ApiModelProperty(value = "状态：1、未发布  2、已发布 ",dataType = "String")
    private String status;
    @ApiModelProperty(value = "问卷标题",dataType = "String")
    private String title;
    @ApiModelProperty(value = "问卷类型：1、教师评价 2、日常评价 4、教师互评",dataType = "String")
    private String type;
    @ApiModelProperty(value = "编辑状态:1、填写问卷基本信息 2、选择问卷题目 3、选择发送对象",dataType = "String")
    private String editState;
    @ApiModelProperty(value = "问卷简介",dataType = "String")
    private String introduction;
    @ApiModelProperty(value = "问卷截止时间",dataType = "String")
    private String endTime;
    @ApiModelProperty(value = "创建时间",dataType = "String")
    private String createTime;
    @ApiModelProperty(value = "开始时间",dataType = "String")
    @Transient
    private String searchStartTime;
    @ApiModelProperty(value = "结束时间",dataType = "String")
    @Transient
    private String searchEndTime;
    @ApiModelProperty(value = "问题集")
    private ArrayList<QusSurveyQuestion> qusSurveyQuestions;
    @ApiModelProperty(value = "显示统计按钮状态:1、不显示 2、显示",dataType = "String")
    private String showSumState;
    @ApiModelProperty(value = "学校id",dataType = "String")
    private String schoolId;
    @ApiModelProperty(value = "问卷总人数",dataType = "String")
    private Integer qusTotalNum;

    @ApiModelProperty(value = "问答题答案",dataType = "String")
    private String questionAnswer;
    //分页排序等
    @Transient
    @Valid
    private Pager pager;

    //1、进行中 2、已截止
    @Transient
    private String state;
}
