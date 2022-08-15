package com.yice.edu.cn.common.pojo.dm.ycVeriface.device;

import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.dm.kqDevice.KqDeviceGroupPerson;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;

import java.util.List;


@Data
@ApiModel("亿策人脸识别设备")
public class YcVerifaceDevice{

    @ApiModelProperty("id")
    private String id;
    @ApiModelProperty("deviceId")
    private String deviceId;
    @ApiModelProperty("账号id")
    private String accountId;
    @ApiModelProperty("设备名称")
    private String deviceName;
    @ApiModelProperty("设备厂家id")
    private String deviceFactoryId;
    @ApiModelProperty("设备厂家名称")
    private String deviceFactoryName;
    @ApiModelProperty("设备系列id")
    private String deviceSeriesId;
    @ApiModelProperty("设备系列名称")
    private String deviceSeriesName;
    @ApiModelProperty("进出入标识0入1出-1不区分")
    private String derectionFlag;
    @ApiModelProperty("设备位置id")
    private String deviceLocationId;
    @ApiModelProperty("设备位置名称")
    private String deviceLocationName;
    @ApiModelProperty("设备标识")
    private String deviceSign;
    @ApiModelProperty("视频流地址")
    private String videoStreamAddress;
    @ApiModelProperty("设备类型0摄像头1门禁")
    private String type;
    @ApiModelProperty("学校id")
    private String schoolId;
    @ApiModelProperty("设备分组id")
    private String deviceGroupId;
    @ApiModelProperty("设备分组名称")
    private String deviceGroupName;
    @ApiModelProperty("摄像头识别人员类型")
    private List<String> capturePersonTypeList;//保存数据时校验的
    @ApiModelProperty("分组为宿舍类型时保存宿舍楼id")
    private String  dormitoryId;
    @ApiModelProperty("分组为宿舍类型时保存宿舍楼Name")
    private String dormitoryName;
    @Transient
    @ApiModelProperty("设备状态")
    private String deviceStatus;//0异常，1正常，-1无法识别
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
    /*----------------------------------*/
    @Transient
    @ApiModelProperty("显示级别")
    private Integer level;
    @Transient
    @ApiModelProperty("摄像头识别人员类型文字展示")
    private String capturePersonTypeListShow;//保存数据时校验的


    @Transient
    @ApiModelProperty("门禁告知人员变化code")
    private String changeCode;//门禁告知人员变化code :0清除所有,1变化,2重置人员
    @Transient
    @ApiModelProperty("获取特征值id列表")
    private List<KqDeviceGroupPerson> toGetfeasList;//要获取特征值的用户列表
    @Transient
    @ApiModelProperty("缓存编号")
    private String cacheKey;//缓存id
    @Transient
    @ApiModelProperty("拉取结果")
    private String pullResult;//拉取结果

}
