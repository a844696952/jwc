package com.yice.edu.cn.common.pojo.jw.teacher;

import cn.hutool.json.JSONObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("登录对象")
public class LoginObj {
    @ApiModelProperty(value = "电话号码")
    private String tel;
    @ApiModelProperty(value = "密码")
    private String password;
    @ApiModelProperty(value = "小程序openId")
    private String openId;
    @ApiModelProperty(value = "验证码或编码")
    private String code;
    @ApiModelProperty("类型，0表示学校教师，1表示校外账号")
    private int type;
    @ApiModelProperty("tap登录为0，小程序登录为1,家长端为2")
    private Integer appPermType;
    @ApiModelProperty(value = "版本号")
    private Integer appVersion;
    @ApiModelProperty(value = "学校id")
    private String schoolId;
    @ApiModelProperty(value = "微信jscode2session接口返回的对象")
    private JSONObject jscode2sessionObject;
}
