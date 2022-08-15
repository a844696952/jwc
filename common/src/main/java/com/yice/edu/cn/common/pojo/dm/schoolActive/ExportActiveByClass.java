package com.yice.edu.cn.common.pojo.dm.schoolActive;

import cn.afterturn.easypoi.excel.annotation.ExcelCollection;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 导出活动根据班级导出
 */
@Data
@ExcelTarget("exportActiveByClass")
public class ExportActiveByClass {

    @ApiModelProperty(value = "活动名称",dataType = "String")
    private String activityName;
    @ApiModelProperty(value = "结束日期",dataType = "String")
    private String endDate;
    @ApiModelProperty(value = "活动内容",dataType = "String")
    private String content;
    @ApiModelProperty(value = "是否允许自定义",dataType = "String")
    private Integer isCustomize;
    @ExcelCollection(name = "班级活动集合")
    private List<ActivityItemClass> activityClassList;
}
