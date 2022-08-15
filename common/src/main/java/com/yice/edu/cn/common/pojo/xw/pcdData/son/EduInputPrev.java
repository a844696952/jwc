package com.yice.edu.cn.common.pojo.xw.pcdData.son;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EduInputPrev {
    private Integer year;
    @ApiModelProperty("小学")
    private int primary;
    @ApiModelProperty("初中")
    private int middle;
    @ApiModelProperty("高中")
    private int high;
}
