package com.yice.edu.cn.common.pojo.dm.schoolActive;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelCollection;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class ActivityItemClass {

    private String schoolId;
    @ApiModelProperty("班级id")
    private String classesId;

    @ApiModelProperty(value = "班级名称",dataType = "String")
    @Excel(name = "班级",width = 20,needMerge = true)
    private String className;

    @ApiModelProperty(value = "活动信息集合")
    @ExcelCollection(name = "")
    private List<DmActivityItem> dmActivityItemList;

}
