package com.yice.edu.cn.common.pojo.dm.schoolActive;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelCollection;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ExcelTarget("activityItem")
public class ActivityItem {

    @Excel(name = "节目/项目",width = 20,needMerge = true)
    @ApiModelProperty("项目名称")
    private String projectName;

    @ApiModelProperty("项目ID")
    private String itemId;

    @ApiModelProperty("班级集合")
    @ExcelCollection(name = "")
    private List<ActivityClass> activityClassList;

}
