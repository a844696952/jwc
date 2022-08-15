package com.yice.edu.cn.common.pojo.xw.cms;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("首页布局新增或删除条件")
public class XwCmsLayoutCondition {
    @ApiModelProperty("普通栏目左侧id")
    private String columnId1;
    @ApiModelProperty("普通栏目右侧id")
    private String columnId2;
    @ApiModelProperty("通栏栏目id")
    private String columnId;
    @ApiModelProperty("学校id")
    private String schoolId;
    @ApiModelProperty("排序编号")
    private Integer sortNumber;
    @ApiModelProperty("新增 -> true,删除 -> false")
    private Boolean flag;
    @ApiModelProperty("栏目位置 位置1 位置2 位置3")
    private Integer position;
}
