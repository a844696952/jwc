package com.yice.edu.cn.common.pojo.api.thirdParty;

import com.yice.edu.cn.common.pojo.Pager;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;


@Data
@ApiModel("第三方账号")
public class ThirdParty{

    @ApiModelProperty("id")
    private String id;
    @ApiModelProperty("第三方名称")
    private String name;
    @ApiModelProperty("appKey")
    private String appKey;
    @ApiModelProperty("应用链接")
    private String applyUrl;
    @ApiModelProperty("图标")
    private String iconUrl;
    @ApiModelProperty("创建时间")
    private String createTime;
    @ApiModelProperty("排序")
    private Integer sort;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
}
