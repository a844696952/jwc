package com.yice.edu.cn.common.pojo.xq.AnalyseClassScore;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("四率对象")
public class ExamRateVo {
	@ApiModelProperty(value = "最小分数值",dataType = "Double")
	private Double minScoreRange=0.0;
	@ApiModelProperty(value = "最大分数值",dataType = "Double")
	private Double maxScoreRange=0.0;
	@ApiModelProperty(value = "所占比例(单位 %)",dataType = "Double")
	private Double scoreRate=0.0;
	@ApiModelProperty(value = "该分数范围内的人数个数",dataType = "Long")
	private Integer num=0;
}
