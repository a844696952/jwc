package com.yice.edu.cn.common.pojo.dm.ycVeriface.deviceSeries;

import com.yice.edu.cn.common.pojo.Pager;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;


@Data
@ApiModel("设备系列")
public class YcVerifaceDeviceSeries{

    @ApiModelProperty("id")
    private String id;
    @ApiModelProperty("创建时间")
    private String createTime;
    @ApiModelProperty("更新时间")
    private String updateTime;
    @ApiModelProperty("厂家id")
    private String factoryId;
    @ApiModelProperty("系列名称")
    private String seriesName;
    @ApiModelProperty("视频流地址示例")
    private String videoStreamAddress;
    @ApiModelProperty("厂家名称")
    private String factoryName;
    @ApiModelProperty("设备系列类型,摄像头0，门禁1")
    private String seriesType;

    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
}
