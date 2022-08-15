package com.yice.edu.cn.common.pojo.jw.equipmentType;

import com.yice.edu.cn.common.pojo.Pager;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
*
*厂商表
*
*/
@Data
public class EquipmentVendorManagement{

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
    @ApiModelProperty(value = "厂商编号",dataType = "String")
    private String vendorNumber;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;

    private String deviceId;//设备id
    private String deviceName;//设备名称
    private List<EquipmentVendorManagement> chilren;
    private String className;//设备类型名称
    private String equipmentId;//设备类型id
}
