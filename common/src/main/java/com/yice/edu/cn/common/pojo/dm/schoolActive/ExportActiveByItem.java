package com.yice.edu.cn.common.pojo.dm.schoolActive;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 根据项目导出
 */
@Data
public class ExportActiveByItem {

    @ApiModelProperty(value = "活动名称",dataType = "String")
    private String activityName;
    @ApiModelProperty(value = "结束日期",dataType = "String")
    private String endDate;
    @ApiModelProperty(value = "是否允许自定义",dataType = "String")
    private Integer isCustomize;
    @ApiModelProperty(value = "活动内容",dataType = "String")
    private String content;

    @ApiModelProperty(value = "班级项目信息")
    private List<ActivityItem> activityItemList;




}
