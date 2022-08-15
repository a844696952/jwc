package com.yice.edu.cn.common.pojo.xq.analysisKnowledge;

import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.jw.JwCourse.JwCourse;
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
@ApiModel("考试年级知识点分析")
@Document
public class AnalyseGradeKnowledge{

    @ApiModelProperty("主键")
    private String id;
    @ApiModelProperty("考试信息")
    private SchoolExam examination;
    @ApiModelProperty("知识点信息")
    private KnowledgePoint knowledge;
    @ApiModelProperty("课程信息")
    private JwCourse course;
    @ApiModelProperty("年级知识点得分率")
    private Double getPersent;
    @ApiModelProperty("年级均分")
    private Double avgScore;
    @ApiModelProperty("题号数组")
    private Integer[] topicArr;
    @ApiModelProperty("题目数量")
    private Integer topicCount;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
}
