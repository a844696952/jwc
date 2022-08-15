package com.yice.edu.cn.common.pojo.xq.AnalyseClassScore;

import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.jw.classes.JwClasses;
import com.yice.edu.cn.common.pojo.jw.exam.examManage.SchoolExam;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Document
@Data
@ApiModel("全部科目全年级考试分析")
public class AnalyseAllSubjectGradeScore{

    @ApiModelProperty(value = "id",dataType = "String")
    private String id;
    @ApiModelProperty(value = "考试对象",dataType = "SchoolExam")
    private SchoolExam examObj;
    @ApiModelProperty(value = "满分分数",dataType = "Double")
    private Double fullMarks;
    @ApiModelProperty(value = "最高分",dataType = "Double")
    private Double maxMarks;
    @ApiModelProperty(value = "最低分",dataType = "Double")
    private Double minMarks;
    @ApiModelProperty(value = "平均分",dataType = "Double")
    private Double avgMarks;
    @ApiModelProperty(value = "创建时间",dataType = "String")
    private String createTime;
    //分页排序等
    @Transient
    private Pager pager;
}
