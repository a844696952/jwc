package com.yice.edu.cn.common.pojo.xw.zc.assetsFile;

import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.xw.zc.assetsCatetory.AssetsCategory;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;


@Data
@ApiModel("资产档案")
public class AssetsFile{

    @ApiModelProperty("主键")
    private String id;
    @ApiModelProperty("资产名称")
    private String assetsName;
    @ApiModelProperty("对应的 资产类型ID")
    private String assetsCategoryId;
    @ApiModelProperty("所有根结点id")
    private String assetsCategoryAncestorId;
    @ApiModelProperty("资产属性")
    private Integer assetsProperty;
    @ApiModelProperty("预警数量")
    private Integer assetsThresholdValue;
    @ApiModelProperty("资产单位")
    private String assetsUnit;
    @ApiModelProperty("创建时间")
    private String createTime;
    @ApiModelProperty("创建人")
    private String createUser;
    @ApiModelProperty("学校id")
    private String schoolId;
    @ApiModelProperty("创建人名称")
    private String createUsername;
    @ApiModelProperty("存货编码")
    private String inventoryCode;
    @ApiModelProperty("资产型号")
    private String assetsModel;
    @ApiModelProperty("厂家")
    private String manufacturer;
    @ApiModelProperty("供应商")
    private String supplier;
    @ApiModelProperty("商品价格")
    private String assetsPrice;
    @ApiModelProperty("对应条码")
    private String assetsBarcode;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
    @ApiModelProperty("入库单号")
    private String putInNo;
    @ApiModelProperty("资产类型")
    private AssetsCategory assetsCategory;
    @ApiModelProperty("资产类型id")
    private String assetsCatetoryName;
    @ApiModelProperty("创建时间")
    private String[] createTimeArr;
    @ApiModelProperty("创建时间1")
    private String createTime1;
    @ApiModelProperty("创建时间2")
    private String createTime2;


    @ApiModelProperty("商品价格")
    private String price;

    private String assetsBuyer;



    //资产库存查询统计字段
    @Transient
    @ApiModelProperty("库存总计")
    private Integer stockCount;
    @Transient
    @ApiModelProperty("空闲数量")
    private Integer freeAssetsCount;
    @Transient
    @ApiModelProperty("占用数量")
    private Integer occupyAssetsCount;
    @Transient
    @ApiModelProperty("维修中数量")
    private Integer repairIngCount;
    @Transient
    @ApiModelProperty("故障数量")
    private Integer faultAssetsCount;
    @Transient
    @ApiModelProperty("报废数量")
    private Integer scrapAssetsCount;
    @Transient
    @ApiModelProperty("是否预警,1是2否")
    private Integer isWarning;


    // 出入库统计 查询统计字段
    @ApiModelProperty("入库数量")
    private Integer inCount;
    @ApiModelProperty("出库数量")
    private Integer outCount;
    @ApiModelProperty("归还数量")
    private Integer returnCount;
    @ApiModelProperty("报废数量")
    private Integer scrapCount;
    @ApiModelProperty("维修次数")
    private Integer repairCount;
    @ApiModelProperty("维修总额（元）")
    private BigDecimal repairTotalCost;


    //资产使用记录
    @ApiModelProperty("使用场地")
    private String usePlace;
    @ApiModelProperty("责任人Id")
    private String dutyPersonId;
    @ApiModelProperty("责任人名称")
    private String dutyPersonName;
    @ApiModelProperty("所在部门")
    private String dutyPersonDept;
    @ApiModelProperty("占用数量")
    private Integer useCount;

    @ApiModelProperty("档案年份")
    private String fileYear;

    @ApiModelProperty("档案开始时间")
    private String assertsStartTime;
    @ApiModelProperty("档案结束")
    private String assertsEndTime;

    @ApiModelProperty("使用场地id")
    private String usePlaceId;


    @ApiModelProperty("档案总金额")
    private BigDecimal totalPrice;

    @ApiModelProperty("学年id")
    private String schoolYearId;

    @ApiModelProperty("学期,0上学期，1下学期")
    private Integer term;

    /**
     * 宿舍管理，添加新字段
     */
    @ApiModelProperty("楼栋Id")
    private String buildingId;
    @ApiModelProperty("楼栋名称")
    private String buildingName;
    @ApiModelProperty("使用场地名称")
    private String usePlaceName;
    @ApiModelProperty("场地类型Id")
    private String typeId;

}
