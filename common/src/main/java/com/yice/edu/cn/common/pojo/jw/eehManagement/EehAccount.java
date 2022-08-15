package com.yice.edu.cn.common.pojo.jw.eehManagement;

import com.yice.edu.cn.common.pojo.Pager;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;


@Data
@ApiModel("")
public class EehAccount{

    @ApiModelProperty("主键id")
    private String id;
    @ApiModelProperty("姓名")
    private String name;
    @ApiModelProperty("电话(普通用户登录账号)")
    private String tel;
    @ApiModelProperty("电教馆id")
    private String eehId;
    @ApiModelProperty("电教馆名称")
    private String eehName;
    @ApiModelProperty("账号类型(1:管理员 2:普通用户)")
    private String accountType;
    @ApiModelProperty("账号(管理员登录账号)")
    private String account;
    @ApiModelProperty("密码")
    private String password;
    @ApiModelProperty("创建时间")
    private String createTime;
    @ApiModelProperty("更新时间 ")
    private String updateTime;
    @ApiModelProperty("平台类型(1:电教馆2:教育局)")
    private String platform;
    @ApiModelProperty("账号状态(1:启用2:禁用)")
    private String status;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;

    @ApiModelProperty("旧密码")
    private String oldPassword;
    @ApiModelProperty("等级：区分省市区(1:省 2:市 3:区)")
    private Integer level;
}
