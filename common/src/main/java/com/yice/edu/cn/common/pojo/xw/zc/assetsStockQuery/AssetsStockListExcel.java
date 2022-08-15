package com.yice.edu.cn.common.pojo.xw.zc.assetsStockQuery;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

@Data
public class AssetsStockListExcel{

    @Excel(name = "存货编码",orderNum = "1")
    private String inventoryCode;

    @Excel(name = "资产名称",orderNum = "2")
    private String assetsName;

    @Excel(name = "资产属性",orderNum = "3")
    private String assetsProperty;

    @Excel(name = "资产单位",orderNum = "4")
    private String assetsUnit;

    @Excel(name="库存总计",groupName = "总量",orderNum = "5")
    private Integer stockCount;

    @Excel(name = "空闲数量",groupName = "总量",orderNum = "6")
    private Integer freeAssetsCount;

    @Excel(name = "占用数量",groupName = "总量",orderNum = "7")
    private Integer occupyAssetsCount;

    @Excel(name = "维修中数量",groupName = "总量",orderNum = "8")
    private Integer repairIngCount;

    @Excel(name = "故障数量",groupName = "总量",orderNum = "9")
    private Integer faultAssetsCount;

    @Excel(name = "报废数量",orderNum = "10")
    private Integer scrapAssetsCount;

    @Excel(name = "预警数量",orderNum = "11")
    private Integer assetsThresholdValue;

    @Excel(name = "是否预警",orderNum = "12")
    private String isWarning;
}
