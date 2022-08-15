package com.yice.edu.cn.common.pojo.jw.stuEvaluate;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * app卡片详情
 */
@Data
public class CardDetail {
    @ApiModelProperty(value = "未评价",dataType = "ArrayList")
    private List<StuEvaluateContent>  notEvaluatedList;
    @ApiModelProperty(value = "已评价",dataType = "ArrayList")
    private List<StuEvaluateContent>  alreadyEvaluatedList;
    @ApiModelProperty(value = "未评价个数",dataType = "int")
    private Integer notSubmitNum;
    @ApiModelProperty(value = "已评价个数",dataType = "int")
    private Integer submitNum;
}
