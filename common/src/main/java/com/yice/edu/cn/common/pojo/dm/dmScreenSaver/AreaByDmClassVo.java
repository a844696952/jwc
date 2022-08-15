package com.yice.edu.cn.common.pojo.dm.dmScreenSaver;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class AreaByDmClassVo {
    @ApiModelProperty("用户名")
    private String id;
    @ApiModelProperty("班级名称")
    private String label;
    @ApiModelProperty("是否选中")
    private boolean selected;
    private List<AreaByDmClassVo> children;
    private boolean indeterminate=false;

}
