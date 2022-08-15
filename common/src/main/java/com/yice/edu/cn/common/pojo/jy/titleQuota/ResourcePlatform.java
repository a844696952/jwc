package com.yice.edu.cn.common.pojo.jy.titleQuota;

import com.yice.edu.cn.common.pojo.Pager;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Transient;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;


@Data
@ApiModel("运营平台（题库资源平台）")
@Accessors(chain = true)
public class ResourcePlatform extends BaseVo{

    @ApiModelProperty("主键")
    private String id;
    @ApiModelProperty("题库平台")
    private String resourceName;
    @ApiModelProperty("创建时间")
    private String createTime;
    @ApiModelProperty("备注")
    private String remarks;
    @ApiModelProperty("更新时间")
    private String updateTime;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;


}
