package com.yice.edu.cn.common.pojo.dm.modeManage;


import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelCollection;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 考试信息（导出excel表格）
 */
@Data
@ExcelTarget("examInfo")
public class ExamInfo implements Serializable {

    @ApiModelProperty(value = "班级ID",dataType = "String")
    private String classId;

    @Excel(name = "展示班牌",needMerge = true,width = 25)
    @ApiModelProperty(value = "班级名称",dataType = "String")
    private String className;

    @Excel(name = "考场号（不输入则不显示）",needMerge = true,width = 25)
    @ApiModelProperty(value = "考场号",dataType = "String")
    private String examSiteNumber;

    @ApiModelProperty(value = "对应设备ID",dataType = "String")
    private String deviceId;

    @ExcelCollection(name="考试信息")
    @ApiModelProperty(value = "考试信息集合")
    private List<ExamSubject> subjectList;

    @ApiModelProperty(value = "考试学生集合")
    private List<String> examStudentList;

    @ApiModelProperty(value = "考试学生座位号集合")
    private List<String> examStudentSeatNumberList;

    @Excel(name = "考试学生",needMerge = true,width = 40)
    @ApiModelProperty(value = "考试学生集合")
    private String examStudentStr;


}
