package com.yice.edu.cn.common.pojo.xq.AnalyseClassScore;

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

@Document
@Data
@ApiModel("学生考试题型分析")
public class AnalyseExamStuTopicType{

    @ApiModelProperty("id")
    private String id;
    @ApiModelProperty("考试对象")
    private SchoolExam examObj;
    @ApiModelProperty("课程对象")
    private JwCourse courseObj;
    @ApiModelProperty("学生对象")
    private Student student;
    @ApiModelProperty("班级对象")
    private JwClasses clazzObj;
    @ApiModelProperty("题型id")
    private String typeId;
    @ApiModelProperty("题型名称")
    private String topicTypeName;
    @ApiModelProperty("题型数组")
    private Integer[] topicArr;
    @ApiModelProperty("题型总分")
    private Double topicFullMarks;
    @ApiModelProperty("学生个人题型得分")
    private Double getTopicScore;
    @ApiModelProperty("学生得分率")
    private Double topicRate;
    @ApiModelProperty("班级平均分")
    private Double classAvgMarks;
    @ApiModelProperty("班级得分率")
    private Double classScoreRate;
    @ApiModelProperty("年级平均分")
    private Double gradeAvgMarks;
    @ApiModelProperty("年级得分率")
    private Double gradeScoreRate;
    @ApiModelProperty("创建时间")
    private String createTime;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
}
