package com.yice.edu.cn.common.pojo.jw.exam.examManage;

import cn.afterturn.easypoi.excel.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ExamStudentInfo {
    @ApiModelProperty("学生id")
    private String id;
    @ApiModelProperty("学生名字")
    @Excel(name = "名字")
    private String name;
    @ApiModelProperty("头像地址")
    private String imgUrl;
    @ApiModelProperty("学号")
    @Excel(name = "学号")
    private String studentNo;
    @ApiModelProperty("班级id")
    private String classesId;
    @ApiModelProperty("入学年份")
    private String enrollYear;
    @ApiModelProperty("学校id")
    private String schoolId;
    @ApiModelProperty("考号")
    @Excel(name = "考号")
    private String examNo;
    @ApiModelProperty("班号")
    private Integer classesNumber;
    @ApiModelProperty("年级id，来自字典表")
    private String gradeId;
    @ApiModelProperty("年级名称")
    private String gradeName;
    @ApiModelProperty("座位号")
    @Excel(name = "座位号")
    private Integer seatNumber;
}
