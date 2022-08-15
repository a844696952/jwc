package com.yice.edu.cn.common.pojo.xw.workerKq;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * @author:xushu
 * @date:2019/5/7
 */
@ApiModel(value = "打卡记录 0迟到 1早退 2正常 3缺卡 4 请假 5补卡 6出差 7公出 8私出 ")
@Data
@Document
public class PunchRules {
    @ApiModelProperty(value = "早上入校", dataType = "String")
    private String morningIn;
    @ApiModelProperty(value = "早上离校", dataType = "String")
    private String morningOut;
    @ApiModelProperty(value = "中午入校", dataType = "String")
    private String noonIn;
    @ApiModelProperty(value = "傍晚离校", dataType = "String")
    private String duskOut;

    @ApiModelProperty(value = "早上入校打卡位置", dataType = "String")
    private KqLocation morningInKqLocation;
    @ApiModelProperty(value = "早上离校打卡位置", dataType = "String")
    private KqLocation morningOutKqLocation;
    @ApiModelProperty(value = "中午入校打卡位置", dataType = "String")
    private KqLocation noonInKqLocation;
    @ApiModelProperty(value = "傍晚离校打卡位置", dataType = "String")
    private KqLocation duskOutKqLocation;

    @ApiModelProperty(value = "早上入校时差", dataType = "String")
    private String morningInTimeLag;
    @ApiModelProperty(value = "早上离校时差", dataType = "String")
    private String morningOutTimeLag;
    @ApiModelProperty(value = "中午入校时差", dataType = "String")
    private String noonInTimeLag;
    @ApiModelProperty(value = "傍晚离校时差", dataType = "String")
    private String duskOutTimeLag;

    @ApiModelProperty(value = "早上入校状态", dataType = "String")
    private String morningInStatus;
    @ApiModelProperty(value = "早上离校状态", dataType = "String")
    private String morningOutStatus;
    @ApiModelProperty(value = "中午入校状态", dataType = "String")
    private String noonInStatus;
    @ApiModelProperty(value = "傍晚离校状态", dataType = "String")
    private String duskOutStatus;

    @ApiModelProperty(value = "上次早上入校状态", dataType = "String")
    private String lastMorningInStatus;
    @ApiModelProperty(value = "上次早上离校状态", dataType = "String")
    private String lastMorningOutStatus;
    @ApiModelProperty(value = "上次中午入校状态", dataType = "String")
    private String lastNoonInStatus;
    @ApiModelProperty(value = "上次傍晚离校状态", dataType = "String")
    private String lastDuskOutStatus;

    @ApiModelProperty(value = "早上入校状态类型", dataType = "String")
    private String morningInStatusType;
    @ApiModelProperty(value = "早上离校状态类型", dataType = "String")
    private String morningOutStatusType;
    @ApiModelProperty(value = "中午入校状态类型", dataType = "String")
    private String noonInStatusType;
    @ApiModelProperty(value = "傍晚离校状态类型", dataType = "String")
    private String duskOutStatusType;

    @ApiModelProperty(value = "早上入校状态详情")
    private   KqDailyStatusDetail morningInStatusDetail;
    @ApiModelProperty(value = "早上离校状态详情")
    private   KqDailyStatusDetail morningOutStatusDetail;
    @ApiModelProperty(value = "中午入校状态详情")
    private   KqDailyStatusDetail noonInStatusDetail;
    @ApiModelProperty(value = "傍晚离校状态详情")
    private   KqDailyStatusDetail duskOutStatusDetail;

    @ApiModelProperty(value = "上次早上入校状态详情")
    private   KqDailyStatusDetail lastMorningInStatusDetail;
    @ApiModelProperty(value = "上次早上离校状态详情")
    private   KqDailyStatusDetail lastMorningOutStatusDetail;
    @ApiModelProperty(value = "上次中午入校状态详情")
    private   KqDailyStatusDetail lastNoonInStatusDetail;
    @ApiModelProperty(value = "上次傍晚离校状态详情")
    private   KqDailyStatusDetail lastDuskOutStatusDetail;
}
