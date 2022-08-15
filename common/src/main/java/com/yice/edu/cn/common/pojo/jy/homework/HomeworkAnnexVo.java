package com.yice.edu.cn.common.pojo.jy.homework;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 *
 *附件
 *
 */
@Data
public class HomeworkAnnexVo {
    @ApiModelProperty(value = "附件地址",dataType = "String")
    private String fileUrl;
    @ApiModelProperty(value = "大小",dataType = "String")
    private String size;
}
