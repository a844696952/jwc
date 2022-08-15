package com.yice.edu.cn.common.pojo.xw.wage;

import com.yice.edu.cn.common.pojo.Pager;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
/**
*
*
*
*/
@Data
public class WageValue{

    @ApiModelProperty(value = "主键id",dataType = "String")
    private String id;
    @ApiModelProperty(value = "属性id",dataType = "String")
    private String wageAttributeId;
    @ApiModelProperty(value = "属性名称下的值",dataType = "String")
    private String valueSize;
    @ApiModelProperty(value = "记录id",dataType = "String")
    private String recordId;
    @ApiModelProperty(value = "学校id",dataType = "String")
    private String schoolId;
    @ApiModelProperty("创建时间")
    private String createTime;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
}
