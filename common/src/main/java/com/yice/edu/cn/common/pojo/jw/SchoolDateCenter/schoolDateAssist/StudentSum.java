package com.yice.edu.cn.common.pojo.jw.SchoolDateCenter.schoolDateAssist;

import com.yice.edu.cn.common.pojo.jw.student.Student;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Api("学生总数")
@Data
public class StudentSum {
    @ApiModelProperty("今年学生总数")
    private Long studentNumber;
    @ApiModelProperty("去年百分比")
    private String percentageIncrease;
    @ApiModelProperty("前年百分比")
    private String percentageDecline;
    @ApiModelProperty("去年毕业人数")
    private Long lastYearNumber;
    @ApiModelProperty("前年学生总数")
    private Long yearBeforeLast;
    @ApiModelProperty("去年学生总数")
    private Long lastYearStudentNumber;
    @ApiModelProperty("女生人数总数")
    private Long girlNumber;
    @ApiModelProperty("男生人数总数")
    private Long boyNumber;
    @ApiModelProperty("学生年级列表")
    private List<Map<String,Object>> studentList;

    @ApiModelProperty("相对于去年，前年是增长还是减少，true是上升,false是下降,第一个是相对于去年，第二个是前年")
    private Boolean[] flags;

    @ApiModelProperty("男生占比")
    private String rateOfMale;
    @ApiModelProperty("女生占比")
    private String rateOfFemale;


}
