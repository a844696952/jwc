package com.yice.edu.cn.common.pojo.dm.classCard;

import com.yice.edu.cn.common.pojo.Pager;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
/**
*
*
*
*/
@Data
public class DmTimedTask{

    @ApiModelProperty(value = "主键id",dataType = "String")
    private String id;
    @ApiModelProperty(value = "设备id",dataType = "String")
    private String equipmentId;
    @ApiModelProperty(value = "学校id",dataType = "String")
    private String schoolId;
    @ApiModelProperty(value = "周一定时开机时间",dataType = "String")
    private String mondayStartTime;
    @ApiModelProperty(value = "周一定时关机机时间",dataType = "String")
    private String mondayShutdownTime;
    @ApiModelProperty(value = "周二定时开机时间",dataType = "String")
    private String tuesdayStartTime;
    @ApiModelProperty(value = "周二定时关机机时间",dataType = "String")
    private String tuesdayShutdownTime;
    @ApiModelProperty(value = "周三定时开机时间",dataType = "String")
    private String wednesdayStartTime;
    @ApiModelProperty(value = "周三定时关机机时间",dataType = "String")
    private String wednesdayShutdownTime;
    @ApiModelProperty(value = "周四定时开机时间",dataType = "String")
    private String thursdayStartTime;
    @ApiModelProperty(value = "周四定时关机机时间",dataType = "String")
    private String thursdayShutdownTime;
    @ApiModelProperty(value = "周五定时开机时间",dataType = "String")
    private String fridayStartTime;
    @ApiModelProperty(value = "周五定时关机机时间",dataType = "String")
    private String fridayShutdownTime;
    @ApiModelProperty(value = "周六定时开机时间",dataType = "String")
    private String saturdayStartTime;
    @ApiModelProperty(value = "周六定时关机机时间",dataType = "String")
    private String saturdayShutdownTime;
    @ApiModelProperty(value = "周日定时开机时间",dataType = "String")
    private String sundayStartTime;
    @ApiModelProperty(value = "周日定时关机机时间",dataType = "String")
    private String sundayShutdownTime;
    @ApiModelProperty(value = "创建时间",dataType = "String")
    private String createTime;
    @ApiModelProperty(value = "修改时间",dataType = "String")
    private String updateTime;
    @ApiModelProperty(value = "班牌版本",dataType = "String")
    private String version;
    @ApiModelProperty(value = "apk路径",dataType = "String")
    private String apkUrl;
    private String status;//通知状态
    private String remark;//备注
    private String position;//操作人
    private Integer installStatus;//安装状态 0：成功   1：失败
    private Integer downStatus;//下载状态 0：成功 1：失败


    @Transient
    private String[] equipments;//复选框数组 用户名
    private String[] cardIds;//复选框数组 用户名
    //分页排序等
    @Transient
    @NotNull
    @Valid
    private Pager pager;


}
