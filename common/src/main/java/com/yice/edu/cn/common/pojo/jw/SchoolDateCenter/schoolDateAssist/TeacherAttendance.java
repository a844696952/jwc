package com.yice.edu.cn.common.pojo.jw.SchoolDateCenter.schoolDateAssist;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.HashMap;
import java.util.List;

@Api("教师考勤率")
@Data
public class TeacherAttendance {
    @ApiModelProperty("教师考勤率")
    private Double teacherAttendance;
    @ApiModelProperty("本周请假次数")
    private Integer weekLeaveNumber;
    @ApiModelProperty("周同比")
    private Double weekComparedToTheSame;
    @ApiModelProperty("周同比是否增长 0否1是")
    private Boolean weekComparedIsRise;
    @ApiModelProperty("月同比")
    private Double monthComparedToTheSame;
    @ApiModelProperty("月同比是否增长 0否1是")
    private Boolean monthComparedIsRise;
    @ApiModelProperty("初始值")
    private Double initNumber;
    @ApiModelProperty("分布图数据List")
    private List<HashMap<String,Double>> teacherWeekAttList;
    @ApiModelProperty("学校Id")
    private String schooleId;

    @ApiModelProperty("考勤总天数")
    private Long kqTotal;
    @ApiModelProperty("实际出勤天数")
    private Long kqRealityTotal;
    @ApiModelProperty("本月出勤天数")
    private Long kqThisMonthTotal;
    @ApiModelProperty("上月出勤天数")
    private Long kqUpMonthTotal;
    @ApiModelProperty("本周请假天数")
    private Long thisWeekTotal;
    @ApiModelProperty("上周请假天数")
    private Long upWeekTotal;
    @ApiModelProperty("教师分布图数据")
    private List<Double> weekTotal;

    @ApiModelProperty("今日打卡初始值")
    private Long teacherThisKq;
    @ApiModelProperty("今日打卡波动值")
    private Long floatValue;
    @ApiModelProperty("教师考勤今日打卡率")
    private Double teacherTheClockRate;



}
