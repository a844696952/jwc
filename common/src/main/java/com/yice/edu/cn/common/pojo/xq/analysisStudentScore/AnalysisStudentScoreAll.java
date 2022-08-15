package com.yice.edu.cn.common.pojo.xq.analysisStudentScore;

import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.jw.JwCourse.JwCourse;
import com.yice.edu.cn.common.pojo.jw.classes.JwClasses;
import com.yice.edu.cn.common.pojo.jw.exam.examManage.SchoolExam;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@ApiModel("学生考试成绩-总成绩")
@Document
public class AnalysisStudentScoreAll{

    @ApiModelProperty(value = "主键",dataType = "String")
    private String id;
    @ApiModelProperty(value = "考试信息",dataType = "String")
    private SchoolExam examination;
    @ApiModelProperty(value = "学生信息",dataType = "JwCourse")
    private Student student;
    @ApiModelProperty(value = "班级信息",dataType = "JwClasses")
    private JwClasses classObj;
    @ApiModelProperty(value = "总分",dataType = "Integer")
    private Integer total;
    @ApiModelProperty(value = "得分（缺考-1分）",dataType = "Double")
    private Double score;
    @ApiModelProperty(value = "班级排名",dataType = "Integer")
    private Integer classRanking;
    @ApiModelProperty(value = "总排名（年段排名）",dataType = "Integer")
    private Integer totalRanking;
    @ApiModelProperty(value = "学校id",dataType = "String")
    private String schoolId;
    @ApiModelProperty(value = "学校名称",dataType = "String")
    private String schoolName;
    @ApiModelProperty(value = "班级中最高分",dataType = "Double")
    private Double maxScore;
    @ApiModelProperty(value = "班级中最低分",dataType = "Double")
    private Double minScore;
    @ApiModelProperty(value = "班级中平均分",dataType = "Double")
    private Double avgScore;
    @ApiModelProperty(value = "是否全缺考",dataType = "Double")
    private Boolean missing;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
}
