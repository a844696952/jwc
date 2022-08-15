package com.yice.edu.cn.common.pojo.xw.kq;

import com.yice.edu.cn.common.pojo.Pager;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @BelongsProject: yep
 * @BelongsPackage: com.yice.edu.cn.common.pojo.xw.kq
 * @Author: Administrator
 * @CreateTime: 2019-03-05 14:26
 * @Description: ${Description}
 */
@Data
public class ZyDeviceBean {
    @ApiModelProperty(value = "主键", dataType = "String")
    private String id;
    @ApiModelProperty(value = "设备编号", dataType = "String")
    private String deviceNo;
    @ApiModelProperty(value = "设备名称", dataType = "String")
    private String deviceName;
    @ApiModelProperty(value = "设备位置", dataType = "String")
    private String deviceLocation;
    @ApiModelProperty(value = "进出标识，0入，1出，-1不区分", dataType = "String")
    private String derectionFlag;
    @ApiModelProperty(value = "设备类型,1摄像头，2门禁机", dataType = "String")
    private String deviceType;
    @ApiModelProperty(value = "设备状态，0异常，1正常，-1未加入", dataType = "String")
    private String deviceStatus;
    @ApiModelProperty(value = "设备开关，0关闭，1开启", dataType = "String")
    private String deviceSwitch;
    @ApiModelProperty("设备分组名")
    private String groupName;
    @ApiModelProperty("设备分组id")
    private String groupId;
    @ApiModelProperty("安防平台是否已经移除")
    private String removed;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
}
