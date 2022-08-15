package com.yice.edu.cn.common.pojo.xq.AnalyseClassScore;

import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.jw.JwCourse.JwCourse;
import com.yice.edu.cn.common.pojo.jw.classes.JwClasses;
import com.yice.edu.cn.common.pojo.jw.exam.examManage.SchoolExam;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Document
@Data
@ApiModel("考试小题分析")
public class AnalyseExamTopic{

    @ApiModelProperty(value = "id",dataType = "String")
    private String id;
    @ApiModelProperty(value = "考试id",dataType = "String")
    private String examId;
    @ApiModelProperty(value = "考试对象",dataType = "SchoolExam")
    private SchoolExam examObj;
    @ApiModelProperty(value = "课程对象",dataType = "Course")
    private JwCourse courseObj;
    @ApiModelProperty(value = "班级对象",dataType = "JwClasses")
    private JwClasses clazzObj;
    @ApiModelProperty(value = "题型名称",dataType = "String")
    private String topicTypeName;
    @ApiModelProperty(value = "题号",dataType = "Integer")
    private Integer topicNum;
    @ApiModelProperty(value = "题型id",dataType = "Integer")
    private Integer topicId;
    @ApiModelProperty(value = "满分分数",dataType = "Double")
    private Double fullMarks;
    @ApiModelProperty(value = "年级平均分",dataType = "Double")
    private Double gradeAvgMarks;
    @ApiModelProperty(value = "年级得分率",dataType = "Double")
    private Double gradeScoreRate;
    @ApiModelProperty(value = "班级平均分",dataType = "Double")
    private Double classAvgMarks;
    @ApiModelProperty(value = "班级得分率",dataType = "Double")
    private Double classScoreRate;
    @ApiModelProperty(value = "创建时间",dataType = "String")
    private String createTime;
    @ApiModelProperty(value = "学生得分率情况",dataType = "String")
    private Integer[] stuScoreRate;
    //分页排序等
    @Transient
    private Pager pager;
}
