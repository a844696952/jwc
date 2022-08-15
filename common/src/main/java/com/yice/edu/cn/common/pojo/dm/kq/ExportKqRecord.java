package com.yice.edu.cn.common.pojo.dm.kq;

import cn.afterturn.easypoi.excel.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "导出异常考勤信息")
public class ExportKqRecord {
    @ApiModelProperty(value = "日期)", dataType = "String")
    @Excel(name = "日期",width = 40)
    private String recordDate;

    @ApiModelProperty(value = "考勤状态(0-默认状态 1-未到 2--迟到 3--已到 4--请假)", dataType = "String")
    @Excel(name = "考勤状态",width = 40)
    private String kqStatus;

    @ApiModelProperty(value = "班级", dataType = "String")
    @Excel(name = "班级",width = 40)
    private String classAndStudents;

    @ApiModelProperty(value = "学生姓名", dataType = "String")
    @Excel(name = "学生姓名",width = 40)
    private String name;

    @ApiModelProperty(value = "年级id", dataType = "String")
    private String gradeId;

    @ApiModelProperty(value = "班级id", dataType = "String")
    private String classId;

    @ApiModelProperty(value = "开启状态 0-默认状态 1-未到 2--迟到 3--已到 4--请假")
    private Integer kqState;

    @ApiModelProperty(value = "学生id", dataType = "String")
    private String studentId;
}
