package com.yice.edu.cn.common.pojo.dm.honourRoll;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by 陈飞龙 on 2019/5/21 13:45
 */
@Data
@ApiModel("光荣榜，")
public class EccHonourRoll {

    @ApiModelProperty("学生姓名")
    private String name;
    @ApiModelProperty("学生头像")
    private String imgUrl;
    @ApiModelProperty("学生编号")
    private String id;
    @ApiModelProperty("获奖时间")
    private String honourTime;

}
