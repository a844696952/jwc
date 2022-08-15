package com.yice.edu.cn.common.pojo.xq.analysisKnowledge;

import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.jw.JwCourse.JwCourse;
import com.yice.edu.cn.common.pojo.jw.classes.JwClasses;
import com.yice.edu.cn.common.pojo.jw.exam.examManage.SchoolExam;
import com.yice.edu.cn.common.pojo.jy.knowledgePoint.KnowledgePoint;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@ApiModel("考试班级知识点分析")
@Document
public class AnalyseClassKnowledge{

    @ApiModelProperty("主键")
    private String id;
    @ApiModelProperty("考试信息")
    private SchoolExam examination;
    @ApiModelProperty("班级信息")
    private JwClasses clazz;
    @ApiModelProperty("知识点信息")
    private KnowledgePoint knowledge;
    @ApiModelProperty("科目信息")
    private JwCourse course;
    @ApiModelProperty("知识点总分")
    private Integer totalScore;
    @ApiModelProperty("知识点得分率")
    private Double getPersent;
    @ApiModelProperty("题号数组")
    private Integer[] topicArr;
    @ApiModelProperty("题目数量")
    private Integer topicCount;
    @ApiModelProperty("知识点班级均分")
    private Double avgScore;
    @ApiModelProperty("年级均分")
    private Double gradeAvgScore;
    @ApiModelProperty("年级得分率")
    private Double gradePersent;

    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
}
