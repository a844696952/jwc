package com.yice.edu.cn.common.pojo.xq.analysisKnowledge;

import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.jw.JwCourse.JwCourse;
import com.yice.edu.cn.common.pojo.jw.exam.examManage.SchoolExam;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import com.yice.edu.cn.common.pojo.jy.knowledgePoint.KnowledgePoint;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;


@Data
@ApiModel("学生知识点分析表")
public class AnalyseStuKnowledge{

    @ApiModelProperty("主键")
    private String id;
    @ApiModelProperty("考试信息")
    private SchoolExam examination;
    @ApiModelProperty("知识点信息")
    private KnowledgePoint knowledge;
    @ApiModelProperty("学生信息")
    private Student student;
    @ApiModelProperty("课程信息")
    private JwCourse course;
    @ApiModelProperty("知识点总分")
    private Integer totalScore;
    @ApiModelProperty("知识点得分")
    private Double getScore;
    @ApiModelProperty("题号数组")
    private Integer[] topicArr;
    @ApiModelProperty("题目数量")
    private Integer topicCount;
    @ApiModelProperty("得分率")
    private Double getPersent;
    @ApiModelProperty("年级均分")
    private Double gradeAvgScore;
    @ApiModelProperty("年级得分率")
    private Double gradePersent;
    @ApiModelProperty("班级均分")
    private Double classAvgScore;
    @ApiModelProperty("班级得分率")
    private Double classPersent;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
}
