package com.yice.edu.cn.common.pojo.dm.ycVeriface.account;

import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.dm.ycVeriface.device.YcVerifaceDevice;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;

import java.util.List;


@Data
@ApiModel("人脸识别账号")
public class YcVerifaceAccount{

    @ApiModelProperty("id")
    private String id;
    @ApiModelProperty("账号")
    private String account;
    @ApiModelProperty("密码")
    private String password;
    @ApiModelProperty("账号类型0摄像头1门禁")
    private String type;
    @ApiModelProperty("备注")
    private String remarks;
    @ApiModelProperty("学校id")
    private String schoolId;
    @ApiModelProperty("创建时间")
    private String createTime;
    @ApiModelProperty("更新时间")
    private String updateTime;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
/*------------------------------------------------------*/


    @Transient
    @ApiModelProperty("设备列表")
    private List<YcVerifaceDevice> children;
    @Transient
    @ApiModelProperty("显示级别")
    private Integer level;
    @Transient
    @ApiModelProperty("主机状态,0异常,1正常")
    private Integer connectStatus;
    @Transient
    @ApiModelProperty("账号id")
    private String accountId;
    @Transient
    @ApiModelProperty("deviceId")
    private String deviceId;
}
