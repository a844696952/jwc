package com.yice.edu.cn.common.pojo.xw.kq;

import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.jw.schoolYear.CurSchoolYear;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import javax.validation.constraints.NotNull;
import javax.validation.Valid;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;

import java.util.List;


@Data
@ApiModel("考勤日统计表")
public class KqDailyStatistical extends CurSchoolYear {

    @ApiModelProperty("id")
    private String id;
    @ApiModelProperty("学生id")
    private String studentId;
    @ApiModelProperty("考勤日期")
    private String kqDate;
    @ApiModelProperty("考勤记录的时间及状态对象（根据考勤规则:2次或4次）")
    private KqPunchRules ruleStatus;
    @ApiModelProperty("考勤次数标识符（1一天一组，2一天两组）")
    private String punchNumber;
    @ApiModelProperty("创建时间")
    private String createTime;
    @ApiModelProperty("更新时间")
    private String updateTime;
    @ApiModelProperty("学生姓名")
    private String name;
    @ApiModelProperty("班级id")
    private String classId;
    @ApiModelProperty("班号")
    private String classesNumber;
    @ApiModelProperty("年级id")
    private String gradeId;
    @ApiModelProperty("年级名称")
    private String gradeName;
    @ApiModelProperty("学校id")
    private String schoolId;
    @ApiModelProperty("周几")
    private String weekDay;
    @ApiModelProperty("座号")
    private Integer seatNumber;
    @ApiModelProperty("迟到次数 0是无 1是有")
    private String lateStatus;
    @ApiModelProperty("早退次数  0是无 1是有")
    private String leaveEarlyStatus;
    @ApiModelProperty("缺卡次数  0是无 1是有")
    private String missStatus;
    @ApiModelProperty("当天规则")
    private KqPunchRules todayRules;

    private List<Student> studentList;//月统计用
}
