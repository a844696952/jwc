package com.yice.edu.cn.common.pojo.dm.dmClassCardArea;

import com.yice.edu.cn.common.pojo.Pager;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;


@Data
@ApiModel("电子班牌场地设备管理")
public class DmClassCardArea{

    @ApiModelProperty("电子班牌设备场地编号")
    private String id;
    @ApiModelProperty("学校编号")
    private String schoolId;
    @ApiModelProperty("设备编号")
    private String deviceId;
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
