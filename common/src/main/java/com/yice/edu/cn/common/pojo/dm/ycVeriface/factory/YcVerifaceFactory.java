package com.yice.edu.cn.common.pojo.dm.ycVeriface.factory;

import com.yice.edu.cn.common.pojo.Pager;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;


@Data
@ApiModel("人脸识别设备厂家")
public class YcVerifaceFactory{

    @ApiModelProperty("id")
    private String id;
    @ApiModelProperty("厂家名称")
    private String factoryName;
    @ApiModelProperty("创建时间")
    private String createTime;
    @ApiModelProperty("更新时间")
    private String updateTime;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
}
