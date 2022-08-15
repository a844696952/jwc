package com.yice.edu.cn.common.pojo.xw.zc.assertsInOutQuery;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

@Data
public class AssetsUseListExcel {


    @Excel(name = "资产名称",orderNum = "1")
    private String assetsName;

    @Excel(name = "资产属性",orderNum = "2")
    private String assetsProperty;

    @Excel(name = "楼栋名称",orderNum = "3")
    private String buildingName;

    @Excel(name = "使用场地",orderNum = "4")
    private String usePlaceName;

    @Excel(name = "责任人",orderNum = "5")
    private String dutyPersonName;

    @Excel(name = "所在部门",orderNum = "6")
    private String dutyPersonDept;

    @Excel(name = "占用数量",orderNum = "7")
    private Integer useCount;


}
