package com.yice.edu.cn.common.pojo.dm.kqDevice;

import com.yice.edu.cn.common.pojo.Pager;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;

import java.util.List;


@Data
@ApiModel("考勤设备与分组关联表")
public class KqDevicePerson{

    @ApiModelProperty("主键")
    private String id;
    @ApiModelProperty("设备分组名")
    private String groupName;
    @ApiModelProperty("设备分组id")
    private String groupId;
    @ApiModelProperty("设备分组类型")
    private List<String> groupType;
    @ApiModelProperty("设备编号")
    private String deviceNo;
    @ApiModelProperty(value = "设备名称", dataType = "String")
    private String deviceName;
    @ApiModelProperty("创建时间")
    private String createTime;
    @ApiModelProperty("更新时间")
    private String updateTime;
    @ApiModelProperty("学校id")
    private String schoolId;


    @ApiModelProperty("绑定的人员id")
    private String personId;
    @ApiModelProperty("绑定的人员姓名")
    private String personName;
    @ApiModelProperty("绑定的人员类型,1：教师，2：住校生 4：走读生 5：访客")
    private String personType;

    @ApiModelProperty(value = "设备类型,1摄像头，2门禁机", dataType = "String")
    private String deviceType;
    @Transient
    @ApiModelProperty(value = "进出标识，0入，1出，-1不区分", dataType = "String")
    private String derectionFlag;
    @Transient
    @ApiModelProperty(value = "设备状态，0异常，1正常，-1未加入", dataType = "String")
    private String deviceStatus;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
}
