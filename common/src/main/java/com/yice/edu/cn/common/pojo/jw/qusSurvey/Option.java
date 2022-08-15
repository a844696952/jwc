package com.yice.edu.cn.common.pojo.jw.qusSurvey;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 *
 *问题选项表
 *
 */
@Data
public class Option {
    @ApiModelProperty(value = "选项id",dataType = "String")
    private String optionId;
    @ApiModelProperty(value = "选项内容",dataType = "String")
    private String optionContent;
    @ApiModelProperty(value = "选项分数",dataType = "String")
    private String optionScore;
    @ApiModelProperty(value = "选项次数",dataType = "int")
    private Integer optionNum;
}
