package com.yice.edu.cn.common.pojo.xw.zc.assetsStockDetail;

import com.yice.edu.cn.common.pojo.jw.schoolYear.CurSchoolYear;
import com.yice.edu.cn.common.pojo.xw.zc.assetsCatetory.AssetsCategory;
import com.yice.edu.cn.common.pojo.xw.zc.assetsFile.AssetsFile;
import com.yice.edu.cn.common.pojo.xw.zc.assetsInNew.AssetsInNew;
import com.yice.edu.cn.common.pojo.xw.zc.assetsOutNew.AssetsOutNew;
import com.yice.edu.cn.common.pojo.xw.zc.assetsWarehouse.AssetsWarehouse;
import com.yice.edu.cn.common.pojo.xw.zc.repairNew.RepairFile;
import com.yice.edu.cn.common.pojo.xw.zc.repairNew.RepairNew;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;


@Data
@ApiModel("资产库存明细")
public class AssetsStockDetail extends CurSchoolYear {

    @ApiModelProperty("主键ID")
    private String id;
    @ApiModelProperty("资产编号")
    private String assetsNo;
    @ApiModelProperty("存货编码")
    private String inventoryCode;
    @ApiModelProperty("资产档案 id")
    private String assetsFileId;
    @ApiModelProperty("资产类型 id")
    private String assetsCategoryId;
    @ApiModelProperty("资产类型 所有祖先id，包括自己id")
    private String assetsCategoryAncestorId;
    @ApiModelProperty("资产名称")
    private String assetsName;
    @ApiModelProperty("资产属性，1：固定资产  2：易耗品")
    private Integer assetsProperty;
    @ApiModelProperty("资产单位")
    private String assetsUnit;
    @ApiModelProperty("资产模型")
    private String assetsModel;
    @ApiModelProperty("厂家")
    private String manufacturer;
    @ApiModelProperty("供应商")
    private String supplier;
    @ApiModelProperty("商品价格")
    private Double assetsPrice;
    @ApiModelProperty("对应条码")
    private String assetsBarcode;
    @ApiModelProperty("采购日期")
    private String assetsBuyTime;
    @ApiModelProperty("资产状态，1：空闲  2：占用  3：维修中  4：故障 5：报废")
    private Integer status;
    @ApiModelProperty("入库单号")
    private String putInNo;
    @ApiModelProperty("归还时间")
    private String returnTime;
    @ApiModelProperty("维修之前的状态。1.维修之用是空闲。2维修之被占用")
    private Integer beforeRepairStatus;
    @ApiModelProperty("资产图片")
    private String assetsPic;
    @ApiModelProperty("物料具体备注")
    private String assetsRemark;
    @ApiModelProperty("删除标准 del 1:2(正常：删除)")
    private Integer del;
    @ApiModelProperty("删除员工ID")
    private String delStaffId;
    @ApiModelProperty("删除员式姓名")
    private String delStaffName;
    @ApiModelProperty("删除操作时间")
    private String delTime;
    @ApiModelProperty("处理时间")
    private String updateTime;
    @ApiModelProperty("学校ID")
    private String schoolId;
    @ApiModelProperty("所在仓库名字")
    private String putInWarehouseName;
    @ApiModelProperty("所在仓库ID")
    private String putInWarehouse;
    @ApiModelProperty("入库时间")
    private String putInTime;
    @ApiModelProperty("借出操作人")
    private String borrowOperaterId;
    @ApiModelProperty("借出操作人名字")
    private String borrowOperaterName;
    @ApiModelProperty("借出时间")
    private String borrowTime;
    @ApiModelProperty("责任人，即借出使用人，一般是从学校老师，默认为空。")
    private String dutyPerson;
    @ApiModelProperty("责任人名称")
    private String dutyPersonName;
    @ApiModelProperty("责任人部门ID")
    private String dutyPersonDeptId;
    @ApiModelProperty("责任人部门")
    private String dutyPersonDept;
    @ApiModelProperty("使用场地 id")
    private String usePlaceId;
    @ApiModelProperty("使用场地")
    private String usePlace;
    @ApiModelProperty("报废操作人ID")
    private String operatorId;
    @ApiModelProperty("报废操作人名称")
    private String operatorName;
    @ApiModelProperty("报废理由")
    private String scrapRemark;
    @ApiModelProperty("报废时间")
    private String scrapTime;
    @ApiModelProperty("维修人id")
    private String repairStaffId;
    @ApiModelProperty("维修名字")
    private String repairStaffName;
    @ApiModelProperty("维修人电话")
    private String repairStaffTel;
    @ApiModelProperty("处理操作人ID")
    private String disposeStaffId;
    @ApiModelProperty("处理操作人名字")
    private String disposeStaffName;
    @ApiModelProperty("处理类型 (1 维修成功  (默认)  2无法维修)")
    private Integer processType;
    @ApiModelProperty("处理费用(维修费用)")
    private Double upkeepCosts;
    @ApiModelProperty("使用截止日期")
    private String useTimeLimitDate;
    @ApiModelProperty("维保截止日期")
    private String maintenanceTimeLimitUnit;
    //分页排序等
//    @Transient
//    @NotNull(message = "pager不能为空")
//    @Valid
//    private Pager pager;

    @ApiModelProperty("资产报废文档名称")
    private String assetsScrapDocName;
    @ApiModelProperty("资产报废文档路径")
    private String assetsScrapDocUrl;
    @ApiModelProperty("资产报废备注")
    private String assetsScrapRemark;
    private List<RepairFile> repairFiles;

    private AssetsInNew assetsInNew;

    private AssetsFile assetsFile;
    private String assetsPropertyID;

    private AssetsCategory assetsCategory;

    private AssetsWarehouse assetsWarehouse;

    private AssetsOutNew assetsOutNew;


    private RepairNew repairNew;

    @ApiModelProperty("资产类型名称")
    private String assetsCategoryName;

    @ApiModelProperty("是否超出维保期限")
    private String isOverMaintenanceTime;

    @ApiModelProperty("是否超出有效期限")
    private String  isOverValidTime;

    @ApiModelProperty("使用期限，如：2年")
    private String validDate;
    @ApiModelProperty("维保日期，如：3月")
    private String maintenanceDate;
    @ApiModelProperty("采购人")
    private String assetsBuyer;

    @ApiModelProperty("处理备注")
    private String processRemark;

    @ApiModelProperty("删除文档时，作为判断是否空闲文档的依据")
    private String FreeFile;

    /**
     * 数据中心类
     */

    @ApiModelProperty("当前日期")
    private String currentDate;
    @ApiModelProperty("1年前的日期")
    private String beforeOneYearDate;

    @ApiModelProperty("x轴时间，如2018-5")
    private String xTime;

    @ApiModelProperty("y轴数量，对应的数量")
    private Integer yCount;
    @ApiModelProperty("年-月，如2018-5")
    private String yearMonth;
    @ApiModelProperty("年-月，对应的数量")
    private Integer yearMonthCount;
    @ApiModelProperty("年-月-日，如2018-5-31")
    private String yearMonthDay;
    @ApiModelProperty("年-月-日，对应的数量")
    private Integer yearMonthDayCount;;

    @ApiModelProperty("创建时间")
    private String createTime;


    /**
     * 宿舍管理需求，新增字段。
     */
    @ApiModelProperty("楼栋id")
    private String buildingId;
    @ApiModelProperty("楼栋名称")
    private String buildingName;
    @ApiModelProperty("使用场地名称")
    private String usePlaceName;

    @ApiModelProperty("场地类型id")
    private String typeId;

}

