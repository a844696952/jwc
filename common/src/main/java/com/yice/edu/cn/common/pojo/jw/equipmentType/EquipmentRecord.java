package com.yice.edu.cn.common.pojo.jw.equipmentType;

import com.yice.edu.cn.common.pojo.Pager;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
/**
*
*设备出入表
*
*/
@Data
public class EquipmentRecord{

    @ApiModelProperty(value = "主键id",dataType = "String")
    private String id;
    @ApiModelProperty(value = "创建时间",dataType = "String")
    private String createTime;
    @ApiModelProperty(value = "更新时间",dataType = "String")
    private String updateTime;
    @ApiModelProperty(value = "删除标识符",dataType = "Integer")
    private Integer del;
    @ApiModelProperty(value = "厂商名称",dataType = "String")
    private String vendorName;
    @ApiModelProperty(value = "设备名称",dataType = "String")
    private String deviceName;
    @ApiModelProperty(value = "库存数量",dataType = "Integer")
    private Integer equipmentNumber;
    @ApiModelProperty(value = "备注",dataType = "String")
    private String remarks;
    @ApiModelProperty(value = "出入库类型（1为入库，2为出库,3为删除）",dataType = "Integer")
    private Integer type;
    @ApiModelProperty(value = "学校id",dataType = "String")
    private String schoolId;
    @ApiModelProperty(value = "省份id",dataType = "String")
    private String provinceId;
    @ApiModelProperty(value = "省份名称",dataType = "String")
    private String provinceName;
    @ApiModelProperty(value = "市id",dataType = "String")
    private String cityId;
    @ApiModelProperty(value = "市名称",dataType = "String")
    private String cityName;
    @ApiModelProperty(value = "区/县id",dataType = "String")
    private String districtId;
    @ApiModelProperty(value = "区/县名称",dataType = "String")
    private String districtName;
    @ApiModelProperty(value = "学校名称",dataType = "String")
    private String schoolName;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;

    private String countyName;//关键字搜索
    private String startTime;//开始时间
    private String endTime;//结束时间
    private String[] searchTimeZone;//时间段数组
}
