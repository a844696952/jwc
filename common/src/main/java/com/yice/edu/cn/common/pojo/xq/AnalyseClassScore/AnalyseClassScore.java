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
@ApiModel("单个班级某个科目考试分析")
public class AnalyseClassScore{

    @ApiModelProperty(value = "id",dataType = "String")
    private String id;
    @ApiModelProperty(value = "考试对象",dataType = "SchoolExam")
    private SchoolExam examObj;
    @ApiModelProperty(value = "班级对象",dataType = "JwClasses")
    private JwClasses classObj;
    @ApiModelProperty(value = "课程对象",dataType = "JwCourse")
    private JwCourse courseObj;
    @ApiModelProperty(value = "实际考试人数",dataType = "Long")
    private Long actualNum;
    @ApiModelProperty(value = "缺考人数",dataType = "Long")
    private Long absenceNum;
    @ApiModelProperty(value = "班级最高分",dataType = "Double")
    private Double maxMarks;
    @ApiModelProperty(value = "班级最低分",dataType = "Double")
    private Double minMarks;
    @ApiModelProperty(value = "班级平均分",dataType = "Double")
    private Double avgMarks;
    @ApiModelProperty(value = "年段平均分",dataType = "Double")
    private Double gradeAvgMarks;
    @ApiModelProperty(value = "班级年段排名",dataType = "Integer")
    private Integer gradeSort;
    @ApiModelProperty(value = "优秀率对象",dataType = "ExamRateVo")
    private ExamRateVo excellentObj;
    @ApiModelProperty(value = "良好率对象",dataType = "ExamRateVo")
    private ExamRateVo goodObj;
    @ApiModelProperty(value = "及格率对象",dataType = "ExamRateVo")
    private ExamRateVo fairObj;
    @ApiModelProperty(value = "低分率对象",dataType = "ExamRateVo")
    private ExamRateVo poorObj;
    @ApiModelProperty(value = "创建时间",dataType = "String")
    private String createTime;
    //分页排序等
    @Transient
    private Pager pager;
    @Transient
    @ApiModelProperty(value = "某一个学生分数",dataType = "String")
    private String stuScore;
    @Transient
    @ApiModelProperty(value = "某一个学生的班级排名",dataType = "String")
    private String classSort;
    @Transient
    @ApiModelProperty(value = "是否缺考",dataType = "String")
    private Boolean missing;
}
