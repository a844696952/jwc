package com.yice.edu.cn.common.pojo.jy.titleQuota;

import com.yice.edu.cn.common.pojo.Pager;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;


@Data
@ApiModel("")
public class PlatformTopichiscoty{

    @ApiModelProperty("id")
    private String id;
    @ApiModelProperty("platformId")
    private String platformId;
    @ApiModelProperty("historyTitleQuotaId")
    private String historyTitleQuotaId;
    @ApiModelProperty("createTime")
    private String createTime;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
}
