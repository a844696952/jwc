package com.yice.edu.cn.common.pojo.dm.classes;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value = "年纪班级的树形展示")
public class GradeAndClasses {
    @ApiModelProperty("编号，外层显示的是年纪编号，内层显示的是班级编号")
    private String id;
    @ApiModelProperty("名称")
    private String name;
    @ApiModelProperty("子集对象")
    private List<GradeAndClasses> children;
}
