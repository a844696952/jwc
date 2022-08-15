package com.yice.edu.cn.common.pojo.dm.kq;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EccKqStuLeaveInfo {

    @ApiModelProperty("请假开始时间")
    private String beginTime;

    @ApiModelProperty("请假结束时间")
    private String endTime;

    @ApiModelProperty(value = "开启状态 0-默认状态 1-未到 2--迟到 3--已到 4--请假")
    private Integer kqState;



}
