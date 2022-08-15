package com.yice.edu.cn.common.pojo.dm.modeManage;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 科目信息
 */
@Data
@ExcelTarget("examSubject")
public class ExamSubject implements Serializable {
    @Excel(name = "科目",width = 25)
    private String subjectName;

    @Excel(name = "考试开始时间",width = 25)
    private String examBeginTime;

    @Excel(name = "考试结束时间",width = 25)
    private String examEndTime;

    @ApiModelProperty(value = "监考老师集合")
    private List<String> invigilatorList;

    @Excel(name = "监考老师",width = 25)
    private String invigilatorStr;

    @ApiModelProperty(value = "考试时间数组，提供前端服务")
    private List<String> time;


}
