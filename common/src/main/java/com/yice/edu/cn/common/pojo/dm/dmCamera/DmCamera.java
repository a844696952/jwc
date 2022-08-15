package com.yice.edu.cn.common.pojo.dm.dmCamera;

import com.yice.edu.cn.common.pojo.Pager;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;


@Data
@ApiModel("云班牌摄像头管理")
public class DmCamera{

    @ApiModelProperty("摄像头编号")
    private String id;
    @ApiModelProperty("学校编号")
    private String schoolId;
    @ApiModelProperty("讲师编号")
    private String teacherId;
    @ApiModelProperty("摄像头名称")
    private String cameraName;
    @ApiModelProperty("摄像头型号")
    private String cameraModule;
    @ApiModelProperty("rtsp 推流地址")
    private String rtsp;
    @ApiModelProperty("设备编号编号")
    private String deviceId;
    @ApiModelProperty("备注")
    private String remark;
    @ApiModelProperty("场地编号")
    private String areaId;
    @ApiModelProperty("场地名称")
    private String areaName;
    @ApiModelProperty("修改时间")
    private String updateTime;
    @ApiModelProperty("创建时间")
    private String createTime;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
}
