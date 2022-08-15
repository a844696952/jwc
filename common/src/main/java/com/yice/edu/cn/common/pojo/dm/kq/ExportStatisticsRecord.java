package com.yice.edu.cn.common.pojo.dm.kq;

import cn.afterturn.easypoi.excel.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(description = "导出考勤统计信息")
public class ExportStatisticsRecord {
    @ApiModelProperty(value = "日期)", dataType = "String")
    @Excel(name = "日期", orderNum = "1",width = 30)
    private String recordDate;

    @ApiModelProperty(value = "年级", dataType = "String")
    @Excel(name = "年级", orderNum = "2")
    private String gradeString;

    @ApiModelProperty(value = "应到人数", dataType = "String")
    @Excel(name = "应到", orderNum = "3")
    private Integer ydNum;

    @ApiModelProperty(value = "实到人数", dataType = "String")
    @Excel(name = "实到", orderNum = "4")
    private Integer sdNum;

    @ApiModelProperty(value = "未到人数", dataType = "String")
    @Excel(name = "未到", orderNum = "5")
    private Integer wdNum;

    @ApiModelProperty(value = "迟到人数", dataType = "String")
    @Excel(name = "迟到", orderNum = "6")
    private Integer cdNum;

    @ApiModelProperty(value = "请假人数", dataType = "String")
    @Excel(name = "请假", orderNum = "7")
    private Integer qjNum;

    @ApiModelProperty(value = "考勤状态(0-默认状态 1-未到 2--迟到 3--已到 4--请假)", dataType = "String")
    private String kqStatus;

    @ApiModelProperty(value = "年级id")
    private String gradeId;

    @ApiModelProperty(value = "班级id", dataType = "String")
    private String classId;

    @ApiModelProperty(value = "学生考勤统计信息集合")
    private List<ProtectedStudent> students;


}
