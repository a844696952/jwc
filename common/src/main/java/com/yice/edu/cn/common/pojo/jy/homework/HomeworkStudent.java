package com.yice.edu.cn.common.pojo.jy.homework;

import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.jw.schoolYear.CurSchoolYear;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import com.yice.edu.cn.common.pojo.validateClass.GroupOne;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
*
*学生作业状态表
*
*/
@Data
@Document
public class HomeworkStudent extends CurSchoolYear {

    private String id;
    /*private String sqId;*/
    @ApiModelProperty(value = "家庭作业对象",dataType = "Homework")
    private Homework homeworkObj;//家庭作业对象
    @Indexed
    @ApiModelProperty(value = "学生id",dataType = "String")
    private String studentId;//学生id
    @ApiModelProperty(value = "学生姓名",dataType = "String")
    private String studentName;//学生姓名
    @ApiModelProperty(value = "学生详细信息",dataType = "String")
    private Student student;
    @ApiModelProperty(value = "年级id",dataType = "String")
    private String gradeId;//年级id
    @ApiModelProperty(value = "年级名称",dataType = "String")
    private String gradeName;//年级名称
    @ApiModelProperty(value = "课程id",dataType = "String")
    private String subjectId;//课程id
    @ApiModelProperty(value = "课程名称",dataType = "String")
    private String subjectName;//课程名称
    @ApiModelProperty(value = "班级id",dataType = "String")
    private String classesId;//班级id
    @ApiModelProperty(value = "班级名称",dataType = "String")
    private String classesName;//班级名称
    @ApiModelProperty(value = "班级应届年份",dataType = "String")
    private String enrollYear;//班级应届年份
    @ApiModelProperty(value = "作业提交时间",dataType = "String")
    private String completeTime;//作业提交时间
    @ApiModelProperty(value = "点评时间(线下作业)",dataType = "String")
    private String remarkTime;//点评时间(线下作业)
    @ApiModelProperty(value = "点评内容(线下作业)",dataType = "String")
    private String remarkNote;//点评内容(线下作业)
    @ApiModelProperty(value = "提交作业图片",dataType = "String[]")
    private String[] homeworkImgArr;//提交作业图片
    @ApiModelProperty(value = "点评状态(1.已点评 2.未点评)",dataType = "int")
    private Integer remarkStatus;//点评状态(1.已点评 2.未点评)
    @ApiModelProperty(value = "状态(1.已提交 2.未提交 3.已提交逾期)",dataType = "int")
    private Integer status;//状态(1.已提交 2.未提交 3.已提交逾期)
    private String createTime;//创建时间
    /*//分页排序等
    @Transient
    @NotNull(groups = {GroupOne.class})
    private Pager pager;*/

    @Transient
    private Double wrongTopic; //学生错题率

    private List<HomeworkStudentAnnexVo> homeworkStudentAnnexList;//附件
    private List<HomeworkStudentAudioVo> homeworkStudentAudioVoList;//音频
    private String content;//内容

}
