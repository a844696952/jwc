package com.yice.edu.cn.common.pojo.jy.titleQuota;

import com.yice.edu.cn.common.pojo.Pager;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;


@Data
@ApiModel("运营超级管理员手机号码维护")
public class SuperTel{

    @ApiModelProperty("主键")
    private String id;
    @ApiModelProperty("手机号")
    private String tel;
    @ApiModelProperty("createTime")
    private String createTime;
    @ApiModelProperty("updateTime")
    private String updateTime;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
}
