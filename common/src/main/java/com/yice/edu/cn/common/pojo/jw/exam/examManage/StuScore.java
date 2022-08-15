package com.yice.edu.cn.common.pojo.jw.exam.examManage;

import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.jw.JwCourse.JwCourse;
import com.yice.edu.cn.common.pojo.jw.classes.JwClasses;
import com.yice.edu.cn.common.pojo.jw.exam.buildPaper.answerSheet.AnswerSheetData;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import com.yice.edu.cn.common.pojo.validateClass.GroupSeven;
import com.yice.edu.cn.common.pojo.validateClass.GroupSix;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;


@Data
@ApiModel("学生成绩")
@Document
@CompoundIndexes({
   @CompoundIndex(name = "scoreUnique", def = "{schoolExamId : 1, 'student.id' : 1,'course.id':1}")
})
public class StuScore{

    @ApiModelProperty(value = "id",dataType = "String")
    private String id;
    @ApiModelProperty(value = "考试id",dataType = "String")
    @NotBlank(message = "考试id不能为空",groups = {GroupSix.class, GroupSeven.class})
    private String schoolExamId;
    @ApiModelProperty(value = "班级对象,需要班级id,班级名称,入学年份等",dataType = "Object")
    @Valid
    @NotNull(message = "班级对象必须传",groups = {GroupSix.class, GroupSeven.class})
    private JwClasses clazz;
    @ApiModelProperty(value = "学生对象,需要id,姓名,学籍号,头像",dataType = "Object")
    @Valid
    @NotNull(message = "学生对象必须传",groups = {GroupSix.class, GroupSeven.class})
    private Student student;
    @ApiModelProperty(value = "课程对象,需要课程id,课程别名,课程类型id（系统课程id）",dataType = "Object")
    @Valid
    @NotNull(message = "课程对象必须传",groups = {GroupSix.class, GroupSeven.class})
    private JwCourse course;
    @ApiModelProperty(value = "该课程成绩,缺考是成绩为-1",dataType = "Double")
    private Double score;
    @ApiModelProperty(value = "是否缺考",dataType = "Boolean")
    private Boolean missing;
    @ApiModelProperty("是否缺卷")
    private Boolean lostPaper;
    @ApiModelProperty(value = "创建时间",dataType = "String")
    private String createTime;

    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
    //额外字段
    @ApiModelProperty(value = "姓名学籍号",dataType = "String")
    private String nameOrCode;
    @ApiModelProperty(value = "最小分值",dataType = "String")
    private Double minVal;
    @ApiModelProperty(value = "最大分值",dataType = "String")
    private Double maxVal;
    @ApiModelProperty("学生答题记录列表,大题列表,网阅考试时,阅卷机上传的数据")
    @NotEmpty(message = "答题卡数据列表不能为空",groups = GroupSix.class)
    @Valid
    private List<AnswerSheetData> answerSheetDatas;
    @ApiModelProperty("该学生成绩是否已阅,即客户端阅完教师主观题也阅完，成绩也出来了")
    private Boolean reviewed;

    @ApiModelProperty(value = "学生试卷原图",dataType = "Object")
    private List<String> imgList;

    @ApiModelProperty(value = "学生试卷批阅后的图片",dataType = "Object")
    private List<String> newImgList;


    @ApiModelProperty(value = "考试列表")
    private SchoolExam schoolExams;
}
