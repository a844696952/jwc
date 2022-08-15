package com.yice.edu.cn.common.pojo.xw.zc.assertsInOutQuery;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AssetsInOutListExcel {


    @Excel(name = "资产名称",orderNum = "2")
    private String assetsName;

    @Excel(name = "资产属性",orderNum = "3")
    private String assetsProperty;

    @Excel(name = "资产单位",orderNum = "4")
    private String assetsUnit;

    @Excel(name = "入库数量",orderNum = "5")
    private Integer inCount;

    @Excel(name = "出库数量",orderNum = "6")
    private Integer outCount;

    @Excel(name = "归还数量",orderNum = "7")
    private Integer returnCount;

    @Excel(name = "报废数量",orderNum = "8")
    private Integer scrapCount;

    @Excel(name = "维修次数",orderNum = "9")
    private Integer repairCount;

    @Excel(name = "维修总额（元）",orderNum = "10")
    private BigDecimal repairTotalCost;



}
