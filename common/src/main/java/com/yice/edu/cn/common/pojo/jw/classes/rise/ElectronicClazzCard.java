package com.yice.edu.cn.common.pojo.jw.classes.rise;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 电子班牌
 * @author 20554
 *
 */
@ApiModel(value = "电子班牌",description="")
@Data
public class ElectronicClazzCard {
    @ApiModelProperty(value = "班级id",dataType = "String")
	 private String clazzId;
     @ApiModelProperty(value = "班牌账号id",dataType = "String")
     private String dmClassCardId;
}
