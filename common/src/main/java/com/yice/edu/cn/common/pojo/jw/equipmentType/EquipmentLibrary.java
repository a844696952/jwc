package com.yice.edu.cn.common.pojo.jw.equipmentType;

import com.yice.edu.cn.common.pojo.Pager;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
/**
*
*设备库
*
*/
@Data
public class EquipmentLibrary{

    @ApiModelProperty(value = "主键Id",dataType = "String")
    private String id;
    @ApiModelProperty(value = "创建时间",dataType = "String")
    private String createTime;
    @ApiModelProperty(value = "更新时间",dataType = "String")
    private String updateTime;
    @ApiModelProperty(value = "删除标识符",dataType = "Integer")
    private Integer del;
    @ApiModelProperty(value = "厂商id",dataType = "String")
    private String vendorId;
    @ApiModelProperty(value = "设备id",dataType = "String")
    private String deviceId;
    @ApiModelProperty(value = "备注",dataType = "String")
    private String remarks;
    @ApiModelProperty(value = "库存数量",dataType = "Integer")
    private Integer equipmentNumber;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;

    private String vendorName;//厂商名称
    private String deviceName;//设备名称
    private String cityId;//市id
    private String cityName;//市名称
    private String districtName;//区名称
    private String districtId;//区/县id
    private String provinceName;//省份名称
    private String provinceId;//省份id
    private String schoolName;//学校名称

}
