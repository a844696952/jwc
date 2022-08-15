package com.yice.edu.cn.common.pojo.xq.analysisStudentScore;

import com.yice.edu.cn.common.pojo.Pager;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@ApiModel(value = "考试学生学情查询vo")
@Data
public class StudentScoreListVo {

    @ApiModelProperty(value = "学生Id",dataType = "String")
    private String studentId;
    @ApiModelProperty(value = "科目Id",dataType = "String")
    private String subjectId;
    @ApiModelProperty(value = "年级Id",dataType = "String")
    private String gradeId;

    @ApiModelProperty(value = "考试名称",dataType = "String")
    private String examName; //考试名称
    @ApiModelProperty(value = "考试类型",dataType = "String")
    private String examType;  //考试类型
    @ApiModelProperty(value = "学期",dataType = "String")
    private String schoolYear; //学期
    @ApiModelProperty(value = "学期标识(0-上学期 1-下学期)",dataType = "String")
    private Integer termType; //上下学期标识
    @ApiModelProperty(value = "考试时间",dataType = "String")
    private String examTime; //考试时间
    @ApiModelProperty(value = "考试分数",dataType = "Double")
    private Double score;      //考试分数
    @ApiModelProperty(value = "班级平均分",dataType = "Double")
    private Double avgScore; //班级平均分
    @ApiModelProperty(value = "总分数",dataType = "Double")
    private Double totalScore;

    //分页排序等
    private Pager pager;
}
