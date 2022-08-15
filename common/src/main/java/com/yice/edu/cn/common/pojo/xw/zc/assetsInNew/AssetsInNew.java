package com.yice.edu.cn.common.pojo.xw.zc.assetsInNew;

import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.xw.zc.assetsFile.AssetsFile;
import com.yice.edu.cn.common.pojo.xw.zc.assetsStockDetail.AssetsStockDetail;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;


@Data
@ApiModel("资产入库")
public class AssetsInNew{

    @ApiModelProperty("主键")
    private String id;
    @ApiModelProperty("资产档案id")
    private String assetsFileId;
    @ApiModelProperty("存货编码")
    private String inventoryCode;
    @ApiModelProperty("入库数量")
    private Integer putInQuantity;
    @ApiModelProperty("所在仓库ID")
    private String putInWarehouse;
    @ApiModelProperty("所在仓库name")
    private String putInWarehouseName;
    @ApiModelProperty("采购人")
    private String assetsBuyer;
    @ApiModelProperty("采购日期")
    private String assetsBuyTime;
    @ApiModelProperty("资产使用期限单位 0：年    1：月")
    private String assetsUseTimelimitNumber;
    @ApiModelProperty("资产使用期限单位对应数量")
    private String assetsUseTimelimitUnit;
    @ApiModelProperty("资产使用截止日期：如果2020年3月")
    private String assetsUseTimelimitReal;
    @ApiModelProperty("资产维保期限单位 0：年    1：月")
    private String assetsMaintenanceTimelimitUnit;
    @ApiModelProperty("资产维保期限单位对应数量")
    private String assetsMaintenanceTimelimitNumber;
    @ApiModelProperty("资产维保期日期：2021年4月")
    private String assetsMaintenanceTimelimitReal;
    @ApiModelProperty("使用期限，如：2年")
    private String validDate;
    @ApiModelProperty("维保日期，如：3月")
    private String maintenanceDate;
    @ApiModelProperty("商品价格")
    private Double price;
    @ApiModelProperty("入库单号")
    private String putInNo;
    @ApiModelProperty("入库时间")
    private String putInTime;
    @ApiModelProperty("采购时间")
    private String buyTime;
    @ApiModelProperty("单位")
    private String unit;
    @ApiModelProperty("备注")
    private String remark;
    @ApiModelProperty("创建时间")
    private String createTime;
    @ApiModelProperty("创建人")
    private String createUser;
    @ApiModelProperty("创建人ID")
    private String createId;
    @ApiModelProperty("创建人名称")
    private String createUsername;
    @ApiModelProperty("资产类型id")
    private String assetsCategoryId;
    @ApiModelProperty("资产类型所有祖先id，包括自己")
    private String assetsCategoryAncestorId;
    @ApiModelProperty("学校id")
    private String schoolId;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;



    @ApiModelProperty("资产名称")
    private String assetsName;
    @ApiModelProperty("资产属性")
    private String assetsProperty;
    @ApiModelProperty("资产单位")
    private String assetsUnit;
    @ApiModelProperty("资产模型")
    private String assetsModel;
    @ApiModelProperty("厂家")
    private String manufacturer;
    @ApiModelProperty("供应商")
    private String supplier;
    @ApiModelProperty("商品价格")
    private String assetsPrice;
    @ApiModelProperty("对应条码")
    private String assetsBarcode;
    @ApiModelProperty("采购单价")
    private BigDecimal assetsUnitPrice;
    @ApiModelProperty("资产图片（单张）")
    private String assetsPic;
    @ApiModelProperty("过期时间")
    private String expireTime;


    @ApiModelProperty("是否超出有效期限")
    private String isOverValidTime;

    @ApiModelProperty("是否超出维保期限")
    private String isOverMaintenanceTime;

    private AssetsFile assetsFile;

    private List<AssetsInNew> assetsInNewList;

    private List<AssetsStockDetail> assetsStockDetailList;



    @ApiModelProperty("页面查询时间范围")
    private String[] putInTimeRange;
    @ApiModelProperty("页面查询开始时间")
    private String searchStartTime;
    @ApiModelProperty("页面查询结束时间")
    private String searchEndTime;




}
