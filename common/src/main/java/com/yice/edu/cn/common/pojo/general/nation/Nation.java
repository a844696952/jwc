package com.yice.edu.cn.common.pojo.general.nation;

import com.yice.edu.cn.common.pojo.Pager;
import lombok.Data;
import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;


@Data
@ApiModel("中国 民族信息")
public class Nation{

    @ApiModelProperty("主键id")
    private String id;
    @ApiModelProperty("名称")
    private String name;
    @ApiModelProperty("编码")
    private String code;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
}
