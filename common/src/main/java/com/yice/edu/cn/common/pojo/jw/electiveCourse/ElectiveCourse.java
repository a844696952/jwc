package com.yice.edu.cn.common.pojo.jw.electiveCourse;

import com.yice.edu.cn.common.pojo.jw.schoolYear.CurSchoolYear;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import java.util.List;


@Data
@ApiModel("选修课表")
public class ElectiveCourse extends CurSchoolYear {

    @ApiModelProperty("id")
    private String id;
    @ApiModelProperty("名称")
    private String name;
    @ApiModelProperty("创建时间")
    private String createTime;
    @ApiModelProperty("更新时间")
    private String updateTime;

    @ApiModelProperty("上课时间（星期）")
    private String classTimeWeek;
    @ApiModelProperty("第几节")
    private String classTimeSection;
    @ApiModelProperty("课程开始时间")
    private String courseStartTime;
    @ApiModelProperty("课程结束时间")
    private String courseEndTime;
    @ApiModelProperty("指导老师id")
    private String teacherId;
    @ApiModelProperty("指导老师名称")
    private String teacherName;
    @ApiModelProperty("选修课最大人数")
    private Integer maxNum;
    @ApiModelProperty("最小开班人数")
    private Integer minNum;
    @ApiModelProperty("报名开始时间")
    private String signUpStartTime;
    @ApiModelProperty("报名结束时间")
    private String signUpEndTime;
    @ApiModelProperty("报名状态 1、未开始 2、人数不足 3、可开课 4、已报满 5、已开课 6、已结束 7、已关闭 8、已报名 ")
    private Integer signUpStatus;
    @ApiModelProperty("学分")
    private Integer score;
    @ApiModelProperty("上课地点")
    private String classPlace;
    @ApiModelProperty("介绍")
    private String introduce;
    @ApiModelProperty("备注")
    private String remarks;
    @ApiModelProperty("学校id")
    private String schoolId;
    @ApiModelProperty("结课状态:0未结课 1已结课")
    private Integer classEndStatus;
    @ApiModelProperty("关闭状态:0未关闭 1已关闭")
    private Integer closeStatus;
    @ApiModelProperty("关闭原因")
    private String closeReason;
    @ApiModelProperty("版本号")
    private Integer version;

    @ApiModelProperty("班级人数")
    private Integer classesPeopleNum;
    @Transient
    @ApiModelProperty("班级id集合")
    private List<String> classIdList;

    @Transient
    @ApiModelProperty("年级id")
    private Integer gradeId;
    @Transient
    @ApiModelProperty("年级名称")
    private String gradeName;
    @Transient
    @ApiModelProperty("班级名称")
    private Integer classesNumber;
    @Transient
    @ApiModelProperty("班级id")
    private String classesId;
    @Transient
    @ApiModelProperty("学生id")
    private String studentId;
    @ApiModelProperty("已选的班级")
    private List<GradeClassesPojo> checkList;
    @Transient
    private String electiveId;


    @ApiModelProperty("bmp使用，1、选修课列表 2、我的选修课")
    @Transient
    private String listType;
}
