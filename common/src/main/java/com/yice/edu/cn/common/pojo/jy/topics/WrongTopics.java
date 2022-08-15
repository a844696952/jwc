package com.yice.edu.cn.common.pojo.jy.topics;

import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.jw.schoolYear.CurSchoolYear;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
*
*学生错题记录表
*
*/
@Data
@Document
public class WrongTopics extends CurSchoolYear {
    private String id;
    @ApiModelProperty(value = "题目对象",dataType = "Topics")
    private Topics topicsObj;//题目对象
    @ApiModelProperty(value = "渠道类型(1.作业)",dataType = "int")
    private Integer channelType;//渠道类型(1.作业)
    @ApiModelProperty(value = "来源Id",dataType = "String")
    private String channelId;//来源Id
    @Indexed
    @ApiModelProperty(value = "学生id",dataType = "String")
    private String studentId;//学生id
    @ApiModelProperty(value = "学生名称",dataType = "String")
    private String studentName;//学生名称
    private Student student;//学生详细对象
    @ApiModelProperty(value = "学生答案",dataType = "String")
    private String answer;//学生答案
    private String answerTime;//答题时间
    @ApiModelProperty(value = "年级id",dataType = "String")
    private String gradeId;//年级id
    @ApiModelProperty(value = "年级名称",dataType = "String")
    private String gradeName;//年级名称
    @ApiModelProperty(value = "课程id",dataType = "String")
    private String subjectId;//课程id
    @ApiModelProperty(value = "课程名称",dataType = "String")
    private String subjectName;//课程名称
    private String classesId;//布置的班级对象
    private String classesName;//布置的班级对象
    private String enrollYear;//布置的班级对象
    private String del;//删除标识
    /*//分页排序等
    @Transient
    @NotNull
    private Pager pager;*/

    private Double  knowledgenum;  //知识点数量
    private String knowledgeName;//知识点名字

    private Double contentNum;  //这道题目数量
    private String contentName;//这道题目题目
    private List<TopicsCount> topicsCounts;//题目作答统计数组(线上作业使用)
    private String[] typeIdNum;//题目类型数组
    private Double classRightRate;
}
