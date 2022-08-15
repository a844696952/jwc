package com.yice.edu.cn.common.pojo.dm.schoolActive;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ExcelTarget("activityClass")
public class ActivityClass {

    private String schoolId;
    @ApiModelProperty("班级id")
    private String classesId;

    @ApiModelProperty(value = "班级名称",dataType = "String")
    @Excel(name = "班级",width = 20,needMerge = true,orderNum ="1")
    private String className;

    @ApiModelProperty("学生报名名单")
    @Excel(name = "人员名单",width = 40,needMerge = true,orderNum = "3")
    private String studentNames;

    @ApiModelProperty("学生报名人数")
    @Excel(name = "人数",width = 20,needMerge = true,orderNum = "2")
    private Integer studentNamesCount;

}
