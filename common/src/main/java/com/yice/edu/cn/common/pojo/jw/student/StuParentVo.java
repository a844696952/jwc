package com.yice.edu.cn.common.pojo.jw.student;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class StuParentVo {
    @ApiModelProperty(value = "家长登录账号",dataType = "String")
    private String tel;
    @ApiModelProperty(value = "学生密码",dataType = "String")
    private String password;
    @ApiModelProperty(value = "学生姓名",dataType = "String")
    private String name;
}
