package com.yice.edu.cn.common.pojo.jw.school;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class SchoolExt extends School {
    private Integer fromYear;
    private Integer toYear;
    private String fromTo;
    @ApiModelProperty("上学期开始时间")
    private String lastTermBegin;
    @ApiModelProperty("下学期开始时间")
    private String nextTermBegin;
}
