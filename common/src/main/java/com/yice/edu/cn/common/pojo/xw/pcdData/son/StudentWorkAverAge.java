package com.yice.edu.cn.common.pojo.xw.pcdData.son;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Api("学生作业情况")
@Data
public class StudentWorkAverAge {
    @ApiModelProperty("平均做题数")
    private Double averTopicCount;
    @ApiModelProperty("平均做题时间")
    private Double averTopicTime;
    @ApiModelProperty("平均作业提交率")
    private String averWorkRatio;
}
