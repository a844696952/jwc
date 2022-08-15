package com.yice.edu.cn.common.pojo.wb.classRegister;

import com.yice.edu.cn.common.pojo.Pager;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;


@Data
@ApiModel("")
public class HelpCenter{

    @ApiModelProperty("主键id")
    private String id;
    @ApiModelProperty("在线客服1")
    private String customerService1;
    @ApiModelProperty("在线客服2")
    private String customerService2;
    @ApiModelProperty("在线客服3")
    private String customerService3;
    @ApiModelProperty("早班上班时间")
    private String morningStartTime;
    @ApiModelProperty("早班下班时间")
    private String morningEndTime;
    @ApiModelProperty("晚班上班时间")
    private String nightStartTime;
    @ApiModelProperty("晚班下班时间")
    private String nightEndTime;
    @ApiModelProperty("0:白板客服中心")
    private Integer type;
    @ApiModelProperty("客服热线1")
    private String serviceHotline1;
    @ApiModelProperty("客服热线2")
    private String serviceHotline2;
    //分页排序等
    @Transient
/*    @NotNull(message = "pager不能为空")
    @Valid*/
    private Pager pager;
}
