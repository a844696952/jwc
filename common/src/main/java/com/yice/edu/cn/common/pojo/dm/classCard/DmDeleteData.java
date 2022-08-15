package com.yice.edu.cn.common.pojo.dm.classCard;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * Created by 陈飞龙 on 2019/7/19 14:25
 */
@Data
public class DmDeleteData {
    @ApiModelProperty("班级数组")
    private List<String> classIdList;
    private int code;
}
