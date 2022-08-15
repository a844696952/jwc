package com.yice.edu.cn.common.pojo.jw.building;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class SpaceOfType {
    @ApiModelProperty(value = "场地类型",dataType = "String")
    private String typeName;
    @ApiModelProperty(value = "场地类型数量",dataType = "String")
    private String typeNum;

}
