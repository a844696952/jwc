package com.yice.edu.cn.common.pojo.dm.cameraManage;

import com.yice.edu.cn.common.pojo.Pager;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;


@Data
@ApiModel("监控设备管理")
public class CameraManage{

    @ApiModelProperty("id")
    private String id;
    @ApiModelProperty("学校ID")
    private String schoolId;
    @ApiModelProperty("监控点名称")
    private String cameraName;
    @ApiModelProperty("用途")
    private String remark;
    @ApiModelProperty("网络A链接地址")
    private String urlA;
    @ApiModelProperty("设备网络名称A")
    private String urlaName;
    @ApiModelProperty("网络B链接地址")
    private String urlB;
    @ApiModelProperty("设备网络名称B")
    private String urlbName;
    @ApiModelProperty("创建时间")
    private String createTime;
    @ApiModelProperty("修改时间")
    private String updateTime;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;

    //日期查询字段
    private String[] searchTimeZone;
    private String searchStearTime;
    private String searchEndTime;
}
