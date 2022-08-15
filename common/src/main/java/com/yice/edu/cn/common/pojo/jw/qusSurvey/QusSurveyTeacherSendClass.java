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
*班级统计页面缓存数据的对象
*
*/
@Data
@Document
public class QusSurveyTeacherSendClass {
    @ApiModelProperty(value = "id",dataType = "String")
    private String id;
    @ApiModelProperty(value = "文档id",dataType = "String")
    @Indexed
    private String surveyId;
    @ApiModelProperty(value = "老师id",dataType = "String")
    @Indexed
    private String teacherId;
    @ApiModelProperty(value = "老师姓名",dataType = "String")
    private String teacherName;
    @ApiModelProperty(value = "老师头像",dataType = "String")
    @Indexed
    private String img;
    @ApiModelProperty(value = "班级id",dataType = "String")
    @Indexed
    private String classId;
    @ApiModelProperty(value = "班级名称",dataType = "String")
    private String className;
    @ApiModelProperty(value = "年级名称",dataType = "String")
    private String gradeName;
    @ApiModelProperty(value = "提交人数",dataType = "String")
    private Integer submitNum;
    @ApiModelProperty(value = "班级总人数",dataType = "String")
    private Integer classPeopleNum;
    @ApiModelProperty(value = "平均分",dataType = "String")
    private Double avg;
    @ApiModelProperty(value = "最高分",dataType = "String")
    private Integer highScore;
    @ApiModelProperty(value = "最低分",dataType = "String")
    private Integer lowScore;
    @ApiModelProperty(value = "整体平均分",dataType = "String")
    private Double topAvg;
    @ApiModelProperty(value = "整体最高分",dataType = "String")
    private Integer topHighScore;
    @ApiModelProperty(value = "整体最低分",dataType = "String")
    private Integer topLowScore;
    @ApiModelProperty(value = "总体提交人数",dataType = "String")
    private Integer topTotalSubmit;

    /**
     * app使用字段
     */
    @ApiModelProperty(value = "学生id",dataType = "String")
    private String studentId;
    @ApiModelProperty(value = "评价状态：1、未评价 2、已评价",dataType = "String")
    private String state;
    @ApiModelProperty("发布时间")
    private String createTime;
    @ApiModelProperty("schoolId")
    private String schoolId;

    //分页排序等
    @Transient
    @Valid
    private Pager pager;
}
