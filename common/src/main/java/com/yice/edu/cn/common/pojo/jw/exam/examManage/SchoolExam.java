package com.yice.edu.cn.common.pojo.jw.exam.examManage;

import com.yice.edu.cn.common.pojo.jw.JwCourse.JwCourse;
import com.yice.edu.cn.common.pojo.jw.classes.JwClasses;
import com.yice.edu.cn.common.pojo.jw.schoolYear.CurSchoolYear;
import com.yice.edu.cn.common.pojo.jw.teacher.TeacherClasses;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import java.util.List;


@Data
@ApiModel("学校考试")
public class SchoolExam extends CurSchoolYear {

    @ApiModelProperty(value = "id",dataType = "String")
    private String id;
    @ApiModelProperty(value = "考试名称",dataType = "String")
    private String name;
    @ApiModelProperty(value = "学校id",dataType = "String")
    private String schoolId;
    @ApiModelProperty(value = "考试类型id,字典表type=23",dataType = "String")
    private String examTypeId;
    @ApiModelProperty(value = "考试类型名称,字典表type=23",dataType = "String")
    private String examTypeName;
    @ApiModelProperty(value = "参考班级,jw_classes对象列表",dataType = "List")
    private List<JwClasses> classes;
    @ApiModelProperty(value = "考试课程,里面包含了科目总分,jwCourse对象列表",dataType = "List")
    private List<JwCourse> courses;
    @ApiModelProperty(value = "考试时间",dataType = "String")
    private String examTime;
    @ApiModelProperty(value = "0线下考试,1网阅考试",dataType = "Integer")
    private Integer type;
    @ApiModelProperty(value = "试率对象,包含4个数组,excellent,good,fair,poor",dataType = "Object")
    private ExamRate examRate;
    @ApiModelProperty(value = "年级id",dataType = "String")
    private String gradeId;
    @ApiModelProperty(value = "年级名称",dataType = "String")
    private String gradeName;
    @ApiModelProperty(value = "创建时间",dataType = "String")
    private String createTime;
    @ApiModelProperty(value = "总人数",dataType = "String")
    private Integer totalNum;
    @ApiModelProperty(value = "搜索开始时间",dataType = "String")
    private String searchStartTime;
    @ApiModelProperty(value = "搜索结束时间",dataType = "String")
    private String searchEndTime;
    @ApiModelProperty(value = "时间标志：1、近一周 2、近一月 3、本学期 4、本学年",dataType = "String")
    private String timeMark;
    @ApiModelProperty("考试是否已结束,若结束，则不能修改，不能改成绩，不能出现在阅卷机客户端考试下拉框中(学情:已发布:true,未发布:false)")
    private Boolean finished;


    //分页排序等
    @ApiModelProperty(value = "老师已批阅完自己所负责的试卷份数与题目的名单",dataType = "Object")
    private List<String> reviewedTeacherIds;
    //额外字段
    @ApiModelProperty("参考学生信息")
    @Transient
    private List<ExamStudentInfo> examStudentInfos;
    @ApiModelProperty("是否自动生成考号")
    @Transient
    private Boolean autoGenExamNo;
    @Transient
    private List<TeacherClasses> TeacherClassesList;

}
