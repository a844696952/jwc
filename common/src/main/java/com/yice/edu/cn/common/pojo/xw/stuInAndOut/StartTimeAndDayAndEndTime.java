package com.yice.edu.cn.common.pojo.xw.stuInAndOut;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("周几和结束时间")
public class StartTimeAndDayAndEndTime {
    private String id;
    @ApiModelProperty("开始时间")
    private String startTime;
    private List<DayAndEndTime> list;
}
