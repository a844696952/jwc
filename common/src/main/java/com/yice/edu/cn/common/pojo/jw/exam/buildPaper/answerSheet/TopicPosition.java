package com.yice.edu.cn.common.pojo.jw.exam.buildPaper.answerSheet;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("题目的坐标")
public class TopicPosition {
    @ApiModelProperty("左上角坐标x轴")
    private Double x;
    @ApiModelProperty("左上角坐标y轴")
    private Double y;
    @ApiModelProperty("该大题的宽度")
    private Double width;
    @ApiModelProperty("该大题的高度")
    private Double height;
    @ApiModelProperty("该题所在的页码")
    private Integer page;
}
