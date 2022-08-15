package com.yice.edu.cn.common.pojo.xw.zc.assetsContract;

import com.yice.edu.cn.common.pojo.Pager;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;


@Data
@ApiModel("资产合同")
public class AssetsContract{

    @ApiModelProperty("合同id")
    private String id;
    @ApiModelProperty("学校编号")
    private String schoolId;
    @ApiModelProperty("采购项目")
    private String purchaseName;
    @ApiModelProperty("采购时间")
    private String purchaseTime;
    @ApiModelProperty("合同名称")
    private String contractName;
    @ApiModelProperty("合同地址")
    private String url;
    @ApiModelProperty("合同文件创建时间")
    private String createTime;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;

    @Transient
    private String rowDatas[];
}
