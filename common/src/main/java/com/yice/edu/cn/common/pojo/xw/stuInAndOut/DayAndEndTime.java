package com.yice.edu.cn.common.pojo.xw.stuInAndOut;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("周几和结束时间")
public class DayAndEndTime {
    private String id;
    @ApiModelProperty("weekDay")
    private String weekDay;
    @ApiModelProperty("endTime")
    private String endTime;
}
