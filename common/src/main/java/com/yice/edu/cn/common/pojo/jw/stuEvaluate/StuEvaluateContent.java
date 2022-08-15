package com.yice.edu.cn.common.pojo.jw.stuEvaluate;

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
*
*
*/
@Data
@Document
public class StuEvaluateContent{

    @ApiModelProperty(value = "id",dataType = "String")
    private String id;
    @ApiModelProperty(value = "标题",dataType = "String")
    private String evaluateTitle;
    @Indexed
    @ApiModelProperty(value = "评价ID",dataType = "String")
    private String evaluateId;
    @ApiModelProperty(value = "发送对象id",dataType = "String")
    private String sendObjectId;
    @ApiModelProperty(value = "截止时间",dataType = "String")
    private String endTime;
    @Indexed
    @ApiModelProperty(value = "班级id",dataType = "String")
    private String classId;
    @Indexed
    @ApiModelProperty(value = "学生Id",dataType = "String")
    private String studentId;
    @ApiModelProperty(value = "学生名称",dataType = "String")
    private String studentName;
    @ApiModelProperty(value = "头像",dataType = "String")
    private String img;
    @ApiModelProperty(value = "星星数量",dataType = "Integer")
    private Integer starNum;
    @ApiModelProperty(value = "分数",dataType = "String")
    private String score;
    @ApiModelProperty(value = "标签",dataType = "String")
    private ArrayList<String> label;
    @ApiModelProperty(value = "内容",dataType = "String")
    private String content;

    @ApiModelProperty(value = "评价状态：1、未评价 2、已评价",dataType = "String")
    private String evaluateState;
    @ApiModelProperty(value = "推送状态：1、未推送 2、已推送",dataType = "String")
    private String pushState;

    @ApiModelProperty(value = "评价日期",dataType = "String")
    private String createTime;
    @ApiModelProperty("schoolId")
    private String schoolId;

    @ApiModelProperty("当前学年的id")
    private String schoolYearId;
    @ApiModelProperty("学年")
    private String fromTo;
    @ApiModelProperty("0表示上学期,1下学期")
    private Integer term;


    //分页排序等
    @Transient
    @Valid
    private Pager pager;
}
