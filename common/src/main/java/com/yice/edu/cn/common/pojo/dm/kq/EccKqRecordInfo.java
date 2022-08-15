package com.yice.edu.cn.common.pojo.dm.kq;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EccKqRecordInfo {


    @ApiModelProperty("打卡时间")
    private String dkTime;

    @ApiModelProperty(value = "开启状态 0-默认状态 1-未到 2--迟到 3--已到 4--请假")
    private Integer kqState;

}
