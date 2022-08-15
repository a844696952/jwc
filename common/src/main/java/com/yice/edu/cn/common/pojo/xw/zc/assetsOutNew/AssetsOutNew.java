package com.yice.edu.cn.common.pojo.xw.zc.assetsOutNew;

import com.yice.edu.cn.common.pojo.Pager;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;


@Data
@ApiModel("新资产出库")
public class AssetsOutNew{

    @ApiModelProperty("主键")
    private String id;
    @ApiModelProperty("学校id")
    private String schoolId;
    @ApiModelProperty("操作人id")
    private String createUserId;
    @ApiModelProperty("操作人名称")
    private String createUsername;
    @ApiModelProperty("出库单号")
    private String getOutNumber;
    @ApiModelProperty("出库时间")
    private String getOutTime;
    @ApiModelProperty("出库数量(此出库单下所有资产出库数量总和)")
    private Integer getOutCount;
    @ApiModelProperty("备注")
    private String remark;
    @ApiModelProperty("责任人id")
    private String applyUserId;
    @ApiModelProperty("责任人名称")
    private String applyUserName;
    @ApiModelProperty("责任人所在部门id")
    private String applyUserDepartmentId;
    @ApiModelProperty("责任人所在部门")
    private String applyUserDepartmentName;
    @ApiModelProperty("存放楼栋id")
    private String buildingId;
    @ApiModelProperty("存放地点类型id")
    private String typeId;
    @ApiModelProperty("存放地点id")
    private String warehouseId;
    @ApiModelProperty("存放地点名称")
    private String warehouseName;
    @ApiModelProperty("物料id")
    private String assetsId;
    @ApiModelProperty("物料名称")
    private String assetsName;
    @ApiModelProperty("物料属性冗余字段")
    private String assetsProperty;
    @ApiModelProperty("物料编号")
    private String assetsNumber;
    @ApiModelProperty("资产档案 id")
    private String assetsFileId;
    @ApiModelProperty("创建时间")
    private String createTime;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
}
