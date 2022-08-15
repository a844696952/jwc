package com.yice.edu.cn.common.pojo.api.anoah;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;


/**
 * 提供给优学派的接口，亿策平台登录的用户信息
 * 登陆名、姓名、唯一标识ID
 */
@Data
public class TeacherModel implements Serializable {
    @ApiModelProperty(value = "主键id",dataType = "String")
    private String id;
    @ApiModelProperty(value = "姓名",dataType = "String")
    private String name;
    @ApiModelProperty(value = "登陆名",dataType = "String")
    private String tel;

}