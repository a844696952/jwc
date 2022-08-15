package com.yice.edu.cn.common.pojo.jw.teacher;

import com.yice.edu.cn.common.pojo.Pager;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
@ApiModel("cms登录地址表")
public class JwCmsAddress {

    @ApiModelProperty("主键id")
    private String id;
    @ApiModelProperty("cms登录地址")
    private String loginUrl;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
}
