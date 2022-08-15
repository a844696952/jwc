package com.yice.edu.cn.common.pojo.xw.zc.assetsStockQuery;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@ApiModel("资产库存汇总数据封装对象")
public class AssetsStockGatherDto {
    @ApiModelProperty("当前库存总计")
    private Integer stockDetailTotal;
    @ApiModelProperty("固定资产库存")
    private Integer fixedAssetsTotal;
    @ApiModelProperty("易耗品库存")
    private Integer consumablesAssetsTotal;
    @ApiModelProperty("报废总量")
    private Integer scrapTotal;
    @ApiModelProperty("维修总额")
    private BigDecimal upkeepCostsTotal;
}
