package com.yice.edu.cn.common.pojo.xw.zc.assetsBasicCategory;

import com.yice.edu.cn.common.pojo.Pager;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;


@Data
@ApiModel("资产类型基础表")
public class AssetsBasicCategory{

    @ApiModelProperty("主键 id")
    private String id;
    @ApiModelProperty("资产类型名称")
    private String name;
    @ApiModelProperty("是否有儿子")
    private String isChildren;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
}
