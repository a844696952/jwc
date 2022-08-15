package com.yice.edu.cn.common.pojo.mes.classManage.rules;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
@Data
public class Query {

    private List<MesAppletsClassRule> mesAppletsClassRules;

    @ApiModelProperty("标签类型 0--待改进 1--表扬")
    private Integer tagType;

    @ApiModelProperty("父Id")
    private String parentId;
}
