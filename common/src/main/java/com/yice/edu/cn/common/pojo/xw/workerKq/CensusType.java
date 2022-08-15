package com.yice.edu.cn.common.pojo.xw.workerKq;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author:xushu
 * @date:2019/5/8
 */
@Data
@Document
@ApiModel("统计阶梯类型")
public class CensusType {
    @ApiModelProperty(value = "统计阶梯类型 0迟到 1早退", dataType = "String")
    private String censusType;
    @ApiModelProperty(value = "统计时长")
    private String censusTime;

}
