package com.yice.edu.cn.common.pojo.jw.equipmentType;

import com.yice.edu.cn.common.pojo.Pager;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
/**
*
*设备名称
*
*/
@Data
public class EquipmentDeviceName{

    @ApiModelProperty(value = "主键id",dataType = "String")
    private String id;
    @ApiModelProperty(value = "创建时间",dataType = "String")
    private String createTime;
    @ApiModelProperty(value = "更新时间",dataType = "String")
    private String updateTime;
    @ApiModelProperty(value = "删除标识",dataType = "Integer")
    private Integer del;
    @ApiModelProperty(value = "设备类型id",dataType = "String")
    private String equipmentId;
    @ApiModelProperty(value = "设备编号",dataType = "String")
    private String deviceNumber;
    @ApiModelProperty(value = "设备名称",dataType = "String")
    private String deviceName;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
}
