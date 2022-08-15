package com.yice.edu.cn.common.pojo.xw.pcdDoc;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@Api("文件")
public class FileName {
    @ApiModelProperty("文件名称")
    private String fileName;
    @ApiModelProperty("文件地址")
    private String fileUrl;
}
