package com.yice.edu.cn.common.pojo.mes.wxPush;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class WxData {

    @ApiModelProperty("主键id")
    private String id;

    @ApiModelProperty("内容")
    private String value;

    @ApiModelProperty("key键排序")
    private Integer sort;

    @ApiModelProperty("模板id")
    private String temId;

    @ApiModelProperty("key")
    private String Key;

    @ApiModelProperty("创建时间")
    private String createTime;

    @ApiModelProperty("学校id")
    private String schoolId;

}
