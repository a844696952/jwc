package com.yice.edu.cn.common.pojo.xq.AnalyseClassScore;


import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.jw.JwCourse.JwCourse;
import com.yice.edu.cn.common.pojo.jw.exam.examManage.SchoolExam;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Document
@Data
@ApiModel("单科全年级分析")
public class AnalyseSubjectGradeScore{

    @ApiModelProperty(value = "id",dataType = "String")
    private String id;
    @ApiModelProperty(value = "考试对象",dataType = "SchoolExam")
    private SchoolExam examObj;
    @ApiModelProperty(value = "课程对象",dataType = "JwCourse")
    private JwCourse courseObj;
    @ApiModelProperty(value = "优秀率对象",dataType = "ExamRateVo")
    private ExamRateVo excellentObj;
    @ApiModelProperty(value = "良好率对象",dataType = "ExamRateVo")
    private ExamRateVo goodObj;
    @ApiModelProperty(value = "及格率对象",dataType = "ExamRateVo")
    private ExamRateVo fairObj;
    @ApiModelProperty(value = "低分率对象",dataType = "ExamRateVo")
    private ExamRateVo poorObj;
    @ApiModelProperty(value = "实际考试人数",dataType = "Long")
    private Long actualNum;
    @ApiModelProperty(value = "缺考人数",dataType = "Long")
    private Long absenceNum;
    @ApiModelProperty(value = "创建时间",dataType = "String")
    private String createTime;
    //分页排序等
    @Transient
    private Pager pager;
}
