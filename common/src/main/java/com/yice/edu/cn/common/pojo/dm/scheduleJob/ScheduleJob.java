package com.yice.edu.cn.common.pojo.dm.scheduleJob;

import com.yice.edu.cn.common.pojo.Pager;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
public class ScheduleJob {

    @ApiModelProperty(value = "主键",dataType = "String")
    private String id;
    @ApiModelProperty(value = "定时任务名称",dataType = "String")
    private String jobName;
    @ApiModelProperty(value = "创建时间",dataType = "String")
    private String creTime;
    @ApiModelProperty(value = "任务cron表达式",dataType = "String")
    private String jobCron;
    @ApiModelProperty(value = "任务时间",dataType = "String")
    private String jobTime;
    @ApiModelProperty(value = "任务组",dataType = "String")
    private String jobGroup;
    @ApiModelProperty(value = "任务描述",dataType = "String")
    private String jobDesc;
    @ApiModelProperty(value = "学校id",dataType = "String")
    private String schoolId;
    @ApiModelProperty(value = "设备id",dataType = "String")
    private String equipmentId;
    //分页排序等
    @Transient
    @NotNull
    @Valid
    private Pager pager;
}
