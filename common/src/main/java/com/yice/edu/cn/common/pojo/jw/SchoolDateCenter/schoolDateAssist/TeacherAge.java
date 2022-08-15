package com.yice.edu.cn.common.pojo.jw.SchoolDateCenter.schoolDateAssist;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Api("教师年龄段图")
@Data
public class TeacherAge {
    @ApiModelProperty("横坐标初始值")
    private Long startNumber;
    @ApiModelProperty("横坐标结束值")
    private Long endNumber;
    @ApiModelProperty("纵坐标值")
    private Long y;
    @ApiModelProperty("横轴文字")
    private String xText;
}
