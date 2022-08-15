package com.yice.edu.cn.common.pojo.xw.zc.repairNew;

import com.yice.edu.cn.common.pojo.Pager;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;

import java.util.List;


@Data
@ApiModel("新报修表")
public class RepairNew{

    @ApiModelProperty("id")
    private String id;
    @ApiModelProperty("报修单号  (资产编号+2位流水号)")
    private String repairNo;
    @ApiModelProperty("报修类型；1：扫码保修，2，手动报修")
    private String repairType;
    @ApiModelProperty("紧急情况(1、一般，2、紧急，3、非常紧急)")
    private String repairPriority;
    @ApiModelProperty("提交时维修物品时的备注(最多50字)")
    private String remark1;
    @ApiModelProperty("照片  (对多5张)")
    private String img;
    @ApiModelProperty("报修时间")
    private String repairTime;
    @ApiModelProperty("创建时间")
    private String createTime;
    @ApiModelProperty("订单状态(1、待指派，2、待处理，3、已处理)")
    private String status;
    @ApiModelProperty("学校ID")
    private String schoolId;
    @ApiModelProperty("报修人ID")
    private String submitterId;
    @ApiModelProperty("报修人名字")
    private String submitterName;
    @ApiModelProperty("报修人电话")
    private String submitterTel;
    @ApiModelProperty("报修人所在部门ID")
    private String submitterDepartementId;
    @ApiModelProperty("报修人所在部门")
    private String submitterDepartement;
    @ApiModelProperty("资产编号")
    private String assetNo;
    @ApiModelProperty("资产类型( 1 固定   2 易耗)")
    private String assetType;
    @ApiModelProperty("资产分类")
    private String assetClassification;
    @ApiModelProperty("资产名称")
    private String assetName;
    @ApiModelProperty("资产型号")
    private String assetModel;
    @ApiModelProperty("场地名称id")
    private String warehouseId;
    @ApiModelProperty("场地名称名字")
    private String warehouseName;
    @ApiModelProperty("维修人ID")
    private String repairStaffId;
    @ApiModelProperty("维修人名字")
    private String repairStaffName;
    @ApiModelProperty("维修人电话")
    private String repairStaffTel;
    @ApiModelProperty("处理类型 (1 维修成功  (默认)  2无法维修)")
    private String processType;
    @ApiModelProperty("处理费用(维修费用)")
    private Double upkeepCosts;
    @ApiModelProperty("处理时间")
    private String updateTime;
    @ApiModelProperty("处理备注")
    private String processRemark;
    @ApiModelProperty("报废 操作人ID")
    private String operatorId;
    @ApiModelProperty("报废 操作人")
    private String operatorName;
    @ApiModelProperty("报废时间")
    private String scrapTime;
    @ApiModelProperty("报废备注")
    private String scrapRemark;
    @ApiModelProperty("维修提交人id")
    private String recordStaffId;
    @ApiModelProperty("维修提交人名字")
    private String recordStaffName;
    @ApiModelProperty("处理提交人id")
    private String disposeStaffId;
    @ApiModelProperty("处理提交人名字")
    private String disposeStaffName;
    @ApiModelProperty("指派维修人时间")
    private String staffTime;
    @ApiModelProperty("库存明细id")
    private String assetsStockDetailId;
    @ApiModelProperty("对应档案 ID")
    private String assetsFileId;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;


    //日期查询字段
    private String[] searchTimeZone;
    private String searchStearTime;

    private String searchEndTime;
    private List<RepairFile> repairNewFiles;


    //宿舍1.1新增字段
    @ApiModelProperty("楼栋 ID")
    private String buildingId;
    @ApiModelProperty("楼栋 名字")
    private String buildingName;
    @ApiModelProperty("场地类型 ID")
    private String typeId;
    @ApiModelProperty("场地类型 名字")
    private String typeName;
    @ApiModelProperty("楼栋 名字")
    private List<String> buildingIdStr;
    @ApiModelProperty("楼栋场地名字")
    private List<String> floorIdStr;
    //新增字段（电教馆查询列表使用）
    private List<String> schoolIds;
    private  String schoolName;
    private String eehId;

}
