package com.yice.edu.cn.common.pojo.xq.AnalyseClassScore;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Transient;

import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.jw.JwCourse.JwCourse;
import com.yice.edu.cn.common.pojo.jw.classes.JwClasses;
import com.yice.edu.cn.common.pojo.jw.exam.examManage.SchoolExam;
import com.yice.edu.cn.common.pojo.jw.student.Student;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
@ApiModel("学生个人的小题分析")
public class AnalyseExamTopicStu{

    @ApiModelProperty("id")
    private String id;
    @ApiModelProperty("考试id")
    private String examId;
    @ApiModelProperty("考试对象")
    private SchoolExam examObj;
    @ApiModelProperty("学生对象")
    private Student student;
    @ApiModelProperty("课程对象")
    private JwCourse courseObj;
    @ApiModelProperty("班级对象")
    private JwClasses clazzObj;
    @ApiModelProperty("题型id")
    private String topicId;
    @ApiModelProperty("小题类型名称")
    private String topicTypeName;
    @ApiModelProperty("小题序号")
    private Integer topicNum;
    @ApiModelProperty("小题满分分数")
    private Double fullMarks;
    @ApiModelProperty("班级均分")
    private Double classAvgMarks;
    @ApiModelProperty("学生个人得分")
    private Double score;
    @ApiModelProperty("学生得分率")
    private Double topicRate;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
    
    @Transient
    @ApiModelProperty("班级得分率")
    private Double clazzRate;
}
