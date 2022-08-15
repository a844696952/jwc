package com.yice.edu.cn.common.pojo.wk;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("微课文档对象")
public class MicroLesson {
    @ApiModelProperty("文档内容")
    private String content;
    @ApiModelProperty("文档名称")
    private String fileName;


}
